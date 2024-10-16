package ilya.service.linkshortener.service.impl;

import ilya.service.linkshortener.config.properties.LinkInfoProperties;
import ilya.service.linkshortener.dto.GetAllLinkInfoResponse;
import ilya.service.linkshortener.dto.LinkInfoRequest;
import ilya.service.linkshortener.dto.LinkInfoResponse;
import ilya.service.linkshortener.model.LinkInfo;
import ilya.service.linkshortener.repository.LinkInfoRepository;
import ilya.service.linkshortener.utils.LinkInfoRequestUtils;
import ilya.service.linkshortener.utils.LinkInfoUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@EnableConfigurationProperties(value = LinkInfoProperties.class)
@TestPropertySource("classpath:application-test.yml")
class LinkServiceImplTest {

    @Autowired
    private LinkServiceImpl linkService;
    @Autowired
    private LinkInfoProperties properties;
    @MockBean
    private LinkInfoRepository linkInfoRepositoryImpl;

    @Test
    @DisplayName("Корректный вызов метода LinkServiceImpl#createLinkInfo()")
    void whenCreateLinkInfoCalled_thenReturnResponse() {
        //given
        LinkInfoRequest request = LinkInfoRequestUtils.random().build();
        int baseShortLinkLength = properties.shortLinkLength();
        String shortLink = RandomStringUtils.randomAlphanumeric(baseShortLinkLength);
        Long openingCount = 0L;

        LinkInfo linkInfo = new LinkInfo(
                UUID.randomUUID(),
                shortLink,
                openingCount,
                request.link(),
                request.endTime(),
                request.description(),
                request.isActive()
        );

        //when
        LinkInfoResponse actualResponse = linkService.createLinkInfo(request);
        when(linkInfoRepositoryImpl.save(any()))
                .thenReturn(linkInfo);

        //then
        String shortLink1 = actualResponse.shortLink();
        assertEquals(baseShortLinkLength, shortLink1.length());

        assertEquals(linkInfo.getShortLink(), shortLink);
        assertEquals(linkInfo.getLink(), request.link());
        assertEquals(linkInfo.getEndTime(), request.endTime());
        assertEquals(linkInfo.getDescription(), request.description());
        assertEquals(linkInfo.getIsActive(), request.isActive());
    }

    @Test
    @DisplayName("Корректный вызов метода LinkServiceImpl#getByShortLink()")
    void whenExistingGetShortLinkCalled_thenReturn() {
        //given
        int baseShortLinkLength = properties.shortLinkLength();
        String shortLink = RandomStringUtils.randomAlphanumeric(baseShortLinkLength);
        LinkInfo linkInfo = LinkInfoUtils.random()
                .shortLink(shortLink)
                .build();

        LinkInfoResponse expectedResponse = LinkInfoResponse.builder()
                .id(linkInfo.getId())
                .shortLink(linkInfo.getShortLink())
                .openingCount(linkInfo.getOpeningCount())
                .link(linkInfo.getLink())
                .endTime(linkInfo.getEndTime())
                .description(linkInfo.getDescription())
                .isActive(linkInfo.getIsActive())
                .build();

        //when
        when(linkInfoRepositoryImpl.findByShortLink(shortLink))
                .thenReturn(Optional.of(linkInfo));
        LinkInfoResponse actualResponse = linkService.getByShortLink(shortLink);

        //then
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void whenFindByFilter_thenReturnAllEntities() {
        //given
        List<LinkInfo> links = new ArrayList<>() {{
            add(LinkInfoUtils.random().build());
            add(LinkInfoUtils.random().build());
            add(LinkInfoUtils.random().build());
        }};
        GetAllLinkInfoResponse expectedResponse = new GetAllLinkInfoResponse(links);

        //when
        when(linkInfoRepositoryImpl.findAll()).thenReturn(links);
        GetAllLinkInfoResponse actualResponse = linkService.findByFilter();

        //then
        assertEquals(expectedResponse.links().size(), actualResponse.links().size());
    }

}