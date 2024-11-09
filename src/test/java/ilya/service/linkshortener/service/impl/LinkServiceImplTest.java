package ilya.service.linkshortener.service.impl;

import ilya.service.linkshortener.config.properties.LinkInfoProperties;
import ilya.service.linkshortener.dto.GetAllLinkInfoResponse;
import ilya.service.linkshortener.dto.LinkInfoRequest;
import ilya.service.linkshortener.dto.LinkInfoResponse;
import ilya.service.linkshortener.dto.UpdateLinkInfoRequest;
import ilya.service.linkshortener.dto.wrapper.CommonRequest;
import ilya.service.linkshortener.maper.LinkInfoMapper;
import ilya.service.linkshortener.model.LinkInfo;
import ilya.service.linkshortener.repository.LinkInfoRepository;
import ilya.service.linkshortener.service.LinkService;
import ilya.service.linkshortener.utils.LinkInfoRequestUtils;
import ilya.service.linkshortener.utils.LinkInfoUtils;
import ilya.service.linkshortener.utils.UpdateLinkInfoRequestUtils;
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
    private LinkService linkService;
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
        LinkInfoResponse actualResponse = linkService.createLinkInfo(CommonRequest.of(request));
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
    @DisplayName("Корректный вызов метода LinkServiceImpl#findByFilter()")
    void whenFindByFilter_thenReturnAllEntities() {
        //given
        LinkInfo entity = LinkInfoUtils.random().build();

        //when
        when(linkInfoRepositoryImpl.findByShortLink(entity.getShortLink()))
                .thenReturn(Optional.of(entity));
        String link = linkService.getTargetLink(entity.getShortLink());

        //then
        assertEquals(entity.getLink(), link);
    }

    @Test
    @DisplayName("Корректный вызов метода LinkServiceImpl#update()")
    void whenUpdate_thenReturnCorrectResponse() {
        //given
        UpdateLinkInfoRequest updateRequest = UpdateLinkInfoRequestUtils.random().build();
        LinkInfo entity = LinkInfoUtils.random().build();

        //when
        when(linkInfoRepositoryImpl.findById(updateRequest.id()))
                .thenReturn(Optional.of(entity));
        LinkInfoResponse response = linkService.update(CommonRequest.of(updateRequest));

        //then
        assertEquals(updateRequest.link(), response.link());
        assertEquals(updateRequest.endTime(), response.endTime());
        assertEquals(updateRequest.description(), response.description());
        assertEquals(updateRequest.isActive(), response.isActive());
    }

    @Test
    @DisplayName("Корректный вызов метода LinkServiceImpl#getTargetLink()")
    void whenGetTargetLink_thenReturnLink() {
        //given
        String shortLink = RandomStringUtils.randomAlphanumeric(properties.shortLinkLength());
        LinkInfo entity = LinkInfoUtils.random().shortLink(shortLink).build();
        //when
        when(linkInfoRepositoryImpl.findByShortLink(entity.getShortLink()))
                .thenReturn(Optional.of(entity));
        String link = linkService.getTargetLink(entity.getShortLink());

        //then
        assertEquals(entity.getLink(), link);
        assertEquals(entity.getShortLink(), shortLink);
    }

}