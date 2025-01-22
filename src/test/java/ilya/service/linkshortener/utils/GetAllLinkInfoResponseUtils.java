package ilya.service.linkshortener.utils;

import ilya.service.linkshortener.dto.controller.response.LinkInfoFilterResponse;

import java.util.stream.Stream;

public class GetAllLinkInfoResponseUtils {

    public static LinkInfoFilterResponse.LinkInfoFilterResponseBuilder random(int countLinks) {
        var links = Stream
                .generate(() -> LinkInfoResponseUtils.random().build())
                .limit(countLinks)
                .toList();
        return LinkInfoFilterResponse.builder().links(links);
    }
}
