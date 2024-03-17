package edu.java.service;

import edu.java.dto.Chat;
import java.util.List;

public interface ChatService {
    List<Chat> getChats();

    void addChat(Chat chat);

    void removeChat(Long id);
}
