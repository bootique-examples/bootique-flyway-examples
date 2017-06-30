package db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Example of a Java-based migration.
 */
public class V3__Update_table implements JdbcMigration {
    private static Logger LOGGER = LoggerFactory.getLogger(V3__Update_table.class);

    @Override
    public void migrate(Connection connection) throws Exception {
        PreparedStatement statement =
                connection.prepareStatement("INSERT INTO TEST (name) VALUES ('test3')");

        try {
            statement.execute();
        } finally {
            statement.close();
        }
    }
}
