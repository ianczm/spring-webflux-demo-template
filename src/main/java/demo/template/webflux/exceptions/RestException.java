package demo.template.webflux.exceptions;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class RestException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String detail;
    private final Instant timestamp = Instant.now();

    public RestException(HttpStatus httpStatus, String message, String detail) {
        super(message);
        this.detail = detail;
        this.httpStatus = httpStatus;
    }

    @Builder
    public RestException(HttpStatus httpStatus, String message, String detail, Throwable cause) {
        super(message, cause);
        this.detail = detail;
        this.httpStatus = httpStatus;
    }

}
