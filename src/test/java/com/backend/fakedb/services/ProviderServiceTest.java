//package com.backend.fakedb.services;
//
//import com.backend.fakedb.entities.ProviderEntity;
//import com.backend.fakedb.repositories.ProviderRepository;
//import com.backend.fakedb.utilities.IntWrapper;
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
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
//class ProviderServiceTest {
//
//    @InjectMocks
//    private ProviderService providerService;
//
//    @Mock
//    private ProviderRepository repository;
//
//    @Test
//    void getAll() {
//        Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());
//        var result = providerService.getAll();
//        Assertions.assertEquals(Collections.emptyList(), result);
//        Mockito.verify(repository, Mockito.times(1)).findAll();
//    }
//
//    @Test
//    void searchCount_emptyList() {
//        Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());
//        var result = providerService.searchCount("query");
//        Assertions.assertEquals(new IntWrapper(0), result);
//    }
//
//    @Test
//    void searchCount() {
//        var provider1 = new ProviderEntity(); provider1.setName("one");
//        var provider2 = new ProviderEntity(); provider2.setName("everyone");
//        var provider3 = new ProviderEntity(); provider3.setName("two");
//        var provider4 = new ProviderEntity(); provider4.setName("thrEE");
//        Mockito.when(repository.findAll()).thenReturn(List.of(provider1, provider2, provider3, provider4));
//        var result = providerService.searchCount("one");
//        Assertions.assertEquals(new IntWrapper(2), result);
//        Mockito.verify(repository, Mockito.times(1)).findAll();
//    }
//
//    @Test
//    void getCount() {
//        Mockito.when(repository.count()).thenReturn(10L);
//        var result = providerService.getCount();
//        Assertions.assertEquals(new IntWrapper(10), result);
//        Mockito.verify(repository, Mockito.times(1)).count();
//    }
//
//    @Test
//    void getInterval_skipBelowZero() {
//        var result = providerService.getInterval(-1, 100);
//        assertNull(result);
//        Mockito.verify(repository, Mockito.never()).findAll();
//    }
//
//    @Test
//    void getInterval_countBelowOne() {
//        var result = providerService.getInterval(100, 0);
//        assertNull(result);
//        Mockito.verify(repository, Mockito.never()).findAll();
//    }
//
//    @Test
//    void getInterval() {
//        var provider1 = new ProviderEntity();
//        var provider2= new ProviderEntity();
//        var provider3 = new ProviderEntity();
//        Mockito.when(repository.findAll()).thenReturn(List.of(provider1, provider2, provider3));
//
//        var result = providerService.getInterval(1, 1);
//        Assertions.assertEquals(List.of(provider2), result);
//        Mockito.verify(repository, Mockito.times(1)).findAll();
//    }
//
//    @Test
//    void search_skipBelowZero() {
//        var result = providerService.search("query", -1, 100);
//        assertNull(result);
//        Mockito.verify(repository, Mockito.never()).findAll();
//    }
//
//    @Test
//    void search_countBelowOne() {
//        var result = providerService.search("query", 100, 0);
//        assertNull(result);
//        Mockito.verify(repository, Mockito.never()).findAll();
//    }
//
//    @Test
//    void search() {
//        var provider1 = new ProviderEntity(); provider1.setName("digi");
//        var provider2 = new ProviderEntity(); provider2.setName("gigi");
//        var provider3 = new ProviderEntity(); provider3.setName("name");
//        Mockito.when(repository.findAll()).thenReturn(List.of(provider1, provider2, provider3));
//
//        var result = providerService.search("igi", 1, 1);
//        Assertions.assertEquals(List.of(provider2), result);
//        Mockito.verify(repository, Mockito.times(1)).findAll();
//    }
//}