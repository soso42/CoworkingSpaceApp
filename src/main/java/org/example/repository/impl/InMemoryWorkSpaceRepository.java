package org.example.repository.impl;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.WorkSpace;
import org.example.repository.WorkSpaceRepository;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class InMemoryWorkSpaceRepository implements WorkSpaceRepository {

    private static InMemoryWorkSpaceRepository INSTANCE;

    private List<WorkSpace> workspaces;


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
