package edu.java.service.jpa;

import edu.java.dto.Chat;
import edu.java.dto.Link;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.service.ChatService;
import java.util.List;

public class JpaChatService implements ChatService {
    private final JpaChatRepository jpaChatRepository;
    private final JpaLinkRepository jpaLinkRepository;

    public JpaChatService(JpaChatRepository jpaChatRepository, JpaLinkRepository jpaLinkRepository) {
        this.jpaChatRepository = jpaChatRepository;
        this.jpaLinkRepository = jpaLinkRepository;
    }

    @Override
    public List<Chat> getChats() {
        return jpaChatRepository.findAll();
    }

    @Override
    public void addChat(Chat chat) {
        jpaChatRepository.save(chat);
    }

    @Override
    public void removeChat(Long id) {
        try {
            Chat chat = jpaChatRepository.findChatById(id);

            List<Link> links = chat.getLinks();
            for(Link link : links){
                List<Chat> chats = link.getChats();
                chats.remove(chat);
                link.setChats(chats);
                jpaLinkRepository.save(link);
            }

            jpaChatRepository.removeById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("chat not found");
        }
    }
}
