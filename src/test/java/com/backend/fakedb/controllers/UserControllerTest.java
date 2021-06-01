//package com.backend.fakedb.controllers;
//
//import com.backend.fakedb.entities.UserEntity;
//import com.backend.fakedb.services.UserService;
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
//import java.util.Optional;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = UserController.class)
//class UserControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    @Test
//    void getAll() throws Exception {
//        var user = new UserEntity(1, "username", "avatarUrl", "bio", "email");
//        var users = Collections.singletonList(user);
//
//        Mockito.when(userService.getUsers()).thenReturn(users);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/getAll")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//        Mockito.verify(userService, Mockito.times(1)).getUsers();
//
//        String expected = "[{\"id\":1,\"username\":\"username\",\"avatarUrl\":\"avatarUrl\", \"bio\":\"bio\", \"email\":\"email\"}]";
//        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
//    }
//
//    @Test
//    void getById() throws Exception {
//        var user = Optional.of(new UserEntity(1, "username", "avatarUrl", "bio", "email"));
//
//        Mockito.when(userService.getUserById(1)).thenReturn(user);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/get?id=1")
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//        Mockito.verify(userService, Mockito.times(1)).getUserById(1);
//
//        String expected = "{\"id\":1,\"username\":\"username\",\"avatarUrl\":\"avatarUrl\", \"bio\":\"bio\", \"email\":\"email\"}";
//        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
//    }
//
//    @Test
//    void registerUser() throws Exception {
//        String userJSON = "{\"id\":1,\"username\":\"username\",\"avatarUrl\":\"avatarUrl\", \"bio\":\"bio\", \"email\":\"email\"}";
//        var user = new UserEntity(1, "username", "avatarUrl", "bio", "email");
//
//        Mockito.when(userService.registerUser(user)).thenReturn(true);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
//                .content(userJSON).contentType(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(requestBuilder);
//        Mockito.verify(userService, Mockito.times(1)).registerUser(user);
//    }
//
//    @Test
//    void delete() throws Exception {
//        Mockito.when(userService.deleteUser(1)).thenReturn(true);
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/delete?id=1");
//        mockMvc.perform(requestBuilder);
//        Mockito.verify(userService, Mockito.times(1)).deleteUser(1);
//    }
//
//    @Test
//    void update() throws Exception {
//        Mockito.when(userService.updateUser(1, "I am gigi")).thenReturn(true);
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/update?id=1&bio=I am gigi");
//        mockMvc.perform(requestBuilder);
//        Mockito.verify(userService, Mockito.times(1)).updateUser(1, "I am gigi");
//    }
//}