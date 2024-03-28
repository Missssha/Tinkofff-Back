package edu.java.scrapper;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ExampleIntegrationTest extends IntegrationTest {

    @Test
    public void testLiquibaseMigration() throws SQLException, FileNotFoundException, LiquibaseException {
        IntegrationTest.runMigrations(POSTGRES);

        Database database = getDatabase(getConnection(
            POSTGRES.getJdbcUrl(),
            POSTGRES.getUsername(),
            POSTGRES.getPassword()));

        assertThat(database).isNotNull();
    }
}
