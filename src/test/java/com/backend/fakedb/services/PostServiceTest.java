//package com.backend.fakedb.services;
//
//import com.backend.fakedb.utilities.IngestionLinker;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
//class PostServiceTest {
//
//    @InjectMocks
//    private PostService postService;
//
//    @Test
//    void getInterval() {
//        var linkerMock = Mockito.mock(IngestionLinker.class);
//        Mockito.when(linkerMock.getInterval(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Collections.emptyList());
//
//        postService.setLinker(linkerMock);
//        var result = postService.getInterval(10, 20);
//
//        Assertions.assertEquals(Collections.emptyList(), result);
//        Mockito.verify(linkerMock, Mockito.times(1)).getInterval(10, 20);
//    }
//
//    @Test
//    void getIntervalByProvider() {
//        var linkerMock = Mockito.mock(IngestionLinker.class);
//        Mockito.when(linkerMock.getIntervalByProvider(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(Collections.emptyList());
//
//        postService.setLinker(linkerMock);
//        var result = postService.getIntervalByProvider(5, 10, 20);
//
//        Assertions.assertEquals(Collections.emptyList(), result);
//        Mockito.verify(linkerMock, Mockito.times(1)).getIntervalByProvider(5, 10, 20);
//    }
//}