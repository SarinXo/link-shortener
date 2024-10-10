package ilya.service.linkshortener.service.impl;

import ilya.service.linkshortener.dto.CreateLinkInfoRequest;
import ilya.service.linkshortener.dto.CreateLinkInfoResponse;
import ilya.service.linkshortener.model.LinkInfo;
import ilya.service.linkshortener.utils.CreateLinkInfoRequestMotherObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class LinkServiceImplTest {
    @InjectMocks
    private LinkServiceImpl linkService;

    @Test
    @DisplayName("Корректный вызов метода LinkServiceImpl#shortenLink()")
    void whenShortenLinkCalled_thenNewLinkAddedInMap() throws IllegalAccessException, NoSuchFieldException {
        //given
        var reqDtoCreator = new CreateLinkInfoRequestMotherObject();
        CreateLinkInfoRequest request = reqDtoCreator.random().build();

        Map<String, LinkInfo> shortLinkStorage = getLinkMap();
        int baseShortLinkLength = getBaseShortLinkLength();

        //when
        CreateLinkInfoResponse actualResponse1 = linkService.createLinkInfo(request);
        CreateLinkInfoResponse actualResponse2 = linkService.createLinkInfo(request);

        //then
        String shortLink1 = actualResponse1.shortLink();
        String shortLink2 = actualResponse2.shortLink();

        assertEquals(baseShortLinkLength, shortLink1.length());
        assertEquals(baseShortLinkLength, shortLink2.length());

        assertTrue(shortLinkStorage.containsKey(actualResponse1.shortLink()));
        assertTrue(shortLinkStorage.containsKey(actualResponse2.shortLink()));

        LinkInfo linkInfo1 = shortLinkStorage.get(shortLink1);
        LinkInfo linkInfo2 = shortLinkStorage.get(shortLink2);

        assertNotEquals(shortLink1, shortLink2);

        assertEquals(linkInfo1.getShortLink(), shortLink1);
        assertEquals(linkInfo1.getLink(), request.link());
        assertEquals(linkInfo1.getEndTime(), request.endTime());
        assertEquals(linkInfo1.getDescription(), request.description());
        assertEquals(linkInfo1.getIsActive(), request.isActive());

        assertEquals(linkInfo2.getShortLink(), shortLink2);
        assertEquals(linkInfo2.getLink(), request.link());
        assertEquals(linkInfo2.getEndTime(), request.endTime());
        assertEquals(linkInfo2.getDescription(), request.description());
        assertEquals(linkInfo2.getIsActive(), request.isActive());

    }

    private int getBaseShortLinkLength() throws IllegalAccessException, NoSuchFieldException {
        Field field = LinkServiceImpl.class.getDeclaredField("BASE_SHORT_LINK_LENGTH");
        field.setAccessible(true);
        return (int) field.get(null);
    }

    private Map<String, LinkInfo> getLinkMap() throws IllegalAccessException, NoSuchFieldException {
        Field storageField = LinkServiceImpl.class.getDeclaredField("shortLinkStorage");
        storageField.setAccessible(true);
        return (Map<String, LinkInfo>) storageField.get(null);
    }

}