package org.example.service.impl;

import org.example.entity.WorkSpace;
import org.example.enums.WorkSpaceType;
import org.example.exceptions.WorkSpaceNotFoundException;
import org.example.service.WorkSpaceService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkSpaceServiceImplTest {

    private static WorkSpaceService service = new WorkSpaceServiceImpl();


    @Test
    void save() {
        // Given
        Long lastIdInDb = service.findAll().getLast().getId();
        WorkSpaceType type = WorkSpaceType.FLEXIBLE_DESK;
        Double price = 100.0;
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
        workSpace.setPrice(100.0);
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
    }

    @Test
    void findById() {
    }

}
