package kimtaehoon.boilerplate.common.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("예상하지 못한 예외가 발생하면 500번 예외를 반환한다.")
    void handleException() throws Exception {
        // given
        Exception exception = new Exception("에러에러");

        // when
        ResponseEntity<ErrorResponse> response = handler.handleServerException(exception);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        ErrorResponse body = response.getBody();
        assertThat(body.getMessage()).isEqualTo(ErrorResponse.SERVER_ERROR_MSG);
    }

    @Test
    @DisplayName("ApiException 이 발생하면 ErrorCode의 status와 message를 반환한다.")
    void handleApiException() throws Exception {
        // given
        ErrorCode errorCode = ErrorCode.TEST;
        ApiException exception = new ApiException(errorCode);

        // when
        ResponseEntity<ErrorResponse> response = handler.handleApiException(exception);

        // then
        assertThat(response.getStatusCode()).isEqualTo(errorCode.getHttpStatus());

        ErrorResponse body = response.getBody();
        assertThat(body.getMessage()).isEqualTo(errorCode.getMessage());
    }

    @Test
    @DisplayName("MethodArgumentTypeMismatchException 예외가 발생하면 BAD_REQUEST 상태를 반환한다.")
    void handleMethodArgumentTypeMismatchException() throws Exception {
        // given
        String paramName = "hello";
        MethodParameter methodParameter = mock(MethodParameter.class);
        when(methodParameter.getParameterName())
            .thenReturn(paramName);
        MethodArgumentTypeMismatchException exception =
            new MethodArgumentTypeMismatchException(null, null, "", methodParameter, null);

        // when
        ResponseEntity<ErrorResponse> response = handler.handleMethodArgumentTypeMismatchException(exception);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        ErrorResponse body = response.getBody();
        assertThat(body.getMessage())
            .isEqualTo("'" + paramName + "'의 타입이 잘못되었습니다.");
    }

    @Test
    @DisplayName("MissingRequestCookieException 예외가 발생하면 NOT_FOUND 상태를 반환한다.")
    void handleMissingRequestCookieException() throws Exception {
        // given
        String cookieName = "d";
        MethodParameter methodParameter = mock(MethodParameter.class);
        MissingRequestCookieException exception = new MissingRequestCookieException(cookieName, methodParameter);

        // when
        ResponseEntity<ErrorResponse> response = handler.handleMissingRequestCookieException(exception);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ErrorResponse body = response.getBody();
        assertThat(body.getMessage())
            .isEqualTo("'" + cookieName + "' 쿠키가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("HttpMessageNotReadableException 예외가 발생하면 BAD_REQUEST 상태를 반환한다.")
    void handleHttpMessageNotReadableException() throws Exception {
        // when
        ResponseEntity<ErrorResponse> response = handler.handleHttpMessageNotReadableException();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        ErrorResponse body = response.getBody();
        assertThat(body.getMessage())
            .isEqualTo("요청보내신 바디의 형태가 잘못되었습니다.");
    }

    @Test
    @DisplayName("MethodArgumentNotValidException 예외가 발생하면 BAD_REQUEST 상태를 반환한다.")
    void handleMethodArgumentNotValidException() throws Exception {
        // given
        String field = "field";
        String defaultMessage = "default-message";
        MethodArgumentNotValidException exception = mockMethodArgumentNotValidException(field, defaultMessage);

        // when
        ResponseEntity<ErrorResponse> response = handler.handleMethodArgumentNotValidException(exception);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        ErrorResponse body = response.getBody();
        assertThat(body.getMessage())
            .isEqualTo("[" + field + "] " + defaultMessage);
    }

    private static MethodArgumentNotValidException mockMethodArgumentNotValidException(String field,
        String defaultMessage) {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = mock(FieldError.class);
        when(fieldError.getField()).thenReturn(field);
        when(fieldError.getDefaultMessage()).thenReturn(defaultMessage);
        when(bindingResult.getFieldErrors())
            .thenReturn(List.of(fieldError));
        when(exception.getBindingResult()).thenReturn(bindingResult);
        return exception;
    }

    @Test
    @DisplayName("NoHandlerFoundException 예외가 발생하면 NOT_FOUND 상태를 반환한다.")
    void handleNoHandlerFoundException() throws Exception {
        // when
        ResponseEntity<ErrorResponse> response = handler.handleNoHandlerFoundException();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ErrorResponse body = response.getBody();
        assertThat(body.getMessage())
            .isEqualTo("해당되는 API 경로는 존재하지 않습니다.");
    }

    @Test
    @DisplayName("HttpRequestMethodNotSupportedException 예외가 발생하면 METHOD_NOT_ALLOWED 상태를 반환한다.")
    void handleMethodNotSupportedException() throws Exception {
        // when
        ResponseEntity<ErrorResponse> response = handler.handleMethodNotSupportedException();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);

        ErrorResponse body = response.getBody();
        assertThat(body.getMessage())
            .isEqualTo("지원하지 않는 Http 메서드입니다.");
    }

    @Test
    @DisplayName("UnsupportedMediaTypeStatusException 예외가 발생하면 UNSUPPORTED_MEDIA_TYPE 상태를 반환한다.")
    void handleUnsupportedMediaTypeStatusException() throws Exception {
        // when
        ResponseEntity<ErrorResponse> response = handler.handleUnsupportedMediaTypeStatusException();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);

        ErrorResponse body = response.getBody();
        assertThat(body.getMessage())
            .isEqualTo("지원하지 않는 미디어 타입입니다.");
    }
}