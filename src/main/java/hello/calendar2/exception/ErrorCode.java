package hello.calendar2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 400: Bad Request (클라이언트 요청 오류)
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "요청값의 형식이 유효하지 않습니다."),
    MISSING_REQUIRED_FIELD(HttpStatus.BAD_REQUEST, "필수 필드가 누락되었습니다."),
    DUPLICATE_ENTRY(HttpStatus.BAD_REQUEST, "중복된 데이터입니다."), // 예: 중복 회원가입
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "이메일 형식이 유효하지 않습니다."), // 이메일 유효성 검사 실패

    // 401: Unauthorized (인증 실패)
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "유효하지 않은 인증 정보입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "접근이 거부되었습니다."),

    // 403: Forbidden (권한 부족)
    FORBIDDEN(HttpStatus.FORBIDDEN, "이 작업을 수행할 권한이 없습니다."),
    RESOURCE_FORBIDDEN(HttpStatus.FORBIDDEN, "이 리소스에 접근할 수 없습니다."),

    // 404: Not Found (리소스 없음)
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."), // userId로 사용자 검색 실패
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."), // scheduleId로 일정 검색 실패
    PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "페이지를 찾을 수 없습니다."),

    // 500: Internal Server Error (서버 내부 오류)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생했습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 처리 중 오류가 발생했습니다."),
    SERVICE_UNAVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, "서비스를 사용할 수 없습니다."),
    UNEXPECTED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "예기치 못한 예외가 발생했습니다."),

    // 502: Bad Gateway (게이트웨이 오류)
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "잘못된 게이트웨이 응답입니다."),
    UPSTREAM_SERVER_ERROR(HttpStatus.BAD_GATEWAY, "상위 서버에서 오류가 발생했습니다.");

    private final HttpStatus status;    // 에러 코드 (예: ERR400, ERR401)
    private final String message; // 에러 메시지 (예: "잘못된 요청입니다.")

}
