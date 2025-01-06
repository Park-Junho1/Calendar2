package hello.calendar2.exception;

import hello.calendar2.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // CustomException 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(getHttpStatus(errorCode).value())
                .error(getHttpStatus(errorCode).getReasonPhrase())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    // RunTimeException의 명시적인 처리
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException ex) {
        // 예외 메시지 활용
        String exceptionMessage = ex.getMessage();

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(exceptionMessage != null ? exceptionMessage : "서버 내부 오류가 발생했습니다.")
                .build();

        // 로깅 추가
        log.error("RuntimeException 발생: ", ex);

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    // 기타 예외에 대한 명시적인 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
        // 예외 메시지 활용
        String exceptionMessage = ex.getMessage();

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(exceptionMessage != null ? exceptionMessage : "알 수 없는 오류가 발생했습니다.")
                .build();

        // 로깅 추가
        log.error("Exception 발생: ", ex);

        return ResponseEntity.status(errorResponseDto.getStatus()).body(errorResponseDto);
    }

    // ErrorCode에서 HTTP 상태 코드 매핑.
    @ExceptionHandler(Exception.class)
    private HttpStatus getHttpStatus(ErrorCode errorCode) {
        return switch (errorCode) {
            case VALIDATION_ERROR, INVALID_REQUEST, MISSING_REQUIRED_FIELD -> HttpStatus.BAD_REQUEST;
            case UNAUTHORIZED, INVALID_CREDENTIALS -> HttpStatus.UNAUTHORIZED;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
            case RESOURCE_NOT_FOUND, USER_NOT_FOUND, SCHEDULE_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case BAD_GATEWAY -> HttpStatus.BAD_GATEWAY;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
