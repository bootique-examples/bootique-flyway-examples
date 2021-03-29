[![verify](https://github.com/bootique-examples/bootique-flyway-demo/actions/workflows/verify.yml/badge.svg)](https://github.com/bootique-examples/bootique-flyway-demo/actions/workflows/verify.yml)
# bootique-flyway-demo

An example of versioned database migration built on [Flyway](https://flywaydb.org) integrated into [Bootique](http://bootique.io).

*For additional help/questions about this example send a message to
[Bootique forum](https://groups.google.com/forum/#!forum/bootique-user).*
   
## Prerequisites
      
    * Java 1.8 or newer.
    * Apache Maven.
      
## Build the Demo
      
Here is how to build it:
        
    git clone git@github.com:bootique-examples/bootique-flyway-demo.git
    cd bootique-flyway-demo
    mvn package
      
## Run the Demo

Check the options available in your app:

    java -jar target/bootique-flyway-demo-1.0-SNAPSHOT.jar
    
    OPTIONS
      -b, --baseline
           Baselines an existing database, excluding all migrations up to and including baselineVersion.

      --clean
           Drops all objects (tables, views, procedures, triggers, ...) in the configured schemas.The schemas are cleaned in the order specified by the schemas property.

      --config=yaml_location
           Specifies YAML config location, which can be a file path or a URL.

      -h, --help
           Prints this message.

      -H, --help-config
           Prints information about application modules and their configuration options.

      -i, --info
           Prints the details and status information about all the migrations.

      -m, --migrate
           Migrates the schema to the latest version. Flyway will create the metadata table automatically if it doesn't exist.

      -r, --repair
           Repairs the metadata table.

      -v, --validate
           Validate applied migrations against resolved ones (on the filesystem or classpath) to detect accidental changes that may prevent the schema(s) from being recreated exactly.

An ordinary use case is when Flyway is pointed at an empty database. [MySQL](https://www.mysql.com) is used in the example. 

Run the script to create an empty schema `mydb`:
```mysql-sql
-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;
```
Configure a database connection and migrations scripts locations in *config.yml*:

```yaml
jdbc:
  mysql:
    url: jdbc:mysql://localhost:3306/mydb?nullNamePatternMatchesAll=true&connectTimeout=0&autoReconnect=true
    driverClassName: com.mysql.jdbc.Driver
    initialSize: 1
    username: root
    password:

flyway:
  locations:
    - db/migration
  dataSources:
    - mysql
``` 
Migrations can be written in SQL or Java.

**Sample SQL script**

*V1__Create_new_table.sql*
```mysql-sql
-- -----------------------------------------------------
-- Table `mydb`.`TEST`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TEST` (
  `id` BIGINT(19) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`));

-- INSERT
INSERT INTO mydb.TEST (name) VALUES ('test1');
```

**Sample Class**

*V3__Update_table.java*
```java
public class V3__Update_table implements JdbcMigration {
    private static Logger LOGGER = LoggerFactory.getLogger(V3__Update_table.class);

    @Override
    public void migrate(Connection connection) throws Exception {
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
```

Run migration:
```bash
java -jar target/bootique-flyway-demo-1.0-SNAPSHOT.jar --config=config.yml --migrate
```    
Result:
```
...
INFO  [2017-06-28 16:03:23,888] main o.f.c.i.c.DbMigrate: Current version of schema `mydb`: << Empty Schema >>
INFO  [2017-06-28 16:03:23,888] main o.f.c.i.c.DbMigrate: Migrating schema `mydb` to version 1 - Create new table
INFO  [2017-06-28 16:03:23,950] main o.f.c.i.c.DbMigrate: Migrating schema `mydb` to version 2 - Insert data
INFO  [2017-06-28 16:03:23,958] main o.f.c.i.c.DbMigrate: Migrating schema `mydb` to version 3 - Update table
INFO  [2017-06-28 16:03:23,966] main o.f.c.i.c.DbMigrate: Successfully applied 3 migrations to schema `mydb` (execution time 00:00.195s).
```

All migrations are checked against the metadata table SCHEMA_VERSION located or created from scratch by Flyway.

Check table **schema_version**:

```text
+----------------+-------------+-------------------+------+------------------------------+-----------+---------------+---------------------+----------------+---------+
| installed_rank | version     | description       | type | script                       | checksum  | installed_by  | installed_on        | execution_time | success |
+----------------+-------------+-------------------+------+------------------------------+-----------+---------------+---------------------+----------------+---------+
| 1              | 1           | Create new table  | SQL  | V1__Create_new_table.sql     | 804051539 | root          | 2017-06-28 19:03:23 | 54             | 1       |                        
| 2              | 2           | Insert data       | SQL  | V2__Insert_data.sql          | 292896403 | root          | 2017-06-28 19:03:23 | 2              | 1       |                                  
| 3              | 3           | Update table      | JDBC | db.migration.V3__Update_table| NULL      | root          | 2017-06-28 19:03:23 | 1              | 1       |                               
+----------------+-------------+-------------------+------+------------------------------+-----------+---------------+---------------------+----------------+---------+
```















