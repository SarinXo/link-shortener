package ilya.service.linkshortener.controller.api.v1;

import ilya.service.linkshortener.dto.LinkInfoRequest;
import ilya.service.linkshortener.dto.LinkInfoResponse;
import ilya.service.linkshortener.dto.UpdateLinkInfoRequest;
import ilya.service.linkshortener.dto.wrapper.CommonRequest;
import ilya.service.linkshortener.dto.wrapper.CommonResponse;
import ilya.service.linkshortener.model.LinkInfo;
import ilya.service.linkshortener.service.LinkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/links")
@Slf4j
@RequiredArgsConstructor
public class LinkRestController {

    private final LinkService linkServiceImpl;

    @PostMapping("/shorten")
    public CommonResponse<LinkInfoResponse> createShortLink(
            @RequestBody @Valid CommonRequest<LinkInfoRequest> requestDto
    ) {
        log.debug("LinkRestController#createShortLink() was called");
        var response = linkServiceImpl.createLinkInfo(requestDto);
        log.debug("LinkRestController#createShortLink() was successfully done");
        return CommonResponse.of(response);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void deleteLink(@RequestParam UUID id) {
        linkServiceImpl.delete(id);
    }

    @PutMapping
    public CommonResponse<LinkInfoResponse> updateLink(
            @RequestBody @Valid CommonRequest<UpdateLinkInfoRequest> updateLinkInfoRequest
    ) {
        LinkInfoResponse response = linkServiceImpl.update(updateLinkInfoRequest);
        return CommonResponse.of(response);
    }

    //public CommonResponse<List<LinkInfo>> getWithFilter(@RequestParam LinkFilter filter) {
        //    LinkInfoResponse response = linkServiceImpl.getByFilter
                //}

}
