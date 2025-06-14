package org.example.repository.impl;

import org.example.entity.WorkSpace;
import org.example.enums.WorkSpaceType;
import org.example.exceptions.WorkSpaceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryWorkSpaceRepositoryTest {

    private InMemoryWorkSpaceRepository repository;

    @BeforeEach
    void setUp() {
        repository = InMemoryWorkSpaceRepository.getInstance();
    }

    @Test
    void save_workspace_happyPath() {
        // Given
        WorkSpace workSpace = new WorkSpace(null, WorkSpaceType.FLEXIBLE_DESK, 999, true);

        // When
        WorkSpace savedWorkSpace = repository.save(workSpace);

        // Then
        assertNotNull(savedWorkSpace);
        assertAll(
                () -> assertEquals(workSpace.getType(), savedWorkSpace.getType()),
                () -> assertEquals(workSpace.getPrice(), savedWorkSpace.getPrice()),
                () -> assertEquals(workSpace.getAvailable(), savedWorkSpace.getAvailable())
        );
    }

    @Test
    void update_workspace_happyPath() {
        // Given
        WorkSpace workSpace = WorkSpace.builder()
                .type(WorkSpaceType.CONFERENCE_ROOM)
                .price(111)
                .available(false)
                .build();
        repository.save(workSpace);
        WorkSpaceType type = WorkSpaceType.FLEXIBLE_DESK;
        Integer price = 999;
        Boolean available = true;

        // When
        workSpace.setType(type);
        workSpace.setPrice(price);
        workSpace.setAvailable(available);
        WorkSpace updatedWorkSpace = repository.update(workSpace);

        // Then
        assertAll(
                () -> assertEquals(type, updatedWorkSpace.getType()),
                () -> assertEquals(price, updatedWorkSpace.getPrice()),
                () -> assertEquals(available, updatedWorkSpace.getAvailable())
        );
    }

    @ParameterizedTest
    @ValueSource(longs = {Long.MAX_VALUE, Long.MAX_VALUE})
    void update_workspace_whenWorkSpaceNotExists_throwsException(Long id) {
        // Given
        WorkSpace workSpace = WorkSpace.builder().id(id).build();

        // When
        // Then
        assertThrows(WorkSpaceNotFoundException.class, () -> {
            repository.update(workSpace);
        });
    }

    @Test
    void findById_happyPath() {
        // Given
        WorkSpace workSpace = WorkSpace.builder()
                .type(WorkSpaceType.CONFERENCE_ROOM).build();
        Long id = repository.save(workSpace).getId();

        // When
        Optional<WorkSpace> optWorkSpace = repository.findById(id);

        // Then
        assertTrue(optWorkSpace.isPresent());
    }

    @ParameterizedTest
    @ValueSource(longs = {Long.MAX_VALUE, Long.MAX_VALUE})
    void findById_whenIdNotExists_returnsEmptyOptional(Long id) {
        // Given
        // When
        Optional<WorkSpace> optWorkSpace = repository.findById(id);

        // Then
        assertTrue(optWorkSpace.isEmpty());
    }

    @Test
    void findAll_happyPath() {
        // Given
        int initialSize = repository.findAll().size();
        repository.save(new WorkSpace());
        repository.save(new WorkSpace());

        // When
        int finalSize = repository.findAll().size();

        // Then
        assertEquals(initialSize + 2, finalSize);
    }

    @Test
    void deleteById_happyPath() {
        // Given
        Long id = repository.save(new WorkSpace()).getId();

        // When
        repository.deleteById(id);

        // Then
        assertTrue(repository.findById(id).isEmpty());
    }


    @Test
    void getInstance_happyPath() {
        // Given
        // When
        InMemoryWorkSpaceRepository repository1 = InMemoryWorkSpaceRepository.getInstance();
        InMemoryWorkSpaceRepository repository2 = InMemoryWorkSpaceRepository.getInstance();

        // Then
        assertEquals(repository1, repository2);
    }

}
