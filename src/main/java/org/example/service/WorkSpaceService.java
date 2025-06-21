package org.example.service;

import org.example.entity.WorkSpace;

import java.util.List;
import java.util.Optional;

public interface WorkSpaceService {
    WorkSpace save(WorkSpace workSpace);
    void removeWorkSpace(Long id);
    List<WorkSpace> findAll();
    Optional<WorkSpace> findById(Long id);
}
