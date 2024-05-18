package org.clearsolutions.restapi.extension;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.*;

import static java.lang.String.format;

public class PostgresContainerExtension implements BeforeAllCallback {
    public static final String DB_NAME = "clear_solutions";
    public static final String DB_USERNAME = "postgres";
    public static final String DB_PASSWORD = "postgres";
    public static final int CONTAINER_PORT = 5432;
    public static final String IMAGE_NAME = "postgres:14-alpine";
    public static final String JDBC_URL_FORMAT = "jdbc:postgresql://localhost:%d/%s";
    public static final String DB_URL_PROPERTY = "spring.datasource.url";
    public static final String DB_USERNAME_PROPERTY = "spring.datasource.username";
    public static final String DB_PASSWORD_PROPERTY = "spring.datasource.password";
    public static final String FLYWAY_URL_PROPERTY = "spring.flyway.url";
    public static final String FLYWAY_USERNAME_PROPERTY = "spring.flyway.user";
    public static final String FLYWAY_PASSWORD_PROPERTY = "spring.flyway.password";

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        PostgreSQLContainer container = new PostgreSQLContainer<>(IMAGE_NAME)
                .withDatabaseName(DB_NAME)
                .withUsername(DB_USERNAME)
                .withPassword(DB_PASSWORD)
                .withExposedPorts(CONTAINER_PORT);

        container.start();
        String jdbcUrl = format(JDBC_URL_FORMAT, container.getFirstMappedPort(), container.getDatabaseName());

        System.setProperty(DB_URL_PROPERTY, jdbcUrl);
        System.setProperty(DB_USERNAME_PROPERTY, container.getUsername());
        System.setProperty(DB_PASSWORD_PROPERTY, container.getPassword());
        System.setProperty(FLYWAY_URL_PROPERTY, jdbcUrl);
        System.setProperty(FLYWAY_USERNAME_PROPERTY, container.getUsername());
        System.setProperty(FLYWAY_PASSWORD_PROPERTY, container.getPassword());
    }
}
