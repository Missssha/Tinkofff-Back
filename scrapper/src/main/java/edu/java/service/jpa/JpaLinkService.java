package edu.java.service.jpa;

import edu.java.dto.Link;
import edu.java.dto.LinkSof;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.service.LinkService;
import java.sql.Timestamp;
import java.util.List;

public class JpaLinkService implements LinkService {

    private final JpaLinkRepository jpaLinkRepository;

    public JpaLinkService(JpaLinkRepository jpaLinkRepository) {
        this.jpaLinkRepository = jpaLinkRepository;
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
            jpaLinkRepository.removeLinksInChatsLinks(id);
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

    @Override
    public void updateLinkLastCheckTimeById(Long id, Timestamp lastCheckTime) {

    }

    @Override
    public LinkSof getLinkPropertiesById(Long id) {
        return null;
    }

    @Override
    public void updateCountOfCommentsById(Long id, Long count) {

    }

    @Override
    public void updateCountOfAnswersById(Long id, Long count) {

    }

}
