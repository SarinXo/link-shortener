package ilya.service.linkshortener.controller.api.v1;

import ilya.service.linkshortener.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ShortLinkController {

    private final LinkService linkService;

    @GetMapping("go")
    public RedirectView redirectOnLink(@RequestParam("ref") String shortLink) {
        String targetLink = linkService.getLinkByShortLink(shortLink);
        return new RedirectView(targetLink);
    }
}
