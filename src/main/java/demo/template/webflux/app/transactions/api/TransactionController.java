package demo.template.webflux.app.transactions.api;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import demo.template.webflux.app.transactions.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @PostMapping
    Mono<ResponseEntity<TransactionDto>> create(@RequestBody @Validated TransactionDto transactionDto) {
        return Mono.just(transactionDto)
            .map(transactionMapper::toEntity)
            .flatMap(transactionRepository::save)
            .flatMap(savedTransaction -> transactionRepository.findById(savedTransaction.getId()))
            .map(transactionMapper::toDto)
            .map(resultTransaction -> ResponseEntity.status(CREATED).body(resultTransaction));
    }

    @GetMapping
    Mono<ResponseEntity<List<TransactionDto>>> findAll() {
        return transactionRepository.findAll()
            .map(transactionMapper::toDto)
            .collectList()
            .map(ResponseEntity::ok);
    }

}
