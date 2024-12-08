package demo.template.webflux.app.balances;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import demo.template.webflux.app.balances.api.UserBalanceDto;
import demo.template.webflux.app.transactions.repository.TransactionEntity;
import demo.template.webflux.app.transactions.repository.TransactionRepository;
import demo.template.webflux.app.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBalanceService {

    private final UserService userService;
    private final TransactionRepository transactionRepository;

    public Mono<UserBalanceDto> findBalancesByUser(@PathVariable Long userId) {
        return userService.verifyUserExists(userId, "Failed to get balances.")
            .then(Mono.defer(() -> {

                var incomingMono = summingTransactionAmounts(() -> transactionRepository.findAllByRecipientId(userId));
                var outgoingMono = summingTransactionAmounts(() -> transactionRepository.findAllByPayeeId(userId));

                return Mono.zip(incomingMono, outgoingMono, toUserBalanceDto(userId));
            }));
    }

    private Mono<BigDecimal> summingTransactionAmounts(Supplier<Flux<TransactionEntity>> fluxSupplier) {
        return fluxSupplier.get()
            .map(TransactionEntity::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .map(result -> result.setScale(4, RoundingMode.UNNECESSARY));
    }

    private BiFunction<BigDecimal, BigDecimal, UserBalanceDto> toUserBalanceDto(Long userId) {
        return (incoming, outgoing) -> new UserBalanceDto(userId, incoming, outgoing);
    }

}
