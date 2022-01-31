package com.jpabook.jpashop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
public class TestController {

//  @Autowired
  ObjectMapper objectMapper;

//  @Autowired
  WebApplicationContext webApplicationContext;

  private MockMvc mocvMvc;

  private HttpHeaders httpHeaders;

//  @BeforeEach
  public void setup() {
    this.mocvMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();

    httpHeaders = new HttpHeaders();
    httpHeaders.add(HttpHeaders.AUTHORIZATION, "test 1234");
  }

//  @DisplayName("POST TEST")
//  @Test
  public void postTest() throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("test1", "1");
    map.put("test2", "2");
    String data = objectMapper.writeValueAsString(map);
    mocvMvc.perform(post("/api/post")
                    .contentType("application/json")
                    .headers(httpHeaders)
                    .content(data))
            .andExpect(status().isOk())
            .andDo(print());
  }

//  @DisplayName("GET TEST")
//  @Test
  public void getTest() throws Exception {
    String test1 = "1";
    String test2 = "2";
    mocvMvc.perform(get("/api/post")
                    .contentType("application/json")
                    .headers(httpHeaders)
                    .param("test1", test1)
                    .param("test2", test2))
            .andExpect(status().isOk())
            .andDo(print());
  }

//  @DisplayName("FILE TEST")
//  @Test
  public void fileTest() throws Exception {
    String filePath = "C:/Users/sws/Pictures";
    String fileExt = "jpg";
    mocvMvc.perform(multipart("/api/file")
                    .file(getMultipartFile(filePath, "file_1", fileExt, MediaType.IMAGE_JPEG))
                    .file(getMultipartFile(filePath, "file_2", fileExt, MediaType.IMAGE_JPEG))
                    .param("test1", "1")
                    .param("test2", "2"))
            .andExpect(status().isOk())
            .andDo(print());
  }

  private MockMultipartFile getMultipartFile(String filePath, String fileName, String fileExt, MediaType mediaType) throws IOException {
    FileInputStream fileInputStream = new FileInputStream(filePath + fileName + "." + fileExt);
    return new MockMultipartFile(fileName, fileName + "." + fileExt, mediaType.getType(), fileInputStream);
  }

}