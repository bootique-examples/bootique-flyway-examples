package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Example of a Java-based migration.
 */
public class V3__Update_table extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        try (Connection connection = context.getConnection()){
            try(PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO TEST (name) VALUES ('test3')")) {
                statement.execute();
            }
        }
    }
}
