package ilya.service.linkshortener.controller.api.v1;

import ilya.service.linkshortener.dto.controller.request.LinkInfoFilterRequest;
import ilya.service.linkshortener.dto.controller.request.LinkInfoRequest;
import ilya.service.linkshortener.dto.controller.request.LinkInfoUpdateRequest;
import ilya.service.linkshortener.dto.controller.response.LinkInfoFilterResponse;
import ilya.service.linkshortener.dto.controller.response.LinkInfoResponse;
import ilya.service.linkshortener.dto.wrapper.CommonRequest;
import ilya.service.linkshortener.dto.wrapper.CommonResponse;
import ilya.service.linkshortener.service.LinkAdapterService;
import ilya.service.linkshortener.service.LinkService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/link-infos")
public class LinkController {

    private final LinkAdapterService linkAdapterServiceImpl;
    private final LinkService linkService;

    @PostMapping
    public CommonResponse<LinkInfoResponse> createShortLink(
            @RequestBody @Valid CommonRequest<LinkInfoRequest> requestDto
    ) {
        return linkAdapterServiceImpl.create(requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteLink(@PathVariable UUID id) {
        linkService.delete(id);
    }

    @PutMapping
    public CommonResponse<LinkInfoResponse> updateLink(
            @RequestBody @Valid CommonRequest<LinkInfoUpdateRequest> updateLinkInfoRequest
    ) {
        return linkAdapterServiceImpl.update(updateLinkInfoRequest);
    }

    @PostMapping("/filter")
    public CommonResponse<LinkInfoFilterResponse> getWithFilter(
            @PageableDefault Pageable pageable,
            @RequestBody @Valid CommonRequest<LinkInfoFilterRequest> request
    ) {
        return linkAdapterServiceImpl.getByFilter(request, pageable);
    }

}
