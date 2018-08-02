package com.zss.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.zss.service.HelloService;



@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

  private MockMvc mvc;
  
  
  @InjectMocks
  private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

  @MockBean
  private HelloService helloService;

  @Before
  public void setUp() throws Exception {

    // request = null;
    this.mvc = MockMvcBuilders.standaloneSetup(new UserController())//
        .setCustomArgumentResolvers(pageableArgumentResolver)//
        .build();

  }

  @Test
  public void testHello() throws Exception {
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
  public void testGetUser() throws Exception {
    RequestBuilder request = get("/user/1")//
        .accept(MediaType.APPLICATION_JSON)//
        .contentType(MediaType.APPLICATION_JSON)//
    ;//

    mvc.perform(request)//
        .andDo(MockMvcResultHandlers.print())//
        .andExpect(status().isOk())//
        .andExpect(jsonPath("$.username").value("test"))//
        .andReturn();
  }

  @Test
  public void testGetUserFail() throws Exception {
    RequestBuilder request = get("/user/a")//
        .accept(MediaType.APPLICATION_JSON)//
        .contentType(MediaType.APPLICATION_JSON)//
    ;//

    mvc.perform(request)//
        .andDo(MockMvcResultHandlers.print())//
        .andExpect(status().is4xxClientError())//
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

  @Test
  public void testPutUser() throws Exception {
    Date date = new Date(LocalDateTime.now().plusYears(-1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
//    Date today = new Date();
    
    RequestBuilder request = put("/user/1")//
        .accept(MediaType.APPLICATION_JSON)//
        .contentType(MediaType.APPLICATION_JSON)//
        .content("{\"id\":1,\"username\":\"tom\",\"password\":null,\"birthday\":"+date.getTime()+"}")
    ;//

    mvc.perform(request)//
        .andDo(MockMvcResultHandlers.print())//
        .andExpect(status().isOk())//
        .andExpect(jsonPath("$.id").value(1))
        .andReturn();
  }

  @Test
  public void testDelUser() throws Exception {
    
    RequestBuilder request = delete("/user/1")//
        .accept(MediaType.APPLICATION_JSON)//
    ;//

    mvc.perform(request)//
        .andDo(MockMvcResultHandlers.print())//
        .andExpect(status().isOk())//
        .andReturn();
  }
}
