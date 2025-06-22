package org.example.repository.impl;

import org.example.config.AppConfig;
import org.example.entity.Booking;
import org.example.exceptions.BookingNotFoundException;
import org.example.repository.BookingRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCBookingRepository implements BookingRepository {

    private static JDBCBookingRepository instance;
    private static String DATABASE;
    private static String USERNAME;
    private static String PASSWORD;

    private static final String INSERT_QUERY = "INSERT INTO booking (workspace_id, start_date, end_date) VALUES (?, ?, ?);";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM booking WHERE id = ?;";
    private static final String FIND_ALL_QUERY = "SELECT * FROM booking;";
    private static final String DELETE_QUERY = "DELETE FROM booking WHERE id = ?;";

    private Connection connection;

    private JDBCBookingRepository() {
        DATABASE = AppConfig.get("db.url");
        USERNAME = AppConfig.get("db.user");
        PASSWORD = AppConfig.get("db.password");

        try {
            this.connection = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Connection to database can not be established: " + e.getMessage());
        }

        Runtime.getRuntime().addShutdownHook(new Thread(this::closeConnection));
    }


    @Override
    public Booking save(Booking booking) {

        try (PreparedStatement stmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

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

        try (PreparedStatement stmt = connection.prepareStatement(FIND_BY_ID_QUERY)) {

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

        try (Statement stmt = connection.createStatement()) {

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

        try (PreparedStatement stmt = connection.prepareStatement(DELETE_QUERY)) {

            stmt.setLong(1, booking.getId());
            int rows = stmt.executeUpdate();

            if (rows == 0) {
                throw new BookingNotFoundException("Booking with id " + booking.getId() + " was not found in database.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection was closed.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static JDBCBookingRepository getInstance() {
        if (instance == null) {
            instance = new JDBCBookingRepository();
        }
        return instance;
    }

}
