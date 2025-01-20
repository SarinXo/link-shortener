package ilya.service.linkshortener.exception;

public class NotFoundLinkException extends LinkShortenerException{

    public NotFoundLinkException(String message) {
        super(message);
    }

    public NotFoundLinkException(String message, Throwable cause) {
        super(message, cause);
    }
}
