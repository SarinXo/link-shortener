package ilya.service.linkshortener.utils;

import ilya.service.linkshortener.dto.GetAllLinkInfoResponse;

import java.util.stream.Stream;

public class GetAllLinkInfoResponseUtils {

    public static GetAllLinkInfoResponse.GetAllLinkInfoResponseBuilder random(int countLinks) {
        var links = Stream
                .generate(() -> LinkInfoResponseUtils.random().build())
                .limit(countLinks)
                .toList();
        return GetAllLinkInfoResponse.builder().links(links);
    }
}
