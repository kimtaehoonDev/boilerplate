package kimtaehoon.boilerplate.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleServerException(Exception exception) {
        log.warn(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.createServerError());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException exception) {
        return ResponseEntity.status(exception.getErrorCode().getHttpStatus())
            .body(ErrorResponse.create(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.create(
                String.format("'%s'의 타입이 잘못되었습니다.", exception.getParameter().getParameterName())));
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestCookieException(MissingRequestCookieException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse.create(String.format("'%s' 쿠키가 존재하지 않습니다.", exception.getCookieName())));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.create("요청보내신 바디의 형태가 잘못되었습니다."));
    }

    /**
     * 바인딩된 객체의 특정 필드가 규칙을 위반할 때 발생합니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException exception) {
        FieldError fieldError = getFirstFieldError(exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.create(
                String.format(String.format("[%s] %s", fieldError.getField(), fieldError.getDefaultMessage()))));
    }

    private FieldError getFirstFieldError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return bindingResult.getFieldErrors().get(0);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse.create("해당되는 API 경로는 존재하지 않습니다."));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupportedException() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(ErrorResponse.create("지원하지 않는 Http 메서드입니다."));
    }

    @ExceptionHandler(UnsupportedMediaTypeStatusException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedMediaTypeStatusException() {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            .body(ErrorResponse.create("지원하지 않는 미디어 타입입니다."));
    }
}
