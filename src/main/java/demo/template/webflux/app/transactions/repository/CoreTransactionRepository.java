package demo.template.webflux.app.transactions.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

interface CoreTransactionRepository extends R2dbcRepository<TransactionEntity, Long> {

    Flux<TransactionEntity> findAllByPayeeId(Long payeeId);

    Flux<TransactionEntity> findAllByRecipientId(Long recipientId);

    Flux<TransactionEntity> findAllByPayeeIdAndRecipientId(Long payeeId, Long recipientId);

    Flux<TransactionEntity> findAllByPayeeIdOrRecipientId(Long payeeId, Long recipientId);

}
