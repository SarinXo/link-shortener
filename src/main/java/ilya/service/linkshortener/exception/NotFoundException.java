package ilya.service.linkshortener.exception;

public class NotFoundException extends LinkShortenerException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
