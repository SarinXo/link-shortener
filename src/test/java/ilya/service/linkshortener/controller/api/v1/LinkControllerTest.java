package ilya.service.linkshortener.controller.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import ilya.service.linkshortener.config.AppTestConfiguration;
import ilya.service.linkshortener.dto.controller.request.LinkInfoFilterRequest;
import ilya.service.linkshortener.dto.controller.request.LinkInfoUpdateRequest;
import ilya.service.linkshortener.dto.controller.response.LinkInfoFilterResponse;
import ilya.service.linkshortener.dto.controller.request.LinkInfoRequest;
import ilya.service.linkshortener.dto.controller.response.LinkInfoResponse;
import ilya.service.linkshortener.dto.wrapper.CommonRequest;
import ilya.service.linkshortener.dto.wrapper.CommonResponse;
import ilya.service.linkshortener.service.LinkAdapterService;
import ilya.service.linkshortener.service.LinkService;
import ilya.service.linkshortener.utils.LinkInfoRequestUtils;
import ilya.service.linkshortener.utils.LinkInfoResponseUtils;
import ilya.service.linkshortener.utils.UpdateLinkInfoRequestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ilya.service.linkshortener.utils.TestUtils.toJson;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(AppTestConfiguration.class)
@WebMvcTest(LinkController.class)
class LinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LinkAdapterService linkAdapterService;

    @MockBean
    private LinkService linkService;

    @Test
    @DisplayName("Корректный вызов метода LinkController#createShortLink()")
    void whenCreateShortLink_thenReturnNewLinkInDto() throws Exception {
        //given
        LinkInfoRequest linkInfoRequest = LinkInfoRequestUtils.random().build();
        CommonRequest<LinkInfoRequest> request = CommonRequest.of(linkInfoRequest);
        LinkInfoResponse linkInfoResponse = LinkInfoResponseUtils.random().build();
        CommonResponse<LinkInfoResponse> response = CommonResponse.of(linkInfoResponse);

        //when
        when(linkAdapterService.create(any())).thenReturn(response);
        mockMvc.perform(post("/api/v1/link-infos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.id").value(response.body().id().toString()))
                .andExpect(jsonPath("$.body.shortLink").value(response.body().shortLink()))
                .andExpect(jsonPath("$.body.openingCount").value(response.body().openingCount()))
                .andExpect(jsonPath("$.body.link").value(response.body().link()))
                .andExpect(jsonPath("$.body.endTime").value(response.body().endTime().format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(jsonPath("$.body.description").value(response.body().description()))
                .andExpect(jsonPath("$.body.isActive").value(response.body().isActive()));
    }

    @Test
    @DisplayName("Корректный вызов метода LinkController#updateLink()")
    void whenUpdateLink_thenReturnUpdatedLink() throws Exception {
        //given
        LinkInfoUpdateRequest updateRequest = UpdateLinkInfoRequestUtils.random().build();
        CommonRequest<LinkInfoUpdateRequest> request = CommonRequest.of(updateRequest);
        LinkInfoResponse linkInfoResponse = LinkInfoResponseUtils.random().build();
        CommonResponse<LinkInfoResponse> response = CommonResponse.of(linkInfoResponse);

        //when
        when(linkAdapterService.update(request)).thenReturn(response);

        mockMvc.perform(put("/api/v1/link-infos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.id").value(response.body().id().toString()))
                .andExpect(jsonPath("$.body.shortLink").value(response.body().shortLink()))
                .andExpect(jsonPath("$.body.openingCount").value(response.body().openingCount()))
                .andExpect(jsonPath("$.body.link").value(response.body().link()))
                .andExpect(jsonPath("$.body.endTime").value(response.body().endTime().format(DateTimeFormatter.ISO_DATE_TIME)))
                .andExpect(jsonPath("$.body.description").value(response.body().description()))
                .andExpect(jsonPath("$.body.isActive").value(response.body().isActive()));
    }

    @Test
    @DisplayName("Корректный вызов метода LinkController#getWithFilter()")
    void whenGetWithFilter_thenReturn3Links() throws Exception {
        //given
        LinkInfoResponse responseDto = LinkInfoResponse.builder()
                .shortLink("1test")
                .description("1testDescription")
                .endTime(LocalDateTime.of(2000, 2, 2, 2, 2))
                .isActive(true)
                .build();

        LinkInfoFilterRequest linkInfoFilterRequest = LinkInfoFilterRequest.builder()
                .linkPart("test")
                .descriptionPart("testDescription")
                .fromEndTime(LocalDateTime.of(2000, 2, 2, 2, 1))
                .toEndTime(LocalDateTime.of(2000, 2, 2, 2, 3))
                .isActive(true)
                .build();

        CommonRequest<LinkInfoFilterRequest> request = CommonRequest.of(linkInfoFilterRequest);
        CommonResponse<LinkInfoFilterResponse> response = CommonResponse.of(new LinkInfoFilterResponse(List.of(responseDto)));
        //when
        when(linkAdapterService.getByFilter(request)).thenReturn(response);
        //then
        mockMvc.perform(post("/api/v1/link-infos/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.links").exists())
                .andExpect(jsonPath("$.body.links", hasSize(1)))
                .andExpect(jsonPath("$.body.links[0].shortLink").value(responseDto.shortLink()))
                .andExpect(jsonPath("$.body.links[0].description").value(responseDto.description()))
                .andExpect(jsonPath("$.body.links[0].isActive").value(responseDto.isActive()));
    }
}