package ilya.service.linkshortener.service.impl;

import ilya.service.linkshortener.config.properties.LinkInfoProperties;
import ilya.service.linkshortener.dto.CreateLinkInfoRequest;
import ilya.service.linkshortener.dto.CreateLinkInfoResponse;
import ilya.service.linkshortener.dto.GetAllLinkInfoResponse;
import ilya.service.linkshortener.dto.GetLinkInfoResponse;
import ilya.service.linkshortener.model.LinkInfo;
import ilya.service.linkshortener.repository.LinkInfoRepository;
import ilya.service.linkshortener.utils.CreateLinkInfoRequestMotherObject;
import ilya.service.linkshortener.utils.GetLinkInfoResponseMotherObject;
import ilya.service.linkshortener.utils.LinkInfoMotherObject;
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
        var reqDtoCreator = new CreateLinkInfoRequestMotherObject();
        CreateLinkInfoRequest request = reqDtoCreator.random().build();
        //todo вынести
        int baseShortLinkLength = properties.getShortLinkLength();
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
        CreateLinkInfoResponse actualResponse = linkService.createLinkInfo(request);
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
        int baseShortLinkLength = properties.getShortLinkLength();
        String shortLink = RandomStringUtils.randomAlphanumeric(baseShortLinkLength);
        LinkInfo linkInfo = new LinkInfoMotherObject()
                .random()
                .setShortLink(shortLink)
                .build();

        GetLinkInfoResponse expectedResponse = new GetLinkInfoResponseMotherObject()
                .setId(linkInfo.getId())
                .setShortLink(linkInfo.getShortLink())
                .setOpeningCount(linkInfo.getOpeningCount())
                .setLink(linkInfo.getLink())
                .setEndTime(linkInfo.getEndTime())
                .setDescription(linkInfo.getDescription())
                .setIsActive(linkInfo.getIsActive())
                .build();

        //when
        when(linkInfoRepositoryImpl.findByShortLink(shortLink))
                .thenReturn(Optional.of(linkInfo));
        GetLinkInfoResponse actualResponse = linkService.getByShortLink(shortLink);

        //then
        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    void whenFindByFilter_thenReturnAllEntities() {
        //given
        var linkInfoCreator = new LinkInfoMotherObject();
        List<LinkInfo> links = new ArrayList<>() {{
            add(linkInfoCreator.random().build());
            add(linkInfoCreator.random().build());
            add(linkInfoCreator.random().build());
        }};
        GetAllLinkInfoResponse expectedResponse = new GetAllLinkInfoResponse(links);

        //when
        when(linkInfoRepositoryImpl.findAll()).thenReturn(links);
        GetAllLinkInfoResponse actualResponse = linkService.findByFilter();
        //then
        assertEquals(expectedResponse.links().size(), actualResponse.links().size());
    }
}