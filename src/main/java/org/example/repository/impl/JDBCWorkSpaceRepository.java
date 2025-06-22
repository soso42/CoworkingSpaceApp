package org.example.repository.impl;

import org.example.config.AppConfig;
import org.example.entity.WorkSpace;
import org.example.enums.WorkSpaceType;
import org.example.exceptions.WorkSpaceNotFoundException;
import org.example.repository.WorkSpaceRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCWorkSpaceRepository implements WorkSpaceRepository {

    private static JDBCWorkSpaceRepository instance;
    private static String DATABASE;
    private static String USERNAME;
    private static String PASSWORD;

    private static final String INSERT_QUERY = "INSERT INTO workspace (type, price, available) VALUES ( ?, ?, ? ); ";
    private static final String UPDATE_QUERY = "UPDATE workspace SET type = ? , price = ? , available = ? WHERE id = ? ;";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM workspace WHERE id = ? ";
    private static final String FIND_ALL_QUERY = "SELECT * FROM workspace;";
    private static final String DELETE_QUERY = "DELETE FROM workspace WHERE id = ?";

    private Connection connection;

    private JDBCWorkSpaceRepository() {
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
    public WorkSpace save(WorkSpace workSpace) {

        try (PreparedStatement stmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, workSpace.getType().toString());
            stmt.setInt(2, workSpace.getPrice());
            stmt.setBoolean(3, workSpace.getAvailable());
            stmt.executeUpdate();

            try (ResultSet resultSet = stmt.getGeneratedKeys()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    return new WorkSpace(id, workSpace.getType(), workSpace.getPrice(), workSpace.getAvailable());
                } else {
                    throw new SQLException("Inserting workspace failed, no ID returned.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public WorkSpace update(WorkSpace workSpace) {

        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_QUERY)) {

            stmt.setString(1, workSpace.getType().toString());
            stmt.setInt(2, workSpace.getPrice());
            stmt.setBoolean(3, workSpace.getAvailable());
            stmt.setLong(4, workSpace.getId());

            int result = stmt.executeUpdate();

            if (result == 0) {
                throw new WorkSpaceNotFoundException("Updating workspace failed.");
            }

            return new WorkSpace(workSpace.getId(), workSpace.getType(), workSpace.getPrice(), workSpace.getAvailable());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<WorkSpace> findById(Long id) {

        WorkSpace workSpace = null;

        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_QUERY)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                workSpace = new WorkSpace();
                workSpace.setId(rs.getLong("id"));
                workSpace.setType(WorkSpaceType.valueOf(rs.getString("type")));
                workSpace.setPrice(rs.getInt("price"));
                workSpace.setAvailable(rs.getBoolean("available"));
            }

        } catch (SQLException e) {
            System.out.println("Could not connect to database: " + e.getMessage());
        }

        return Optional.ofNullable(workSpace);
    }

    @Override
    public List<WorkSpace> findAll() {

        List<WorkSpace> workSpaces = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(FIND_ALL_QUERY);

            while (rs.next()) {
                WorkSpace workSpace = new WorkSpace();
                workSpace.setId(rs.getLong("id"));
                workSpace.setType(WorkSpaceType.valueOf(rs.getString("type")));
                workSpace.setPrice(rs.getInt("price"));
                workSpace.setAvailable(rs.getBoolean("available"));
                workSpaces.add(workSpace);
            }

        } catch (SQLException e) {
            System.out.println("Could not connect to database: " + e.getMessage());
        }

        return workSpaces;
    }

    @Override
    public void deleteById(Long id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not connect to database: " + e.getMessage());
        }
    }



    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection was closed.");
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }


    public static JDBCWorkSpaceRepository getInstance() {
        if (instance == null) {
            instance = new JDBCWorkSpaceRepository();
        }
        return instance;
    }

}
