package demo.template.webflux.app.transactions.api;

import java.util.List;

import demo.template.webflux.app.transactions.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("users/{userId}/transactions")
@RequiredArgsConstructor
public class UserTransactionController {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @GetMapping
    Mono<ResponseEntity<List<TransactionDto>>> findAllByUserIdFiltered(
        @PathVariable Long userId,
        @RequestParam(value = "filter", defaultValue = "ALL") TransactionFilterQueryParam filter
    ) {

        var transactionsFlux = switch (filter) {
            case ALL -> transactionRepository.findAllByPayeeIdOrRecipientId(userId, userId);
            case INCOMING -> transactionRepository.findAllByRecipientId(userId);
            case OUTGOING -> transactionRepository.findAllByPayeeId(userId);
        };

        return transactionsFlux
            .map(transactionMapper::toDto)
            .collectList()
            .map(ResponseEntity::ok);
    }

}
