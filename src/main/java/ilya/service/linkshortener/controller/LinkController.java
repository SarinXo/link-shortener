package ilya.service.linkshortener.controller;

import ilya.service.linkshortener.dto.CreateLinkInfoRequest;
import ilya.service.linkshortener.dto.CreateLinkInfoResponse;
import ilya.service.linkshortener.service.LinkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkServiceImpl;

    @PostMapping("/shorten")
    public CreateLinkInfoResponse createShortLink(@RequestBody @Valid CreateLinkInfoRequest requestDto) {
        log.debug("LinkController#createShortLink() was called");
        var response = linkServiceImpl.shortenLink(requestDto);
        log.debug("LinkController#createShortLink() was successfully done");
        return response;
    }

}
