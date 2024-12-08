package demo.template.webflux.app.transactions.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

/* todo: pagination? */
public interface TransactionRepository extends R2dbcRepository<TransactionEntity, Long> {

    Flux<TransactionEntity> findAllByPayee(Long payeeId);

    Flux<TransactionEntity> findAllByRecipient(Long recipientId);

    Flux<TransactionEntity> findAllByPayeeAndRecipient(Long payeeId, Long recipientId);

}
