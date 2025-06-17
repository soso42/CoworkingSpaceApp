package org.example.repository.impl;

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
    private static final String DATABASE = "jdbc:postgresql://localhost:54321/coworking_app";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1";


    private JDBCWorkSpaceRepository() {}


    @Override
    public WorkSpace save(WorkSpace workSpace) {

        String query = "INSERT INTO workspace (type, price, available) VALUES ( ?, ?, ? ); ";

        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD)) {

            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

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

        String query = "UPDATE workspace SET type = ? , price = ? , available = ? WHERE id = ? ;";

        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD)) {

            PreparedStatement stmt = conn.prepareStatement(query);

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

        String query = "SELECT * FROM workspace WHERE id = ?";
        WorkSpace workSpace = null;

        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(query);
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

        String query = "SELECT * FROM workspace";
        List<WorkSpace> workSpaces = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

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

        String query = "DELETE FROM workspace WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD)) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Could not connect to database: " + e.getMessage());
        }
    }


    public static JDBCWorkSpaceRepository getInstance() {
        if (instance == null) {
            instance = new JDBCWorkSpaceRepository();
        }
        return instance;
    }

}
