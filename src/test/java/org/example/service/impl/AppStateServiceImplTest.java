package org.example.service.impl;

import org.example.service.PersistenceService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppStateServiceImplTest {

//    @Mock
//    private InMemoryBookingRepository bookingRepository;
//    @Mock
//    private InMemoryWorkSpaceRepository workSpaceRepository;
    @Mock
    private PersistenceService persistenceService;
    @InjectMocks
    private AppStateServiceImpl appStateService;


//    @Test
//    void saveAllData() {
//        // Given
//        // When
//        appStateService.saveAllData();
//
//        // Then
//        verify(persistenceService, times(2)).saveToFile(any(), anyString());
//    }
//
//
//    @Test
//    void restoreAllData_whenFetchSuccess_thenUpdateRepositories() {
//        // Given
//        when(persistenceService.readFromFile(anyString())).thenReturn(new HashMap<>());
//
//        // When
//        appStateService.restoreAllData();
//
//        // Then
//        verify(bookingRepository, times(1)).setBookings(any());
//        verify(workSpaceRepository, times(1)).setWorkspaces(any());
//    }
//
//    @Test
//    void restoreAllData_whenCantReadFiles_thenDontUpdateRepositories() {
//        // Given
//        when(persistenceService.readFromFile(anyString())).thenReturn(null);
//
//        // When
//        appStateService.restoreAllData();
//
//        // Then
//        verify(bookingRepository, times(0)).setBookings(any());
//        verify(workSpaceRepository, times(0)).setWorkspaces(any());
//    }

}
