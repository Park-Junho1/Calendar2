package hello.calendar2.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    protected BaseException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
