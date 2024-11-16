package ilya.service.linkshortener.service;

import ilya.service.linkshortener.dto.GetAllLinkInfoResponse;
import ilya.service.linkshortener.dto.LinkInfoRequest;
import ilya.service.linkshortener.dto.LinkInfoResponse;
import ilya.service.linkshortener.dto.UpdateLinkInfoRequest;
import ilya.service.linkshortener.dto.wrapper.CommonRequest;
import ilya.service.linkshortener.dto.wrapper.CommonResponse;

public interface LinkAdapterService {
    CommonResponse<LinkInfoResponse> create(CommonRequest<LinkInfoRequest> requestDto);

    CommonResponse<LinkInfoResponse> update(CommonRequest<UpdateLinkInfoRequest> request);

    CommonResponse<GetAllLinkInfoResponse> getByFilter();
}
