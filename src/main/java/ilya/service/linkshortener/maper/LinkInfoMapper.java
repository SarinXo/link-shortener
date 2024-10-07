package ilya.service.linkshortener.maper;

import ilya.service.linkshortener.dto.CreateLinkInfoRequest;
import ilya.service.linkshortener.dto.LinkInfo;

public class LinkInfoMapper {

    public static LinkInfo requestToDto(CreateLinkInfoRequest req) {
        return new LinkInfo(
                req.link(),
                req.endTime(),
                req.description(),
                req.isActive()
        );
    }

}
