package org.example.repository.impl;

import org.example.entity.WorkSpace;
import org.example.enums.WorkSpaceType;
import org.example.repository.WorkSpaceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryWorkSpaceRepository implements WorkSpaceRepository {

    private static InMemoryWorkSpaceRepository INSTANCE;

    private final List<WorkSpace> workspaces = new ArrayList<>();

    private InMemoryWorkSpaceRepository() {
        workspaces.add(new WorkSpace(1L, WorkSpaceType.FLEXIBLE_DESK, 90.00, true));
        workspaces.add(new WorkSpace(2L, WorkSpaceType.FLEXIBLE_DESK, 90.00, true));
        workspaces.add(new WorkSpace(3L, WorkSpaceType.FLEXIBLE_DESK, 90.00, true));
        workspaces.add(new WorkSpace(4L, WorkSpaceType.FLEXIBLE_DESK, 90.00, true));
        workspaces.add(new WorkSpace(5L, WorkSpaceType.PRIVATE_ROOM, 150.00, true));
        workspaces.add(new WorkSpace(6L, WorkSpaceType.PRIVATE_ROOM, 150.00, true));
        workspaces.add(new WorkSpace(7L, WorkSpaceType.CONFERENCE_ROOM, 230.00, true));
    }

    @Override
    public void save(WorkSpace workSpace) {
        this.workspaces.add(workSpace);
    }

    @Override
    public WorkSpace update(WorkSpace workSpace) {
        WorkSpace savedWorkSpace = this.workspaces.stream()
                .filter(w -> w.getId().equals(workSpace.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No work space found"));
        savedWorkSpace.setId(workSpace.getId());
        savedWorkSpace.setType(workSpace.getType());
        savedWorkSpace.setPrice(workSpace.getPrice());
        savedWorkSpace.setAvailable(workSpace.getAvailable());
        return savedWorkSpace;
    }

    @Override
    public Optional<WorkSpace> findById(Long id) {
        return this.workspaces.stream()
                .filter(w -> w.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<WorkSpace> findAll() {
        return this.workspaces;
    }

    @Override
    public void deleteById(Long id) {
        this.workspaces.removeIf(w -> w.getId().equals(id));
    }


    public static InMemoryWorkSpaceRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryWorkSpaceRepository();
        }
        return INSTANCE;
    }

}
