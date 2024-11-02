package ilya.service.linkshortener.dto.wrapper;

import jakarta.validation.Valid;

public record CommonRequest <T> (
        @Valid T body
) {

    public static <T> CommonRequest<T> of(T body) {
        return new CommonRequest<>(body);
    }

}
