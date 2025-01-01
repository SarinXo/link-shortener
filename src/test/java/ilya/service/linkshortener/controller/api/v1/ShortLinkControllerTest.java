package ilya.service.linkshortener.controller.api.v1;

import ilya.service.linkshortener.config.AppTestConfiguration;
import ilya.service.linkshortener.service.LinkService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(AppTestConfiguration.class)
@WebMvcTest(ShortLinkController.class)
public class ShortLinkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LinkService linkService;

    @Test
    @DisplayName("Корректный вызов метода ShortLinkController#redirectOnLink()")
    void whenCreateShortLink_thenReturnNewLinkInDto() throws Exception {

        //when
        when(linkService.getLinkByShortLink(any())).thenReturn("https://google.com");
        mockMvc.perform(get("/api/v1/short-link/SHORT_LINK"))
                //then
                .andExpect(status().is3xxRedirection());
    }

}