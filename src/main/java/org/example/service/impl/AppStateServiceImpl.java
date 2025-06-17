package org.example.service.impl;

import lombok.AllArgsConstructor;
import org.example.service.AppStateService;
import org.flywaydb.core.Flyway;

@AllArgsConstructor
public class AppStateServiceImpl implements AppStateService {

    private static final String DATABASE = "jdbc:postgresql://localhost:54321/coworking_app";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1";

    @Override
    public void createDatabaseSchema() {
        Flyway flyway = Flyway.configure()
                .dataSource(DATABASE, USERNAME, PASSWORD)
                .load();
        flyway.migrate();
    }
}
