package ilya.service.linkshortener.service;

import ilya.service.linkshortener.dto.controller.request.LinkInfoFilterRequest;
import ilya.service.linkshortener.dto.controller.request.LinkInfoRequest;
import ilya.service.linkshortener.dto.controller.request.LinkInfoUpdateRequest;
import ilya.service.linkshortener.dto.controller.response.LinkInfoFilterResponse;
import ilya.service.linkshortener.dto.controller.response.LinkInfoResponse;
import ilya.service.linkshortener.dto.wrapper.CommonRequest;
import ilya.service.linkshortener.dto.wrapper.CommonResponse;
import org.springframework.data.domain.Pageable;

public interface LinkAdapterService {
    CommonResponse<LinkInfoResponse> create(CommonRequest<LinkInfoRequest> requestDto);

    CommonResponse<LinkInfoResponse> update(CommonRequest<LinkInfoUpdateRequest> request);

    CommonResponse<LinkInfoFilterResponse> getByFilter(CommonRequest<LinkInfoFilterRequest> request, Pageable pageable);
}
