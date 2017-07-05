package db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Example of a Java-based migration.
 */
public class V3__Update_table implements JdbcMigration {

    @Override
    public void migrate(Connection connection) throws Exception {

        try (PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO TEST (name) VALUES ('test3')")) {

            statement.execute();
        }
    }
}
