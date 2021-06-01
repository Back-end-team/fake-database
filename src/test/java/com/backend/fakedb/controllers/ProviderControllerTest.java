//package com.backend.fakedb.controllers;
//
//import com.backend.fakedb.entities.ProviderEntity;
//import com.backend.fakedb.services.ProviderService;
//import com.backend.fakedb.utilities.IntWrapper;
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
//import java.util.Arrays;
//import java.util.Collections;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = ProviderController.class)
//class ProviderControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ProviderService providerService;
//
//    @Test
//    void getAll() throws Exception {
//        var providers = Collections.singletonList(new ProviderEntity(1, "digi", 90.0, "example.com"));
//
//        Mockito.when(providerService.getAll()).thenReturn(providers);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/provider/getAll")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//        Mockito.verify(providerService, Mockito.times(1)).getAll();
//
//        String expected = "[{\"id\":1,\"name\":\"digi\",\"credibility\":90.0, \"avatar\":\"example.com\"}]";
//        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
//    }
//
//
//    @Test
//    void getInterval() throws Exception {
//        var providers = Arrays.asList(
//                new ProviderEntity(1, "digi", 90.0, "example.com"),
//                new ProviderEntity(2, "ziarul de iasi", 80.0, "example.com"));
//
//        Mockito.when(providerService.getInterval(Mockito.anyInt(), Mockito.anyInt()))
//                .thenReturn(Collections.singletonList(providers.get(1)));
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/provider/getInterval?skip=1&count=1")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//        Mockito.verify(providerService, Mockito.times(1)).getInterval(Mockito.anyInt(), Mockito.anyInt());
//
//        String expected = "[{\"id\":2,\"name\":\"ziarul de iasi\",\"credibility\":80.0, \"avatar\":\"example.com\"}]";
//        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
//    }
//
//    @Test
//    void getCount() throws Exception {
//
//        Mockito.when(providerService.getCount()).thenReturn(new IntWrapper(10));
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/provider/getCount")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//        Mockito.verify(providerService, Mockito.times(1)).getCount();
//
//        String expected = "{\"count\":10}";
//        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
//    }
//
//    @Test
//    void searchCount() throws Exception {
//        Mockito.when(providerService.searchCount(Mockito.anyString())).thenReturn(new IntWrapper(10));
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/provider/searchCount?query=digi");
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        Mockito.verify(providerService, Mockito.times(1)).searchCount("digi");
//
//        String expected = "{\"count\":10}";
//        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
//    }
//
//    @Test
//    void search() throws Exception {
//        Mockito.when(providerService.search(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
//                .thenReturn(Collections.emptyList());
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/provider/search?query=digi&skip=1&count=1");
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        Mockito.verify(providerService, Mockito.times(1)).search("digi", 1, 1);
//
//        String expected = "[]";
//        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
//    }
//}