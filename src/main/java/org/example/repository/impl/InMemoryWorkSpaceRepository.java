package org.example.repository.impl;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.WorkSpace;
import org.example.repository.WorkSpaceRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class InMemoryWorkSpaceRepository implements WorkSpaceRepository {

    private static InMemoryWorkSpaceRepository INSTANCE;

    private Map<Long, WorkSpace> workspaces = new HashMap<>();


    @Override
    public void save(WorkSpace workSpace) {
        Long lastId = this.workspaces.keySet()
                .stream()
                .max(Long::compareTo)
                .orElse(0L);
        workSpace.setId(lastId + 1);
        this.workspaces.put(workSpace.getId(), workSpace);
    }

    @Override
    public WorkSpace update(WorkSpace workSpace) {
        WorkSpace savedWorkSpace = this.workspaces.get(workSpace.getId());
        if (savedWorkSpace == null) {
            throw  new RuntimeException("No work space found");
        }
        savedWorkSpace.setId(workSpace.getId());
        savedWorkSpace.setType(workSpace.getType());
        savedWorkSpace.setPrice(workSpace.getPrice());
        savedWorkSpace.setAvailable(workSpace.getAvailable());
        return savedWorkSpace;
    }

    @Override
    public Optional<WorkSpace> findById(Long id) {
        return Optional.ofNullable(this.workspaces.get(id));
    }

    @Override
    public List<WorkSpace> findAll() {
        return this.workspaces.values().stream().toList();
    }

    @Override
    public void deleteById(Long id) {
        this.workspaces.remove(id);
    }


    public static InMemoryWorkSpaceRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryWorkSpaceRepository();
        }
        return INSTANCE;
    }

}
