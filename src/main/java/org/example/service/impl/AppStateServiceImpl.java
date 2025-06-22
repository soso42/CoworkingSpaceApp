package org.example.service.impl;

import org.example.config.AppConfig;
import org.example.service.AppStateService;
import org.flywaydb.core.Flyway;

public class AppStateServiceImpl implements AppStateService {

    private static String DATABASE;
    private static String USERNAME;
    private static String PASSWORD;

    public AppStateServiceImpl() {
        DATABASE = AppConfig.get("db.url");
        USERNAME = AppConfig.get("db.user");
        PASSWORD = AppConfig.get("db.password");
    }

    @Override
    public void initDatabase() {
        Flyway flyway = Flyway.configure()
                .dataSource(DATABASE, USERNAME, PASSWORD)
                .load();
        flyway.migrate();
    }
}
