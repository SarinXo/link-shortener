package ilya.service.linkshortener.maper;

import ilya.service.linkshortener.dto.controller.request.LinkInfoRequest;
import ilya.service.linkshortener.dto.controller.request.UpdateLinkInfoRequest;
import ilya.service.linkshortener.dto.controller.response.LinkInfoResponse;
import ilya.service.linkshortener.dto.service.LinkInfoCreateDto;
import ilya.service.linkshortener.dto.service.LinkInfoUpdateDto;
import ilya.service.linkshortener.model.LinkInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LinkInfoMapper {

    LinkInfoResponse modelToResponse(LinkInfoEntity entity);

    @Mapping(target = "openingCount", constant = "0L")
    LinkInfoEntity createDtoToModel(LinkInfoCreateDto dto);

    LinkInfoCreateDto requestToCreateDto(LinkInfoRequest req);

    LinkInfoUpdateDto updateRequestToUpdateDto(UpdateLinkInfoRequest req);
}
