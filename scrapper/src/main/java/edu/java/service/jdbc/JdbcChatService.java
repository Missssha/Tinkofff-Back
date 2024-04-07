package edu.java.service.jdbc;

import edu.java.dto.Chat;
import edu.java.repository.jdbc.JdbcChatRepository;
import edu.java.service.ChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcChatService implements ChatService {

    private final JdbcChatRepository chatRepository;

    @Override
    public List<Chat> getChats() {
        try {
            return chatRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error getting a chat");
        }
    }

    @Override
    public void addChat(Chat chat) {
        chatRepository.add(chat);
    }

    @Override
    public void removeChat(Long id) {
        try {
            chatRepository.remove(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("chat not found");
        }

    }
}
