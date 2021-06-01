//package com.backend.fakedb.controllers;
//
//import com.backend.fakedb.entities.ProviderEntity;
//import com.backend.fakedb.entities.UserEntity;
//import com.backend.fakedb.entities.UserPreferencesEntity;
//import com.backend.fakedb.entities.UserPreferencesPK;
//import com.backend.fakedb.services.UserPreferencesService;
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
//@WebMvcTest(value = UserPreferencesController.class)
//class UserPreferencesControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserPreferencesService upService;
//
//    @Test
//    void getAll() throws Exception {
//        var user = new UserEntity(1, "username", "avatarUrl", "bio", "email");
//        var provider = new ProviderEntity(1, "digi", 90.0, "example.com");
//        var userPrefsID = new UserPreferencesPK(user, provider);
//        var userPrefs = Collections.singletonList(new UserPreferencesEntity(userPrefsID, true));
//
//        Mockito.when(upService.getPreferences()).thenReturn(userPrefs);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/preferences/getAll")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//        Mockito.verify(upService, Mockito.times(1)).getPreferences();
//
//        String expected = "[{\"id\":{\"userID\":1,\"providerID\":1},\"subscribed\":true,\"userID\":1,\"providerID\":1}]";
//        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
//    }
//
//    @Test
//    void isSubscribed() throws Exception {
//        Mockito.when(upService.isSubscribed(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/preferences/isSubscribed?uid=1&prov_id=1")
//                .accept(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(requestBuilder);
//        Mockito.verify(upService, Mockito.times(1)).isSubscribed(1, 1);
//    }
//
//    @Test
//    void getByUserId() throws Exception {
//        var provider = new ProviderEntity(1, "digi", 90.0, "example.com");
//
//        Mockito.when(upService.getProviderListForUser(1, 1, 1))
//                .thenReturn(Collections.singletonList(provider));
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/preferences/getByUserId?uid=1&skip=1&count=1")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//        Mockito.verify(upService, Mockito.times(1)).getProviderListForUser(1, 1, 1);
//
//        String expected = "[{\"id\":1,\"name\":\"digi\",\"credibility\":90.0,\"avatar\":\"example.com\"}]";
//        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
//    }
//
//    @Test
//    void subscribeUserToProviders() throws Exception {
//        String providerIDsJSON = "[1, 2, 3, 4, 5]";
//
//        Mockito.when(upService.subscribeUserToProviders(1, Arrays.asList(1, 2, 3, 4, 5))).thenReturn(true);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/preferences/subscribeUserToProviders?uid=1")
//                .content(providerIDsJSON).contentType(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(requestBuilder);
//        Mockito.verify(upService, Mockito.times(1)).subscribeUserToProviders(1, Arrays.asList(1, 2, 3, 4, 5));
//    }
//
//    @Test
//    void updateSubscriptionStatus() throws Exception {
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .put("/preferences/updateSubscriptionStatus?uid=1&prov_id=1&status=true");
//
//        mockMvc.perform(requestBuilder);
//        Mockito.verify(upService, Mockito.times(1)).updateSubscriptionStatus(1, 1, true);
//    }
//}