package hello.calendar2.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponseDto {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private HttpStatus httpStatus;
    private final String message;
}
