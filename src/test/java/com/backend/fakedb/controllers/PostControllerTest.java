//package com.backend.fakedb.controllers;
//
//import com.backend.fakedb.services.PostService;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.Collections;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = PostController.class)
//class PostControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private PostService postService;
//
//    @Test
//    void getInterval() throws Exception {
//        Mockito.when(postService.getInterval(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Collections.emptyList());
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/post/getInterval?skip=100&count=100")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//        Mockito.verify(postService, Mockito.times(1)).getInterval(100, 100);
//
//        String expected = "[]";
//        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
//    }
//
//    @Test
//    void getIntervalByProvider() throws Exception {
//        Mockito.when(postService.getIntervalByProvider(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
//                .thenReturn(Collections.emptyList());
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/post/getIntervalByProvider?provider_id=69&skip=420&count=1337")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//        Mockito.verify(postService, Mockito.times(1)).getIntervalByProvider(69, 420, 1337);
//
//        String expected = "[]";
//        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
//    }
//}