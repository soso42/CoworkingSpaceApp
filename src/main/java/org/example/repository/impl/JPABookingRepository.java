package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.example.entity.Booking;
import org.example.repository.BookingRepository;
import org.example.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class JPABookingRepository implements BookingRepository {

    private static JPABookingRepository instance;

    private JPABookingRepository() {}


    @Override
    public Booking save(Booking booking) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(booking);
            tx.commit();
            return booking;
        } catch (PersistenceException e) {
            tx.rollback();
            throw new PersistenceException(e);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Booking> findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Booking booking = em.find(Booking.class, id);
            tx.commit();
            return Optional.ofNullable(booking);
        } catch (PersistenceException e) {
            tx.rollback();
            throw new PersistenceException(e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Booking> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            List<Booking> bookings = em.createQuery("SELECT b FROM Booking b JOIN FETCH b.workSpace", Booking.class).getResultList();
            tx.commit();
            return bookings;
        } catch (PersistenceException e) {
            tx.rollback();
            throw new PersistenceException(e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Booking booking) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.createQuery("DELETE FROM Booking b WHERE b.id = :id")
                    .setParameter("id", booking.getId())
                    .executeUpdate();
            tx.commit();
        } catch (PersistenceException e) {
            tx.rollback();
            throw new PersistenceException(e);
        } finally {
            em.close();
        }
    }


    public static JPABookingRepository getInstance() {
        if  (instance == null) {
            instance = new JPABookingRepository();
        }
        return instance;
    }

}
