package ilya.service.linkshortener.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.TEXT_HTML;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class LinkExceptionHandler {
    private final String notFoundPage;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity
                .status(NOT_FOUND)
                .contentType(TEXT_HTML)
                .body(notFoundPage);
    }
}
