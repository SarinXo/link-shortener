package ilya.service.linkshortener.controller.api.v1;

import ilya.service.linkshortener.dto.GetAllLinkInfoResponse;
import ilya.service.linkshortener.dto.LinkInfoRequest;
import ilya.service.linkshortener.dto.LinkInfoResponse;
import ilya.service.linkshortener.dto.UpdateLinkInfoRequest;
import ilya.service.linkshortener.dto.wrapper.CommonRequest;
import ilya.service.linkshortener.dto.wrapper.CommonResponse;
import ilya.service.linkshortener.service.impl.LinkServiceImpl;
import ilya.service.linkshortener.utils.GetAllLinkInfoResponseUtils;
import ilya.service.linkshortener.utils.LinkInfoRequestUtils;
import ilya.service.linkshortener.utils.LinkInfoResponseUtils;
import ilya.service.linkshortener.utils.UpdateLinkInfoRequestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.format.DateTimeFormatter;

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


@WebMvcTest(LinkRestController.class)
class LinkRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LinkServiceImpl linkServiceImpl;

    @Test
    @DisplayName("Корректный вызов метода LinkRestController#createShortLink()")
    void whenCreateShortLink_thenReturnNewLinkInDto() throws Exception {
        //given
        LinkInfoRequest linkInfoRequest = LinkInfoRequestUtils.random().build();
        CommonRequest<LinkInfoRequest> request = CommonRequest.of(linkInfoRequest);
        LinkInfoResponse linkInfoResponse = LinkInfoResponseUtils.random().build();
        CommonResponse<LinkInfoResponse> response = CommonResponse.of(linkInfoResponse);

        //when
        when(linkServiceImpl.createLinkInfo(any())).thenReturn(linkInfoResponse);
        mockMvc.perform(post("/api/v1/links/shorten")
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
    @DisplayName("Корректный вызов метода LinkRestController#updateLink()")
    void whenUpdateLink_thenReturnUpdatedLink() throws Exception {
        //given
        UpdateLinkInfoRequest updateRequest = UpdateLinkInfoRequestUtils.random().build();
        CommonRequest<UpdateLinkInfoRequest> request = CommonRequest.of(updateRequest);
        LinkInfoResponse linkInfoResponse = LinkInfoResponseUtils.random().build();
        CommonResponse<LinkInfoResponse> response = CommonResponse.of(linkInfoResponse);

        //when
        when(linkServiceImpl.update(request)).thenReturn(linkInfoResponse);

        mockMvc.perform(put("/api/v1/links")
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
    @DisplayName("Корректный вызов метода LinkRestController#getWithFilter()")
    void whenGetWithFilter_thenReturn3Links() throws Exception {
        GetAllLinkInfoResponse getAllLinkInfoResponse = GetAllLinkInfoResponseUtils.random(3).build();

        when(linkServiceImpl.findByFilter()).thenReturn(getAllLinkInfoResponse);

        mockMvc.perform(get("/api/v1/links/filter"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.links").exists())
                .andExpect(jsonPath("$.body.links", hasSize(3)));
    }
}