package org.example.service.impl;

import org.example.entity.WorkSpace;
import org.example.enums.WorkSpaceType;
import org.example.exceptions.WorkSpaceNotFoundException;
import org.example.repository.impl.InMemoryWorkSpaceRepository;
import org.example.service.WorkSpaceService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorkSpaceServiceImplTest {

    private static InMemoryWorkSpaceRepository mockRepository = mock(InMemoryWorkSpaceRepository.class);
    private static WorkSpaceService mockService = new WorkSpaceServiceImpl(mockRepository);

    private static WorkSpaceService service = new WorkSpaceServiceImpl(InMemoryWorkSpaceRepository.getInstance());


    @Test
    void save() {
        // Given
        Long lastIdInDb = service.findAll().getLast().getId();
        WorkSpaceType type = WorkSpaceType.FLEXIBLE_DESK;
        Integer price = 100;
        WorkSpace workSpace = new WorkSpace();
        workSpace.setId(lastIdInDb + 1);
        workSpace.setType(type);
        workSpace.setPrice(price);
        // When
        service.save(workSpace);
        WorkSpace savedWorkSpace = service.findAll().getLast();
        // Then
        assertEquals(savedWorkSpace.getId(), workSpace.getId());
        assertEquals(savedWorkSpace.getType(), workSpace.getType());
        assertEquals(savedWorkSpace.getPrice(), workSpace.getPrice());
    }

    @Test
    void removeWorkSpace_happyPath() {
        // Given
        Long lastIdInDb = service.findAll().getLast().getId();
        WorkSpace workSpace = new WorkSpace();
        workSpace.setId(++lastIdInDb);
        workSpace.setType(WorkSpaceType.FLEXIBLE_DESK);
        workSpace.setPrice(100);
        service.save(workSpace);
        // When
        service.removeWorkSpace(lastIdInDb);
        // Then
        assertTrue(service.findById(lastIdInDb).isEmpty());
    }

    @Test
    void removeWorkSpace_whenIdNotExists() {
        // Given
        Long nonExistentId = Long.MAX_VALUE;
        // When
        // Then
        assertThrows(WorkSpaceNotFoundException.class, () -> {
            service.removeWorkSpace(nonExistentId);
        });
    }

    @Test
    void findAll() {
        // Given
        List<WorkSpace> workSpaces = Arrays.asList(
                new WorkSpace(),
                new WorkSpace()
        );
        when(mockRepository.findAll()).thenReturn(workSpaces);
        // When
        List<WorkSpace> result = mockService.findAll();
        // Then
        assertEquals(workSpaces.size(), result.size());
    }

    @Test
    void findById_happyPath() {
        // Given
        long id = 999L;
        WorkSpace mockWorkSpace = mock(WorkSpace.class);
        when(mockService.findById(id)).thenReturn(Optional.of(mockWorkSpace));
        // When
        Optional<WorkSpace> result = mockService.findById(id);
        // Then
        assertTrue(result.isPresent());
    }

    @Test
    void findById_whenIdNotExists() {
        // Given
        long id = Long.MAX_VALUE;
        when(mockService.findById(id)).thenReturn(Optional.empty());
        // When
        Optional<WorkSpace> result = mockService.findById(id);
        // Then
        assertTrue(result.isEmpty());
    }

}
