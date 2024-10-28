package ilya.service.linkshortener.dto.wrapper;

public record CommonRequest <T> (
        T body
) {

    public static <T> CommonRequest<T> of(T body) {
        return new CommonRequest<>(body);
    }

}
