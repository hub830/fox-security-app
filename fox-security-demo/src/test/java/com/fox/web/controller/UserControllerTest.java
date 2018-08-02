package com.fox.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

  private MockMvc mvc;

  @InjectMocks
  private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
  
  @Before
  public void setUp() throws Exception {
    this.mvc = MockMvcBuilders.standaloneSetup(new UserController())//
        .setCustomArgumentResolvers(pageableArgumentResolver)//
        .build();
  }

  @Test
  public void testList() throws Exception {
    RequestBuilder request = get("/user")//
        .accept(MediaType.APPLICATION_JSON)//
        .contentType(MediaType.APPLICATION_JSON)//
        // .content("{}");
        .param("username", "aaaaaaaa")//
        .param("age", "18")//
        .param("ageTo", "60")//
        .param("xxx", "yyy")//
        .param("size", "15")//
        .param("page", "3")//
        .param("sort", "age,desc")//
    // .param("type", "AGENT");
    ;//

    mvc.perform(request)//
        .andDo(MockMvcResultHandlers.print())//
        .andExpect(status().isOk())//
        // .andExpect(jsonPath("$.accountName").value("aaaaaaaa"))//
        // .andExpect(jsonPath("$.type").value("AGENT"))//
        .andExpect(jsonPath("$.length()").value(3))//
        .andReturn();
  }

  @Test
  public void testPostUser() throws Exception {
    RequestBuilder request = post("/user")//
        .accept(MediaType.APPLICATION_JSON)//
        .contentType(MediaType.APPLICATION_JSON)//
        .content("{\"username\":\"tom\",\"password\":\"123456\",\"birthday\":1531461270000}")
    ;//

    mvc.perform(request)//
        .andDo(MockMvcResultHandlers.print())//
        .andExpect(status().isOk())//
        .andExpect(jsonPath("$.id").value(1))
        .andReturn();
  }
}
