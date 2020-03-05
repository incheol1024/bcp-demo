package com.etoos.bcpdemo.bcp.demo.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@TestMethodOrder(OrderAnnotation.class)
class DemoControllerTest {

    @Autowired
    MockMvc mockMvc;

    private static String rootUrl;

    @BeforeAll
    static void beforeAll() {
        rootUrl = "/demo";
    }

    @Test
    @Order(0)
    void createDemo() throws Exception {
        String entityJson = "{\"name\":\"incheol\"}";
        this.mockMvc.perform(post(rootUrl).content(entityJson).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(1)
    void findDemo() throws Exception {
        this.mockMvc.perform(get(rootUrl + "/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void updateDemo() throws Exception {
        this.mockMvc.perform(put(rootUrl + "/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    void deleteDemo() throws Exception {
        this.mockMvc.perform(delete(rootUrl + "/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    void createEntityValidationTest() throws Exception {
        this.mockMvc.perform(post("/demo").contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("id", "-1").param("name", ""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}