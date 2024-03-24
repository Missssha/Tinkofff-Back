package edu.java.service.jpa;

import edu.java.dto.Link;
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
        jpaLinkRepository.removeLinkById(id);
    }

    @Override
    public void updateLinkLastCheckTime(Long id, Timestamp lastCheckTime) {

    }

    @Override
    public List<Link> getUnUpdatedLinks() {
        return jpaLinkRepository.findOldLinks();
    }

}
