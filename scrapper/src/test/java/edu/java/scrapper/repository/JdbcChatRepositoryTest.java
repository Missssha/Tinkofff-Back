package edu.java.scrapper.repository;

import edu.java.dto.Chat;
import edu.java.repository.jdbc.JdbcChatRepository;
import edu.java.scrapper.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class JdbcChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcChatRepository chatRepository;
    @Autowired
    private JdbcClient jdbcClient;


    @Test
    @Transactional
    @Rollback
    public void testAdding() {

        Chat chat = new Chat(123456789L);
        assertThat(POSTGRES.isRunning()).isTrue();

        chatRepository.add(chat);

        // Assert
        List<Chat> chats =
            jdbcClient.sql("SELECT * FROM chat WHERE chat_id = ?")
                .param(1, chat.getChatId())
                .query(Chat.class)
                .list();
        assertThat(chats.getFirst().getChatId()).isEqualTo(chat.getChatId());
    }

}
