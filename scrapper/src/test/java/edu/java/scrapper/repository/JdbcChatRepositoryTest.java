package edu.java.scrapper.repository;

import edu.java.dto.Chat;
import edu.java.repository.jdbc.JdbcChatRepository;
import edu.java.scrapper.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class JdbcChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcChatRepository chatRepository;

    @Test
    public void testAdding() {
        Chat chat = new Chat();
        chat.setChatId(1L);
        chatRepository.add(chat);
        List<Chat> chats = chatRepository.findAll();

        assertEquals(1, chats.size());
        assertEquals(1, chats.get(0).getId());
        chatRepository.remove(1L);
    }

}
