package org.example.repository;

import org.example.entity.WorkSpace;

import java.util.List;
import java.util.Optional;

public interface WorkSpaceRepository {
    WorkSpace save(WorkSpace workSpace);
    WorkSpace update(WorkSpace workSpace);
    Optional<WorkSpace> findById(Long id);
    List<WorkSpace> findAll();
    void deleteById(Long id);
}
