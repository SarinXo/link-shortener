package ilya.service.linkshortener.exception.model;

public class NotFoundException extends LinkShortenerException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
