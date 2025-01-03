package ilya.service.linkshortener.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class LogFilter extends HttpFilter {

    @Override
    protected void doFilter(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        String requestId = request.getRequestId();
        String method = request.getMethod();
        String requestUri = request.getRequestURI() + formatQueryString(request);
        String headers = inlineHeaders(request);

        log.debug("Начало обработки запроса: {} метод: {} URI перехода: {} заголовки: {} ", requestId, method, requestUri, headers);

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        try {
            super.doFilter(request, responseWrapper, chain);

            int status = response.getStatus();
            String responseBody = new String(responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding());

            log.debug("Ответ на запрос: {} метод: {} URI перехода: {} статус: {} Тело ответа: {}", requestId, method, requestUri, status, responseBody);

        } finally {
            responseWrapper.copyBodyToResponse();
        }

    }

    private static String formatQueryString(HttpServletRequest request) {
        return Optional
                .ofNullable(request.getQueryString())
                .map(qs -> "?=" + qs)
                .orElse(Strings.EMPTY);
    }

    private static String inlineHeaders(HttpServletRequest request) {
        Map<String, String> headersMap = Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        request::getHeader)
                );
        return headersMap.entrySet().stream()
                .map(entry -> {
                    String headerName = entry.getKey();
                    String headerValue = entry.getValue();

                    return headerName + "=" + headerValue;
                })
                .collect(Collectors.joining(", "));
    }

}
