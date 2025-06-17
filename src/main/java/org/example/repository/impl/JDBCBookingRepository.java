package org.example.repository.impl;

import org.example.entity.Booking;
import org.example.entity.WorkSpace;
import org.example.exceptions.BookingNotFoundException;
import org.example.repository.BookingRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCBookingRepository implements BookingRepository {

    private static JDBCBookingRepository instance;
    private static final String DATABASE = "jdbc:postgresql://localhost:54321/coworking_app";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1";

    private static final String INSERT_QUERY = "INSERT INTO booking (workspace_id, start_date, end_date) VALUES (?, ?, ?);";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM booking WHERE id = ?;";
    private static final String FIND_ALL_QUERY = "SELECT * FROM booking;";
    private static final String DELETE_QUERY = "DELETE FROM booking WHERE id = ?;";

    private JDBCBookingRepository() {}


    @Override
    public Booking save(Booking booking) {

        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, booking.getWorkSpaceId());
            stmt.setDate(2, Date.valueOf(booking.getStartDate()));
            stmt.setDate(3, Date.valueOf(booking.getEndDate()));
            stmt.executeUpdate();

            try (ResultSet resultSet = stmt.getGeneratedKeys()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    return new Booking(id, booking.getWorkSpaceId(), booking.getStartDate(), booking.getEndDate());
                } else {
                    throw new SQLException("Inserting workspace failed, no ID returned.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Booking> findById(Long id) {

        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID_QUERY)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getLong("id"));
                booking.setWorkSpaceId(rs.getLong("workspace_id"));
                booking.setStartDate(LocalDate.parse(rs.getString("start_date")));
                booking.setEndDate(LocalDate.parse(rs.getString("end_date")));
                return Optional.of(booking);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Booking> findAll() {

        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(FIND_ALL_QUERY);

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getLong("id"));
                booking.setWorkSpaceId(rs.getLong("workspace_id"));
                booking.setStartDate(LocalDate.parse(rs.getString("start_date")));
                booking.setEndDate(LocalDate.parse(rs.getString("end_date")));
                bookings.add(booking);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bookings;
    }

    @Override
    public void delete(Booking booking) {

        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY)) {

            stmt.setLong(1, booking.getId());
            int rows = stmt.executeUpdate();

            if (rows == 0) {
                throw new BookingNotFoundException("Booking with id " + booking.getId() + " was not found in database.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static JDBCBookingRepository getInstance() {
        if (instance == null) {
            instance = new JDBCBookingRepository();
        }
        return instance;
    }

}
