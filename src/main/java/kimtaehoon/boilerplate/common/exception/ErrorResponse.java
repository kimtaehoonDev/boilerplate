package kimtaehoon.boilerplate.common.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO 3. ErrorResponse 클래스를 생성한다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ErrorResponse {

    public static final String SERVER_ERROR_MSG = "서버 에러가 발생했습니다. 관리자에게 문의하세요.";

    private String message;

    @Builder
    private ErrorResponse(String message) {
        this.message = message;
    }

    public static ErrorResponse create(String message) {
        return ErrorResponse.builder()
            .message(message)
            .build();
    }

    public static ErrorResponse createServerError() {
        return create(SERVER_ERROR_MSG);
    }
}
