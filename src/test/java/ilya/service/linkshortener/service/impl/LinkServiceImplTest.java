package ilya.service.linkshortener.service.impl;

import ilya.service.linkshortener.dto.CreateLinkInfoRequestDto;
import ilya.service.linkshortener.dto.CreateLinkInfoResponseDto;
import ilya.service.linkshortener.dto.LinkInfoDto;
import ilya.service.linkshortener.maper.LinkInfoMapper;
import ilya.service.linkshortener.utils.CreateLinkInfoRequestMotherObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkServiceImplTest {
    @InjectMocks
    private LinkServiceImpl linkService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Корректный вызов метода LinkServiceImpl#shortenLink()")
    void whenShortenLinkCalled_thenNewLinkAddedInMap() throws IllegalAccessException, NoSuchFieldException {
        //given
        var reqDtoCreator = new CreateLinkInfoRequestMotherObject();
        CreateLinkInfoRequestDto request = reqDtoCreator.random().build();

        Map<String, LinkInfoDto> shortLinkStorage = getLinkMap();
        int baseShortLinkLength = getBaseShortLinkLength();
        String keyShortLink = RandomStringUtils.randomAlphanumeric(baseShortLinkLength);

        LinkInfoDto info = new LinkInfoDto(
                request.link(),
                request.endTime(),
                request.description(),
                request.isActive()
        );

        CreateLinkInfoResponseDto expectedResponse = new CreateLinkInfoResponseDto(keyShortLink);

        //when
        mockStatic(RandomStringUtils.class)
                .when(() -> RandomStringUtils.randomAlphanumeric(baseShortLinkLength))
                .thenReturn(keyShortLink);

        mockStatic(LinkInfoMapper.class)
                .when(() -> LinkInfoMapper.requestToDto(request))
                .thenReturn(info);

        CreateLinkInfoResponseDto actualResponse = linkService.shortenLink(request);

        //then
        assertEquals(expectedResponse, actualResponse);
        assertTrue(shortLinkStorage.containsKey(keyShortLink));
        assertEquals(info, shortLinkStorage.get(keyShortLink));
    }

    private int getBaseShortLinkLength() throws IllegalAccessException, NoSuchFieldException {
        Field field = LinkServiceImpl.class.getDeclaredField("BASE_SHORT_LINK_LENGTH");
        field.setAccessible(true);
        return (int) field.get(null);
    }

    private Map<String, LinkInfoDto> getLinkMap() throws IllegalAccessException, NoSuchFieldException {
        Field storageField = LinkServiceImpl.class.getDeclaredField("shortLinkStorage");
        storageField.setAccessible(true);
        return (Map<String, LinkInfoDto>) storageField.get(null);
    }

}