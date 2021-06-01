//package com.backend.fakedb.services;
//
//import com.backend.fakedb.entities.UserEntity;
//import com.backend.fakedb.repositories.UserRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Collections;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @InjectMocks
//    private UserService userService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Test
//    void getAll() {
//        Mockito.when(userRepository.findAll()).thenReturn(Collections.emptyList());
//        var result = userService.getUsers();
//        Assertions.assertEquals(result, Collections.emptyList());
//        Mockito.verify(userRepository, Mockito.times(1)).findAll();
//    }
//
//    @Test
//    void getById() {
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
//        var result = userService.getUserById(1);
//        Assertions.assertEquals(result, Optional.empty());
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//    }
//
//    @Test
//    void registerUser_usernameNotAlreadyUsed() {
//        UserEntity user = new UserEntity();
//        user.setUsername("abc");
//        Mockito.when(userRepository.findAll()).thenReturn(Collections.emptyList());
//
//        var result = userService.registerUser(user);
//        assertTrue(result);
//        Mockito.verify(userRepository, Mockito.times(1)).save(user);
//    }
//
//    @Test
//    void registerUser_usernameAlreadyUsed() {
//        UserEntity user = new UserEntity();
//        user.setUsername("abc");
//        Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
//
//        var result = userService.registerUser(user);
//        assertFalse(result);
//    }
//
//    @Test
//    void deleteUser_userDoesNotExist() {
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
//        var result = userService.deleteUser(1);
//        assertFalse(result);
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//    }
//
//    @Test
//    void deleteUser_userExists() {
//        var user = new UserEntity();
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
//        var result = userService.deleteUser(1);
//        assertTrue(result);
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1);
//    }
//
//    @Test
//    void updateUser_userDoesNotExist() {
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
//        var result = userService.updateUser(1, "sampleBio");
//        assertFalse(result);
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//    }
//
//    @Test
//    void updateUser_userExists() {
//        var user = new UserEntity();
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
//        var result = userService.updateUser(1, "sampleBio");
//        assertTrue(result);
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(userRepository, Mockito.times(1)).update("sampleBio", 1);
//    }
//}