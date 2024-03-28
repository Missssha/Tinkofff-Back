package edu.java.scrapper.repository;

import edu.java.dto.Chat;
import edu.java.repository.jdbc.JdbcChatRepository;
import edu.java.scrapper.IntegrationTest;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class JdbcChatRepositoryTest extends IntegrationTest {
    @Autowired
    @Mock
    private JdbcChatRepository chatRepository;


    @Test
    @Transactional
    @Rollback
    public void testAdding() throws SQLException, FileNotFoundException, LiquibaseException {
        Database database = IntegrationTest.runMigrations(POSTGRES);
        assertThat(database).isNotNull();

        Chat chat = new Chat();
        chat.setChatId(1L);
        chatRepository.add(chat);
        List<Chat> chats = chatRepository.findAll();

        assertEquals(1L, chats.get(0).getChatId());
        chatRepository.remove(1L);
    }

}
