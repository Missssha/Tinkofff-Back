package edu.java.scrapper.service.jdbc;

import edu.java.dto.Chat;
import edu.java.repository.jdbc.ChatRepository;
import edu.java.service.jdbc.JdbcChatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class JdbcChatServiceTest {

    @Mock
    private ChatRepository chatRepository;

    @InjectMocks
    private JdbcChatService chatService;

    @Test
    public void testGetChatsReturnsChatsFromRepository() {
        List<Chat> chats = Arrays.asList(new Chat(1L), new Chat(2L));
        Mockito.when(chatRepository.findAll()).thenReturn(chats);

        List<Chat> retrievedChats = chatService.getChats();

        assertThat(retrievedChats).isSameAs(chats);
    }

    @Test
    public void testAddChatCallsRepositoryAdd() {
        Chat newChat = new Chat(3L);
        Mockito.doNothing().when(chatRepository).add(newChat);

        chatService.addChat(newChat);

        Mockito.verify(chatRepository).add(newChat);
    }

    @Test
    public void testRemoveChatCallsRepositoryRemove() {
        Long chatId = 1L;
        Mockito.doNothing().when(chatRepository).remove(chatId);

        chatService.removeChat(chatId);

        Mockito.verify(chatRepository).remove(chatId);
    }
}

