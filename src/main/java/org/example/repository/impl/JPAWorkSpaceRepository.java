package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.entity.WorkSpace;
import org.example.repository.WorkSpaceRepository;
import org.example.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class JPAWorkSpaceRepository implements WorkSpaceRepository {

    private static JPAWorkSpaceRepository instance;

    private JPAWorkSpaceRepository() {}


    @Override
    public WorkSpace save(WorkSpace workSpace) {
        return null;
    }

    @Override
    public WorkSpace update(WorkSpace workSpace) {
        return null;
    }

    @Override
    public Optional<WorkSpace> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<WorkSpace> findAll() {

        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            List<WorkSpace> list = em.createQuery("SELECT w FROM WorkSpace w", WorkSpace.class).getResultList();
            
            tx.commit();

            return list;

        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }

    }

    @Override
    public void deleteById(Long id) {

    }


    public static JPAWorkSpaceRepository getInstance() {
        if (instance == null) {
            instance = new JPAWorkSpaceRepository();
        }
        return instance;
    }

}
