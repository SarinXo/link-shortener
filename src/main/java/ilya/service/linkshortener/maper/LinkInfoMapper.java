package ilya.service.linkshortener.maper;

import ilya.service.linkshortener.dto.CreateLinkInfoRequestDto;
import ilya.service.linkshortener.dto.LinkInfoDto;

public class LinkInfoMapper {

    public static LinkInfoDto requestToDto(CreateLinkInfoRequestDto req) {
        return new LinkInfoDto(
                req.link(),
                req.endTime(),
                req.description(),
                req.isActive()
        );
    }

}
