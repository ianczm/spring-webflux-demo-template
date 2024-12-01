package demo.template.webflux.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(RestException.class)
    public Mono<ResponseEntity<?>> handleException(RestException restException) {

        logError(restException);

        return toResponseEntity(restException);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<?>> handleException(WebExchangeBindException webExchangeBindException) {

        logError(webExchangeBindException);

        record FieldErrorDetail(String field, String message) {
        }

        var message = webExchangeBindException.getReason();
        var fieldErrors = webExchangeBindException.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> new FieldErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()));

        return toResponseEntity(fromStatus(BAD_REQUEST)
            .message("Validation error")
            .detail(webExchangeBindException.getBody().getDetail())
            .errors(Map.of("validations", fieldErrors))
            .build());
    }

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<?>> handleException(ServerWebInputException serverWebInputException) {

        logError(serverWebInputException);

        return toResponseEntity(fromStatus(BAD_REQUEST)
            .message("Validation error")
            .detail(serverWebInputException.getBody().getDetail())
            .build());
    }

    @ExceptionHandler(Throwable.class)
    public Mono<ResponseEntity<?>> handleException(Throwable throwable) {

        logError(throwable);

        return toResponseEntity(fromStatus(INTERNAL_SERVER_ERROR)
            .message("An unexpected error occurred.")
            .build());
    }

    private static Mono<ResponseEntity<?>> toResponseEntity(RestProblemDetail restProblemDetail) {
        return Mono.just(ResponseEntity
            .status(restProblemDetail.status())
            .body(restProblemDetail));
    }

    private static Mono<ResponseEntity<?>> toResponseEntity(RestException restException) {
        return Mono.just(ResponseEntity
            .status(restException.getHttpStatus())
            .body(RestProblemDetail.fromException(restException)));
    }

    private static RestProblemDetail.RestProblemDetailBuilder fromStatus(HttpStatus httpStatus) {
        return RestProblemDetail.builder()
            .timestamp(Instant.now())
            .status(httpStatus.value())
            .title(httpStatus.getReasonPhrase());
    }

    private static void logError(RestException restException) {
        log.error("{}: {}", restException.getMessage(), restException.getDetail(), restException);
    }

    private static void logError(Throwable throwable) {
        log.error("{}: {}", throwable.getClass(), throwable.getMessage(), throwable);
    }
}
