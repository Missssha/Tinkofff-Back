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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@Transactional
@Rollback
public class JdbcChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcChatRepository chatRepository;

    @Test
    public void testAdding() {
        Chat chat = new Chat();
        chat.setChatId(1L);
        chatRepository.add(chat);
        List<Chat> chats = chatRepository.findAll();

        assertThat(chats).hasSize(1);
        assertThat(chats.get(0).getChatId()).isEqualTo(chat.getChatId());

    }

    @Test
    public void testRemoving() {
        Chat chat = new Chat();
        chat.setChatId(1L);
        chatRepository.remove(chat.getChatId());

        List<Chat> chats = chatRepository.findAll();
        assertThat(chats).isEmpty();
    }

    @Test
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


        assertThat(chats).isNotNull();
    }
}
