package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.example.entity.WorkSpace;
import org.example.exceptions.WorkSpaceNotFoundException;
import org.example.repository.WorkSpaceRepository;
import org.example.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class JPAWorkSpaceRepository implements WorkSpaceRepository {

    private static JPAWorkSpaceRepository instance;

    private JPAWorkSpaceRepository() {}


    @Override
    public WorkSpace save(WorkSpace workSpace) {

        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(workSpace);
            tx.commit();
            return workSpace;
        } catch (PersistenceException e) {
            tx.rollback();
            throw new PersistenceException(e);
        } finally {
            em.close();
        }
    }

    @Override
    public WorkSpace update(WorkSpace workSpace) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            WorkSpace dbWorkSpace = em.find(WorkSpace.class, workSpace.getId());
            if (dbWorkSpace == null) {
                throw new WorkSpaceNotFoundException("WorkSpace with id " + workSpace.getId() + " not found");
            }

            WorkSpace updated = em.merge(workSpace);
            tx.commit();
            return  updated;
        } catch (PersistenceException e) {
            tx.rollback();
            throw new PersistenceException(e);
        }  finally {
            em.close();
        }
    }

    @Override
    public Optional<WorkSpace> findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            WorkSpace workSpace = em.find(WorkSpace.class, id);
            tx.commit();
            return Optional.ofNullable(workSpace);
        } catch (PersistenceException e) {
            tx.rollback();
            throw new PersistenceException(e.getMessage());
        } finally {
            em.close();
        }
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
        } catch (PersistenceException e) {
            tx.rollback();
            throw new PersistenceException(e);
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.createQuery("DELETE FROM WorkSpace w WHERE w.id = :id")
                            .setParameter("id", id)
                            .executeUpdate();
            tx.commit();
        } catch (PersistenceException e) {
            tx.rollback();
            throw new PersistenceException(e);
        } finally {
            em.close();
        }
    }


    public static JPAWorkSpaceRepository getInstance() {
        if (instance == null) {
            instance = new JPAWorkSpaceRepository();
        }
        return instance;
    }

}
