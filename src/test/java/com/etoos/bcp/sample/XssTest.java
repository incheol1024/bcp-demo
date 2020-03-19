package com.etoos.bcp.sample;

import lombok.With;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class XssTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Xss Resquest Body 테스트")
    @WithMockUser(roles = "USER")
//    @PreAuthorize("authenticated")
    void xssParamValidateTest() throws Exception {
        this.mockMvc.perform(get("/user").param("userName", "<html></html>"))
                .andDo(print())
                .andExpect(status().isOk())
        ;

    }

    @Test
    @WithMockUser
    void xssBodyValidateTest() throws Exception {
        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\":\"<script></script>\"}"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void loginTest() throws Exception {
        this.mockMvc
                .perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("username=incheol&password=pass"))
                .andDo(print());
    }

}
