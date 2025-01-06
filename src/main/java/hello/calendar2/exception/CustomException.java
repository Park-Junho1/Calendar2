package hello.calendar2.exception;

public class CustomException extends BaseException {

  private final ErrorCode errorCode;

  public CustomException(ErrorCode errorCode) {
      super(errorCode.getMessage(), errorCode.getStatus());
      this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
      return errorCode;
  }
}
