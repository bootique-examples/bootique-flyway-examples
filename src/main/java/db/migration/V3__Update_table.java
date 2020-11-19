package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Example of a Java-based migration.
 */
public class V3__Update_table extends BaseJavaMigration {
    private static Logger LOGGER = LoggerFactory.getLogger(V3__Update_table.class);

    @Override
    public void migrate(Context context) throws Exception {
        Connection connection = context.getConnection();
        PreparedStatement statement =
                connection.prepareStatement("INSERT INTO TEST (name) VALUES ('test3')");

        try {
            statement.execute();
        } catch (SQLException e) {
            LOGGER.error("Migration failed", e);
        } finally {
            statement.close();
        }
    }
}
