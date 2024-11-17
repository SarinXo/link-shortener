package ilya.service.linkshortener.controller.api.v1;

import ilya.service.linkshortener.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/short-link")
public class ShortLinkController {

    private final LinkService linkService;

    @GetMapping("/{shortLink}")
    public RedirectView redirectOnLink(@PathVariable String shortLink) {
        String targetLink = linkService.getLinkByShortLink(shortLink);
        return new RedirectView(targetLink);
    }
}
