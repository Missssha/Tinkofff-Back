package edu.java.service.jpa;

import edu.java.dto.Chat;
import edu.java.dto.Link;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.service.LinkService;
import java.sql.Timestamp;
import java.util.List;

public class JpaLinkService implements LinkService {

    private final JpaLinkRepository jpaLinkRepository;
    private final JpaChatRepository jpaChatRepository;

    public JpaLinkService(JpaLinkRepository jpaLinkRepository, JpaChatRepository jpaChatRepository) {
        this.jpaLinkRepository = jpaLinkRepository;
        this.jpaChatRepository = jpaChatRepository;
    }

    @Override
    public List<Link> getLinks() {
        return jpaLinkRepository.findAll();
    }

    @Override
    public void addLink(Link link) {
        jpaLinkRepository.save(link);
    }

    @Override
    public void removeLink(Long id) {
        try {
            Link link = jpaLinkRepository.findLinkById(id);

            List<Chat> chats = link.getChats();
            for(Chat chat : chats){
                List<Link> links = chat.getLinks();
                links.remove(link);
                chat.setLinks(links);
                jpaChatRepository.save(chat);
            }

            jpaLinkRepository.removeLinkById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("link not found");
        }
    }

    @Override
    public void updateLinkLastCheckTime(Long id, Timestamp lastCheckTime) {
        jpaLinkRepository.updateLinkLastCheckTimeById(lastCheckTime, id);
    }

    @Override
    public List<Link> getUnUpdatedLinks() {
        return jpaLinkRepository.findOldLinks();
    }

}
