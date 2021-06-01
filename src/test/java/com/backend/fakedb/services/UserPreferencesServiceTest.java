//package com.backend.fakedb.services;
//
//import com.backend.fakedb.entities.ProviderEntity;
//import com.backend.fakedb.entities.UserEntity;
//import com.backend.fakedb.entities.UserPreferencesEntity;
//import com.backend.fakedb.entities.UserPreferencesPK;
//import com.backend.fakedb.repositories.ProviderRepository;
//import com.backend.fakedb.repositories.UserPreferencesRepository;
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
//class UserPreferencesServiceTest {
//
//    @InjectMocks
//    private UserPreferencesService prefService;
//
//    @Mock
//    private UserPreferencesRepository prefRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private ProviderRepository providerRepository;
//
//    @Test
//    void getAll() {
//        Mockito.when(prefRepository.findAll()).thenReturn(Collections.emptyList());
//        var result = prefService.getPreferences();
//        Assertions.assertEquals(Collections.emptyList(), result);
//        Mockito.verify(prefRepository, Mockito.times(1)).findAll();
//    }
//
//    @Test
//    void subscribe_userDoesNotExist() {
//        var provider = new ProviderEntity();
//        Mockito.when(providerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(provider));
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
//
//        var result = prefService.subscribeUserToProviders(1, Collections.singletonList(1));
//        assertFalse(result);
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(providerRepository, Mockito.times(1)).findById(1);
//    }
//
//    @Test
//    void subscribe_providerDoesNotExist() {
//        var user = new UserEntity();
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
//        Mockito.when(providerRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
//
//        var result = prefService.subscribeUserToProviders(1, Collections.singletonList(1));
//        assertFalse(result);
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(providerRepository, Mockito.times(1)).findById(1);
//    }
//
//    @Test
//    void subscribe() {
//        var user = new UserEntity();
//        var provider = new ProviderEntity();
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
//        Mockito.when(providerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(provider));
//
//        var result = prefService.subscribeUserToProviders(1, Collections.singletonList(1));
//        assertTrue(result);
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(providerRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(prefRepository, Mockito.times(1)).save(Mockito.any(UserPreferencesEntity.class));
//    }
//
//    @Test
//    void isSubscribed_userDoesNotExist() {
//        var provider = new ProviderEntity();
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
//        Mockito.when(providerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(provider));
//
//        var result = prefService.isSubscribed(1, 1);
//        assertFalse(result);
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(providerRepository, Mockito.times(1)).findById(1);
//    }
//
//    @Test
//    void isSubscribed_providerDoesNotExist() {
//        var user = new UserEntity();
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
//        Mockito.when(providerRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
//
//        var result = prefService.isSubscribed(1, 1);
//        assertFalse(result);
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(providerRepository, Mockito.times(1)).findById(1);
//    }
//
//    @Test
//    void isSubscribed_entryFoundButEmpty() {
//        var user = new UserEntity();
//        var provider = new ProviderEntity();
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
//        Mockito.when(providerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(provider));
//        Mockito.when(prefRepository.findById(Mockito.any(UserPreferencesPK.class))).thenReturn(Optional.empty());
//
//        var result = prefService.isSubscribed(1, 1);
//        assertFalse(result);
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(providerRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(prefRepository, Mockito.times(1)).findById(Mockito.any(UserPreferencesPK.class));
//    }
//
//    @Test
//    void isSubscribed_userNotSubbed() {
//        var user = new UserEntity();
//        var provider = new ProviderEntity();
//        var upEntity = new UserPreferencesEntity(new UserPreferencesPK(user, provider), false);
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
//        Mockito.when(providerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(provider));
//        Mockito.when(prefRepository.findById(Mockito.any(UserPreferencesPK.class))).thenReturn(Optional.of(upEntity));
//
//        var result = prefService.isSubscribed(1, 1);
//        assertFalse(result);
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(providerRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(prefRepository, Mockito.times(1)).findById(Mockito.any(UserPreferencesPK.class));
//    }
//
//    @Test
//    void isSubscribed_userSubbed() {
//        var user = new UserEntity();
//        var provider = new ProviderEntity();
//        var upEntity = new UserPreferencesEntity(new UserPreferencesPK(user, provider), true);
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
//        Mockito.when(providerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(provider));
//        Mockito.when(prefRepository.findById(Mockito.any(UserPreferencesPK.class))).thenReturn(Optional.of(upEntity));
//
//        var result = prefService.isSubscribed(1, 1);
//        assertTrue(result);
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(providerRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(prefRepository, Mockito.times(1)).findById(Mockito.any(UserPreferencesPK.class));
//    }
//
//    @Test
//    void getProviderListForUser() {
//        Mockito.when(prefRepository.findAll()).thenReturn(Collections.emptyList());
//        Mockito.when(providerRepository.findAll()).thenReturn(Collections.emptyList());
//        var result = prefService.getProviderListForUser(1, 1, 1);
//        Assertions.assertEquals(Collections.emptyList(), result);
//        Mockito.verify(prefRepository, Mockito.times(1)).findAll();
//        Mockito.verify(providerRepository, Mockito.times(1)).findAll();
//    }
//
//    @Test
//    void updatePreferences_userDoesNotExist() {
//        var provider = new ProviderEntity();
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
//        Mockito.when(providerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(provider));
//
//        var result = prefService.updateSubscriptionStatus(1, 1, true);
//        assertFalse(result);
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(providerRepository, Mockito.times(1)).findById(1);
//    }
//
//    @Test
//    void updatePreferences_providerDoesNotExist() {
//        var user = new UserEntity();
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
//        Mockito.when(providerRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
//
//        var result = prefService.updateSubscriptionStatus(1, 1, true);
//        assertFalse(result);
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(providerRepository, Mockito.times(1)).findById(1);
//    }
//
//    @Test
//    void updatePreferences_userWasNotSubbed() {
//        var user = new UserEntity();
//        var provider = new ProviderEntity();
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
//        Mockito.when(providerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(provider));
//        Mockito.when(prefRepository.findById(Mockito.any(UserPreferencesPK.class))).thenReturn(Optional.empty());
//
//        var result = prefService.updateSubscriptionStatus(1, 1, true);
//        assertTrue(result);
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(providerRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(prefRepository, Mockito.times(1)).findById(Mockito.any(UserPreferencesPK.class));
//        Mockito.verify(prefRepository, Mockito.times(1)).save(Mockito.any(UserPreferencesEntity.class));
//    }
//
//    @Test
//    void updatePreferences_userWasSubbed() {
//        var user = new UserEntity();
//        var provider = new ProviderEntity();
//        var upEntity = new UserPreferencesEntity(new UserPreferencesPK(user, provider), true);
//        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(user));
//        Mockito.when(providerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(provider));
//        Mockito.when(prefRepository.findById(Mockito.any(UserPreferencesPK.class))).thenReturn(Optional.of(upEntity));
//
//        var result = prefService.updateSubscriptionStatus(1, 1, false);
//        assertTrue(result);
//        Mockito.verify(userRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(providerRepository, Mockito.times(1)).findById(1);
//        Mockito.verify(prefRepository, Mockito.times(1)).findById(Mockito.any(UserPreferencesPK.class));
//        Mockito.verify(prefRepository, Mockito.times(1)).deleteById(Mockito.any(UserPreferencesPK.class));
//        Mockito.verify(prefRepository, Mockito.times(1)).save(Mockito.any(UserPreferencesEntity.class));
//    }
//}