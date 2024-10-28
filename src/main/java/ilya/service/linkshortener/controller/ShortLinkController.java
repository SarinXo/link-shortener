package ilya.service.linkshortener.controller;

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

    private final LinkService linkServiceImpl;

    @GetMapping("go")
    public RedirectView openTargetLink(@RequestParam("ref") String shortLink) {
        String targetLink = linkServiceImpl.getTargetLink(shortLink);
        return new RedirectView(targetLink);
    }
}
