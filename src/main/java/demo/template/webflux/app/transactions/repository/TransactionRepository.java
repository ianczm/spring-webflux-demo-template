package demo.template.webflux.app.transactions.repository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import demo.template.webflux.app.users.repository.UserEntity;
import demo.template.webflux.app.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TransactionRepository {

    private final CoreTransactionRepository coreTransactionRepository;
    private final UserRepository userRepository;

    public Mono<TransactionEntity> save(TransactionEntity transactionEntity) {
        return enrichWithUsersMono(() -> coreTransactionRepository.save(transactionEntity));
    }

    public Mono<TransactionEntity> findById(Long transactionId) {
        return enrichWithUsersMono(() -> coreTransactionRepository.findById(transactionId));
    }

    public Flux<TransactionEntity> findAll() {
        return enrichWithUsersFlux(coreTransactionRepository::findAll);
    }

    public Flux<TransactionEntity> findAllByPayeeId(Long payeeId) {
        return enrichWithUsersFlux(() -> coreTransactionRepository.findAllByPayeeId(payeeId));
    }

    public Flux<TransactionEntity> findAllByRecipientId(Long recipientId) {
        return enrichWithUsersFlux(() -> coreTransactionRepository.findAllByRecipientId(recipientId));
    }

    public Flux<TransactionEntity> findAllByPayeeIdAndRecipientId(Long payeeId, Long recipientId) {
        return enrichWithUsersFlux(() -> coreTransactionRepository.findAllByPayeeIdAndRecipientId(payeeId, recipientId));
    }

    public Flux<TransactionEntity> findAllByPayeeIdOrRecipientId(Long payeeId, Long recipientId) {
        return enrichWithUsersFlux(() -> coreTransactionRepository.findAllByPayeeIdOrRecipientId(payeeId, recipientId));
    }

    private Mono<TransactionEntity> enrichWithUsersMono(Supplier<Mono<TransactionEntity>> monoSupplier) {
        return enrichWithUsersFlux(() -> monoSupplier.get()
            .flatMapMany(Flux::just))
            .next();
    }

    private Flux<TransactionEntity> enrichWithUsersFlux(Supplier<Flux<TransactionEntity>> fluxSupplier) {

        var transactionsFlux = fluxSupplier.get().cache();

        var uniqueUserIdsMono = transactionsFlux
            .flatMap(transaction -> Flux.just(transaction.getPayeeId(), transaction.getRecipientId()))
            .distinct()
            .collectList();

        var usersByIdMono = uniqueUserIdsMono
            .flatMapMany(userRepository::findAllById)
            .collectMap(UserEntity::getId);

        return Flux.zip(transactionsFlux.collectList(), usersByIdMono, this::withRecipientAndPayee)
            .flatMap(Function.identity());
    }

    private Flux<TransactionEntity> withRecipientAndPayee(List<TransactionEntity> transactions, Map<Long, UserEntity> usersById) {
        return Flux.fromIterable(transactions)
            .map(transaction -> withRecipientAndPayee(transaction, usersById));
    }

    private TransactionEntity withRecipientAndPayee(TransactionEntity transactionEntity, Map<Long, UserEntity> usersById) {

        var recipient = usersById.get(transactionEntity.getRecipientId());
        var payee = usersById.get(transactionEntity.getPayeeId());

        return transactionEntity.toBuilder()
            .recipient(recipient)
            .payee(payee)
            .build();
    }

}
