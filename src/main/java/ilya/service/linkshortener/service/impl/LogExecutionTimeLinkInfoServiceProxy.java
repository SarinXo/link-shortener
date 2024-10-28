package ilya.service.linkshortener.service.impl;


import ilya.service.linkshortener.dto.GetAllLinkInfoResponse;
import ilya.service.linkshortener.dto.LinkInfoRequest;
import ilya.service.linkshortener.dto.LinkInfoResponse;
import ilya.service.linkshortener.dto.UpdateLinkInfoRequest;
import ilya.service.linkshortener.service.LinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class LogExecutionTimeLinkInfoServiceProxy implements LinkService {

    private final LinkService linkServiceImpl;

    @Override
    public LinkInfoResponse createLinkInfo(LinkInfoRequest requestDto) {
        var st = new StopWatch();
        st.start();
        try {
            return linkServiceImpl.createLinkInfo(requestDto);
        } finally {
            st.stop();
            log.info("Время выполнения метода: {} ms", st.getTotalTimeMillis());
        }
    }

    @Override
    public LinkInfoResponse getByShortLink(String shortLink) {
        var st = new StopWatch();
        st.start();
        try {
            return linkServiceImpl.getByShortLink(shortLink);
        } finally {
            st.stop();
            log.info("Время выполнения метода: {} ms", st.getTotalTimeMillis());
        }
    }

    @Override
    public GetAllLinkInfoResponse findByFilter() {
        var st = new StopWatch();
        st.start();
        try {
            return linkServiceImpl.findByFilter();
        } finally {
            st.stop();
            log.info("Время выполнения метода: {} ms", st.getTotalTimeMillis());
        }
    }

    @Override
    public void delete(UUID id) {
        var st = new StopWatch();
        st.start();
        try {
            linkServiceImpl.delete(id);
        } finally {
            st.stop();
            log.info("Время выполнения метода: {} ms", st.getTotalTimeMillis());
        }
    }

    @Override
    public LinkInfoResponse update(UpdateLinkInfoRequest updateLinkInfoRequest) {
        var st = new StopWatch();
        st.start();
        try {
            return linkServiceImpl.update(updateLinkInfoRequest);
        } finally {
            st.stop();
            log.info("Время выполнения метода: {} ms", st.getTotalTimeMillis());
        }
    }

}
