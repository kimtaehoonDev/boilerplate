package kimtaehoon.boilerplate.common.exception;

import lombok.Getter;

// TODO 2. ApiException 을 생성한다.
@Getter
public class ApiException extends RuntimeException {

    private final ErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
