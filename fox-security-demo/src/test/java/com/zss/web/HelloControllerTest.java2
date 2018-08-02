package com.zss.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloControllerTest {

  private MockMvc mvc;

  @Before
  public void setUp() throws Exception {

    // request = null;
    this.mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();

  }

  @Test
  public void testHello() throws Exception {
    RequestBuilder request = get("/hello")//
        .accept(MediaType.APPLICATION_JSON);//
    // .contentType(MediaType.APPLICATION_JSON)//
    // .content("{}");
    // .param("accountName", "aaaaaaaa")//
    // .param("type", "AGENT");

    mvc.perform(request)//
        .andDo(MockMvcResultHandlers.print())//
        .andExpect(status().isOk())//
        // .andExpect(jsonPath("$.accountName").value("aaaaaaaa"))//
        // .andExpect(jsonPath("$.type").value("AGENT"))//
        // .andExpect(jsonPath("$.id").isNotEmpty())//
        .andReturn();
  }

}
