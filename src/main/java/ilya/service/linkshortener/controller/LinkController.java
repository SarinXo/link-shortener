package ilya.service.linkshortener.controller;

import ilya.service.linkshortener.dto.CreateLinkInfoRequestDto;
import ilya.service.linkshortener.dto.CreateLinkInfoResponseDto;
import ilya.service.linkshortener.service.LinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkServiceImpl;

    @ResponseStatus(OK)
    @PostMapping("/shorten")
    public CreateLinkInfoResponseDto createShortLink(@RequestBody CreateLinkInfoRequestDto requestDto) {
        log.debug("LinkController#createShortLink() was called");
        var response = linkServiceImpl.shortenLink(requestDto);
        log.debug("LinkController#createShortLink() was successfully done");
        return response;
    }

}
