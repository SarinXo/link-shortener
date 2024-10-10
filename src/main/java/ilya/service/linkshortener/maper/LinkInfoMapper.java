package ilya.service.linkshortener.maper;

import ilya.service.linkshortener.dto.CreateLinkInfoRequest;
import ilya.service.linkshortener.model.LinkInfo;

import java.util.UUID;

public class LinkInfoMapper {

    public static LinkInfo requestToDto(CreateLinkInfoRequest req, String shortLink) {
        return new LinkInfo(
                UUID.randomUUID(),
                shortLink,
                0L,
                req.link(),
                req.endTime(),
                req.description(),
                req.isActive()
        );
    }

}
