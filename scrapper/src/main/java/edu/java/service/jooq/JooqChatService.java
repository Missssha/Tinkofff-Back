package edu.java.service.jooq;

import edu.java.domain.jooq.tables.records.ChatRecord;
import edu.java.dto.Chat;
import edu.java.repository.jooq.JooqChatRepository;
import edu.java.service.ChatService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JooqChatService implements ChatService {
    private final JooqChatRepository chatRepository;

    @Override
    public List<Chat> getChats() {
        try {
            List<ChatRecord> records = chatRepository.findAll();
            List<Chat> chats = new ArrayList<>();
            for (ChatRecord chatRecord : records) {
                Chat chat = new Chat();
                chat.setChatId(chatRecord.getChatId());
                chat.setId(chatRecord.getId());
                chats.add(chat);
            }
            return chats;
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
