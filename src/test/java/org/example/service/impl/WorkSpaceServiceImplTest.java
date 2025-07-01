package org.example.service.impl;

import org.example.entity.WorkSpace;
import org.example.enums.WorkSpaceType;
import org.example.exceptions.WorkSpaceNotFoundException;
import org.example.repository.WorkSpaceRepository;
import org.example.repository.impl.JPAWorkSpaceRepository;
import org.example.service.WorkSpaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorkSpaceServiceImplTest {

    private WorkSpaceRepository repository;
    private WorkSpaceService workSpaceService;


    @BeforeEach
    void setUp() {
        this.repository = mock(JPAWorkSpaceRepository.class);
        this.workSpaceService = new WorkSpaceServiceImpl(repository);
    }


    @Test
    void save() {
        // Given
        WorkSpaceType type = WorkSpaceType.FLEXIBLE_DESK;
        Integer price = 999;
        Boolean availability = true;
        WorkSpace workSpace = new WorkSpace(null, type, price, availability);
        WorkSpace savedWorkSpace = new WorkSpace(1L, type, price, availability);
        when(repository.save(workSpace)).thenReturn(savedWorkSpace);

        // When
        WorkSpace result = workSpaceService.save(workSpace);

        // Then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(savedWorkSpace.getId(), result.getId()),
                () -> assertEquals(savedWorkSpace.getType(), result.getType()),
                () -> assertEquals(savedWorkSpace.getPrice(), result.getPrice()),
                () -> assertEquals(savedWorkSpace.getAvailable(), result.getAvailable())
        );
    }

    @Test
    void removeWorkSpace_happyPath() {
        // Given
        when(repository.findById(anyLong())).thenReturn(Optional.of(new WorkSpace()));

        // When
        // Then
        assertDoesNotThrow(() -> workSpaceService.removeWorkSpace(anyLong()));
    }

    @ParameterizedTest
    @ValueSource(longs = {Long.MIN_VALUE, Long.MAX_VALUE})
    void removeWorkSpace_whenIdNotExists(Long id) {
        // Given
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When
        // Then
        assertThrows(WorkSpaceNotFoundException.class, () -> {
            workSpaceService.removeWorkSpace(id);
        });
    }

    @Test
    void findAll() {
        // Given
        when(repository.findAll()).thenReturn(List.of());

        // When
        // Then
        assertEquals(0, workSpaceService.findAll().size());
    }

    @Test
    void findById_happyPath() {
        // Given
        Long workingId = 1L;
        when(repository.findById(workingId)).thenReturn(Optional.of(new WorkSpace()));

        // When
        Optional<WorkSpace> result = workSpaceService.findById(workingId);

        // Then
        assertTrue(result.isPresent());
    }

    @Test
    void findById_whenIdNotExists() {
        // Given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        Optional<WorkSpace> result = workSpaceService.findById(1L);

        // Then
        assertTrue(result.isEmpty());
    }

}
