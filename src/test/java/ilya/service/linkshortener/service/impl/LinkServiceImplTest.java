package ilya.service.linkshortener.service.impl;

import ilya.service.linkshortener.config.properties.LinkInfoProperties;
import ilya.service.linkshortener.dto.service.LinkInfoCreateDto;
import ilya.service.linkshortener.dto.service.LinkInfoUpdateDto;
import ilya.service.linkshortener.model.LinkInfoEntity;
import ilya.service.linkshortener.repository.LinkInfoRepository;
import ilya.service.linkshortener.service.LinkService;
import ilya.service.linkshortener.utils.LinkInfoCreateDtoUtils;
import ilya.service.linkshortener.utils.LinkInfoUpdateDtoUtils;
import ilya.service.linkshortener.utils.LinkInfoUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

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
    @DisplayName("Корректный вызов метода LinkServiceImpl#create()")
    void whenCreate_thenReturnCorrectEntity() {
        //given
        LinkInfoCreateDto dto = LinkInfoCreateDtoUtils.random().build();
        int baseShortLinkLength = properties.shortLinkLength();
        String shortLink = RandomStringUtils.randomAlphanumeric(baseShortLinkLength);
        Long openingCount = 0L;

        LinkInfoEntity linkInfo = new LinkInfoEntity(
                UUID.randomUUID(),
                shortLink,
                openingCount,
                dto.link(),
                dto.endTime(),
                dto.description(),
                dto.isActive()
        );

        //when
        when(linkInfoRepositoryImpl.save(any()))
                .thenReturn(linkInfo);
        LinkInfoEntity entity = linkService.create(dto);

        //then
        int shortLinkLength = entity.getShortLink().length();
        assertEquals(baseShortLinkLength, shortLinkLength);

        assertEquals(linkInfo.getShortLink(), shortLink);
        assertEquals(linkInfo.getLink(), dto.link());
        assertEquals(linkInfo.getEndTime(), dto.endTime());
        assertEquals(linkInfo.getDescription(), dto.description());
        assertEquals(linkInfo.getIsActive(), dto.isActive());
    }

    @Test
    @DisplayName("Корректный вызов метода LinkServiceImpl#update()")
    void whenUpdate_thenReturnCorrectEntity() {
        //given
        LinkInfoUpdateDto dto = LinkInfoUpdateDtoUtils.random().build();
        LinkInfoEntity entity = LinkInfoUtils.random().build();

        //when
        when(linkInfoRepositoryImpl.findById(dto.id()))
                .thenReturn(Optional.of(entity));
        when(linkInfoRepositoryImpl.save(entity))
                .thenReturn(entity);
        LinkInfoEntity result = linkService.update(dto);

        //then
        assertEquals(dto.link(), result.getLink());
        assertEquals(dto.endTime(), result.getEndTime());
        assertEquals(dto.description(), result.getDescription());
        assertEquals(dto.isActive(), result.getIsActive());
    }

    @Test
    @DisplayName("Корректный вызов метода LinkServiceImpl#getById()")
    void whenGetById_thenReturnCorrectEntity() {
        //given
        UUID id = UUID.randomUUID();
        LinkInfoEntity entity = LinkInfoUtils.random().id(id).build();

        //when
        when(linkInfoRepositoryImpl.findById(id))
                .thenReturn(Optional.of(entity));
        LinkInfoEntity result = linkService.getById(id);

        //then
        assertEquals(entity, result);
    }

    @Test
    @DisplayName("Корректный вызов метода LinkServiceImpl#getByShortLink()")
    void whenGetByShortLink_thenReturnCorrectEntity() {
        //given
        int baseShortLinkLength = properties.shortLinkLength();
        String shortLink = RandomStringUtils.randomAlphanumeric(baseShortLinkLength);
        LinkInfoEntity expectedEntity = LinkInfoUtils.random()
                .shortLink(shortLink)
                .build();

        //when
        when(linkInfoRepositoryImpl.findActiveLinkByShortLink(shortLink))
                .thenReturn(Optional.of(expectedEntity));
        LinkInfoEntity actualEntity = linkService.getByShortLink(shortLink);

        //then
        assertEquals(expectedEntity, actualEntity);
    }

    @Test
    @DisplayName("Корректный вызов метода LinkServiceImpl#getLinkByShortLink()")
    void whenGetLinkByShortLink_thenReturnAllEntities() {
        //given
        LinkInfoEntity entity = LinkInfoUtils.random().build();

        //when
        when(linkInfoRepositoryImpl.findActiveLinkByShortLink(entity.getShortLink()))
                .thenReturn(Optional.of(entity));
        String link = linkService.getLinkByShortLink(entity.getShortLink());

        //then
        assertEquals(entity.getLink(), link);
    }

    @Test
    @DisplayName("Корректный вызов метода LinkServiceImpl#getAllLinks()")
    void whenGetAllLinks_thenReturnLink() {
        //given
        LinkInfoEntity l1 = LinkInfoEntity.builder().build();
        LinkInfoEntity l2 = LinkInfoEntity.builder().build();
        LinkInfoEntity l3 = LinkInfoEntity.builder().build();

        List<LinkInfoEntity> expected = List.of(l1, l2, l3);

        //when
        when(linkInfoRepositoryImpl.findAll())
                .thenReturn(expected);
        List<LinkInfoEntity> actual = linkService.getLinksByFilter();

        //then
        assertEquals(actual.size(), expected.size());
    }

}