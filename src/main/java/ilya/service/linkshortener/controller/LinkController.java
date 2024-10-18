package ilya.service.linkshortener.controller;

import ilya.service.linkshortener.dto.LinkInfoRequest;
import ilya.service.linkshortener.dto.LinkInfoResponse;
import ilya.service.linkshortener.dto.UpdateLinkInfoRequest;
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

import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/links")
@Slf4j
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkServiceImpl;

    @PostMapping("/shorten")
    public LinkInfoResponse createShortLink(@RequestBody @Valid LinkInfoRequest requestDto) {
        log.debug("LinkController#createShortLink() was called");
        var response = linkServiceImpl.createLinkInfo(requestDto);
        log.debug("LinkController#createShortLink() was successfully done");
        return response;
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void deleteLink(@RequestParam UUID id) {
        linkServiceImpl.delete(id);
    }

    @PutMapping
    @ResponseStatus(NO_CONTENT)
    public void updateLink(@RequestBody @Valid UpdateLinkInfoRequest updateLinkInfoRequest) {
        linkServiceImpl.update(updateLinkInfoRequest);
    }


}
