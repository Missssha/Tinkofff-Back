package edu.java.scrapper.repository;

import edu.java.dto.Chat;
import edu.java.repository.jdbc.JdbcChatRepository;
import edu.java.scrapper.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class JdbcChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcChatRepository chatRepository;

    @Test
    @Transactional
    @Rollback
    public void testAdding() {
        Chat chat = new Chat();
        chat.setChatId(1L);
        chatRepository.add(chat);
        List<Chat> chats = chatRepository.findAll();

        assertEquals(1, chats.size());
        assertEquals(1, chats.get(0).getId());
        chatRepository.remove(chat.getChatId());
    }

    @Test
    @Transactional
    @Rollback
    public void testRemoving() {
        Chat chat = new Chat();
        chat.setChatId(1L);
        chatRepository.add(chat);
        chatRepository.remove(chat.getChatId());
        List<Chat> chats = chatRepository.findAll();

        assertEquals(0, chats.size());
    }

    @Test
    @Transactional
    @Rollback
    public void testFinding() {
        Chat chat1 = new Chat();
        chat1.setChatId(1L);
        Chat chat2 = new Chat();
        chat2.setChatId(2L);
        Chat chat3 = new Chat();
        chat3.setChatId(3L);

        chatRepository.add(chat1);
        chatRepository.add(chat2);
        chatRepository.add(chat3);
        List<Chat> chats = chatRepository.findAll();


        assertEquals(3, chats.size());
        chatRepository.remove(chat1.getChatId());
        chatRepository.remove(chat2.getChatId());
        chatRepository.remove(chat3.getChatId());
    }
}
