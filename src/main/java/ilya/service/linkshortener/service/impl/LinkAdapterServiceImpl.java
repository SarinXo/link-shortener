package ilya.service.linkshortener.service.impl;

import ilya.service.linkshortener.dto.controller.request.LinkInfoFilterRequest;
import ilya.service.linkshortener.dto.controller.request.LinkInfoRequest;
import ilya.service.linkshortener.dto.controller.request.LinkInfoUpdateRequest;
import ilya.service.linkshortener.dto.controller.response.LinkInfoFilterResponse;
import ilya.service.linkshortener.dto.controller.response.LinkInfoResponse;
import ilya.service.linkshortener.dto.service.LinkInfoCreateDto;
import ilya.service.linkshortener.dto.service.LinkInfoFilterDto;
import ilya.service.linkshortener.dto.service.LinkInfoUpdateDto;
import ilya.service.linkshortener.dto.wrapper.CommonRequest;
import ilya.service.linkshortener.dto.wrapper.CommonResponse;
import ilya.service.linkshortener.maper.LinkInfoMapper;
import ilya.service.linkshortener.model.LinkInfoEntity;
import ilya.service.linkshortener.service.LinkAdapterService;
import ilya.service.linkshortener.service.LinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LinkAdapterServiceImpl implements LinkAdapterService {

    private final LinkService linkService;
    private final LinkInfoMapper mapper;

    @Override
    public CommonResponse<LinkInfoResponse> create(CommonRequest<LinkInfoRequest> request) {
        LinkInfoCreateDto createDto = mapper.requestToCreateDto(request.body());
        LinkInfoEntity linkInfo = linkService.create(createDto);
        LinkInfoResponse response = mapper.modelToResponse(linkInfo);

        return CommonResponse.of(response);
    }

    @Override
    public CommonResponse<LinkInfoResponse> update(CommonRequest<LinkInfoUpdateRequest> request) {
        LinkInfoUpdateDto updateDto = mapper.updateRequestToUpdateDto(request.body());
        LinkInfoEntity linkInfo = linkService.update(updateDto);
        LinkInfoResponse response = mapper.modelToResponse(linkInfo);

        return CommonResponse.of(response);
    }

    @Override
    public CommonResponse<LinkInfoFilterResponse> getByFilter(CommonRequest<LinkInfoFilterRequest> request) {
        LinkInfoFilterDto filterDto = mapper.filterRequestToFilterDto(request.body());
        LinkInfoFilterResponse response = linkService.getLinksByFilter(filterDto).stream()
                .map(mapper::modelToResponse)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        LinkInfoFilterResponse::new
                ));

        return CommonResponse.of(response);
    }
}
