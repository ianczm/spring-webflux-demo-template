package demo.template.webflux.exceptions;

import org.springframework.http.HttpStatus;

public class UnavailableRestException extends RestException {

    public UnavailableRestException(String detail) {
        super(HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable", detail);
    }
}
