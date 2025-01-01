package ilya.service.linkshortener.dto.wrapper;

import java.util.UUID;

public record CommonResponse<T>(
        UUID id,
        T body
) {

    public static <T> CommonResponse<T> of(T body) {
        return new CommonResponse<>(null, body);
    }

}
