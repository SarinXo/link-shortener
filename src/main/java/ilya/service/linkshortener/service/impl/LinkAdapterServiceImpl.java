package ilya.service.linkshortener.service.impl;

import ilya.service.linkshortener.dto.GetAllLinkInfoResponse;
import ilya.service.linkshortener.dto.LinkInfoRequest;
import ilya.service.linkshortener.dto.LinkInfoResponse;
import ilya.service.linkshortener.dto.UpdateLinkInfoRequest;
import ilya.service.linkshortener.dto.service.LinkInfoCreateDto;
import ilya.service.linkshortener.dto.service.LinkInfoUpdateDto;
import ilya.service.linkshortener.dto.wrapper.CommonRequest;
import ilya.service.linkshortener.dto.wrapper.CommonResponse;
import ilya.service.linkshortener.maper.LinkInfoMapper;
import ilya.service.linkshortener.model.LinkInfo;
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

    @Override
    public CommonResponse<LinkInfoResponse> create(CommonRequest<LinkInfoRequest> request) {
        LinkInfoCreateDto createDto = LinkInfoMapper.requestToCreateDto(request.body());
        LinkInfo linkInfo = linkService.create(createDto);
        LinkInfoResponse response = LinkInfoMapper.modelToResponse(linkInfo);

        return CommonResponse.of(response);
    }

    @Override
    public CommonResponse<LinkInfoResponse> update(CommonRequest<UpdateLinkInfoRequest> request) {
        LinkInfoUpdateDto updateDto = LinkInfoMapper.updateRequestToUpdateDto(request.body());
        LinkInfo linkInfo = linkService.update(updateDto);
        LinkInfoResponse response = LinkInfoMapper.modelToResponse(linkInfo);

        return CommonResponse.of(response);
    }

    @Override
    public CommonResponse<GetAllLinkInfoResponse> getByFilter() {
        GetAllLinkInfoResponse response = linkService.getAllLinks().stream()
                .map(LinkInfoMapper::modelToResponse)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        GetAllLinkInfoResponse::new
                ));

        return CommonResponse.of(response);
    }
}
