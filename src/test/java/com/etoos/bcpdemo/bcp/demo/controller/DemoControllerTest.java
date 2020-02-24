package com.etoos.bcpdemo.bcp.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class DemoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private static String rootUrl;

    @BeforeAll
    static void beforeAll() {
        rootUrl = "/demo";
    }

    @Test
    void createEntity() throws Exception {
        String entityJson = "{\"name\":\"incheol\"}";
        this.mockMvc.perform(post(rootUrl).content(entityJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void findEntity() throws Exception {
        this.mockMvc.perform(get(rootUrl + "/1"))
                .andDo(print())
                .andExpect(status().isNotFound()); // 데이터 없어서 404
    }

    @Test
    void updateEntity() throws Exception {
        this.mockMvc.perform(put(rootUrl + "/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteEntity() throws Exception {
        this.mockMvc.perform(delete(rootUrl + "/1"))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    void createEntityValidationTest() throws Exception {
        this.mockMvc.perform(post("/demo")
                .param("id", "-1").param("name", ""))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    void listResponseEntityTest() throws Exception {
        this.mockMvc.perform(get("/demo/test"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}