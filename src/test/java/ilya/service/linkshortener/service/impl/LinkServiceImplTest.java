package ilya.service.linkshortener.service.impl;

import ilya.service.linkshortener.dto.CreateLinkInfoRequest;
import ilya.service.linkshortener.dto.CreateLinkInfoResponse;
import ilya.service.linkshortener.dto.LinkInfo;
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

        LinkInfo info = new LinkInfo(
                request.link(),
                request.endTime(),
                request.description(),
                request.isActive()
        );

        //when
        CreateLinkInfoResponse actualResponse1 = linkService.shortenLink(request);
        CreateLinkInfoResponse actualResponse2 = linkService.shortenLink(request);

        //then
        String shortLink1 = actualResponse1.shortLink();
        String shortLink2 = actualResponse2.shortLink();

        assertEquals(shortLink1.length(), baseShortLinkLength);
        assertEquals(shortLink2.length(), baseShortLinkLength);

        assertNotEquals(actualResponse1, actualResponse2);
        assertNotEquals(shortLink1, shortLink2);

        assertTrue(shortLinkStorage.containsKey(actualResponse1.shortLink()));
        assertTrue(shortLinkStorage.containsKey(actualResponse2.shortLink()));

        assertEquals(info, shortLinkStorage.get(shortLink1));
        assertEquals(info, shortLinkStorage.get(shortLink2));
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