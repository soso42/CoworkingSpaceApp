package org.example.service.impl;

import org.example.entity.WorkSpace;
import org.example.exceptions.WorkSpaceNotFoundException;
import org.example.repository.WorkSpaceRepository;
import org.example.repository.impl.InMemoryWorkSpaceRepository;
import org.example.service.WorkSpaceService;

import java.util.List;
import java.util.Optional;

public class WorkSpaceServiceImpl implements WorkSpaceService {

    private final WorkSpaceRepository repository = InMemoryWorkSpaceRepository.getInstance();


    @Override
    public void save(WorkSpace workSpace) {
        Long id = repository.findAll().getLast().getId();
        workSpace.setId(++id);
        repository.save(workSpace);
    }

    @Override
    public void removeWorkSpace(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new WorkSpaceNotFoundException("Work Space Not Found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public List<WorkSpace> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<WorkSpace> findById(Long id) {
        return repository.findById(id);
    }

}
