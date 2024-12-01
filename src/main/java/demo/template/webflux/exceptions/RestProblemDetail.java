package demo.template.webflux.exceptions;

import java.time.Instant;
import java.util.Map;

import lombok.Builder;

@Builder
public record RestProblemDetail(
    Instant timestamp,
    int status,
    String title,
    String message,
    String detail,
    String instance,
    Map<String, Object> errors
) {

    public static RestProblemDetail fromException(RestException restException) {

        var httpStatus = restException.getHttpStatus();

        return RestProblemDetail.builder()
            .timestamp(restException.getTimestamp())
            .status(httpStatus.value())
            .title(httpStatus.getReasonPhrase())
            .message(restException.getMessage())
            .detail(restException.getDetail())
            .build();
    }

}
