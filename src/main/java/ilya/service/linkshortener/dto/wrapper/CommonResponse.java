package ilya.service.linkshortener.dto.wrapper;

public record CommonResponse <T> (
        T body
) {

    public static <T> CommonResponse<T> of(T body) {
        return new CommonResponse<>(body);
    }

}
