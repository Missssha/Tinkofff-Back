package edu.java.service.jdbc;

import edu.java.dto.Link;
import edu.java.dto.LinkSof;
import edu.java.repository.LinkRepository;
import edu.java.service.LinkService;
import java.sql.Timestamp;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final LinkRepository linkRepository;

    @Override
    public List<Link> getLinks() {
        return linkRepository.findAll();
    }

    public List<Link> getOldLinks() {
        return linkRepository.findUnUpdatedLinks();
    }

    @Override
    public int addLink(Link link) {
        return linkRepository.add(link);
    }

    @Override
    public int removeLink(Long id) {
        return linkRepository.remove(id);
    }

    @Override
    public void updateLinkLastCheckTimeById(Long id, Timestamp lastCheckTime) {
        linkRepository.updateLinkLastCheckTimeById(id, lastCheckTime);
    }

    @Override
    public LinkSof getLinkPropertiesById(Long id) {
        return linkRepository.getLinkPropertiesById(id);
    }

    @Override
    public void updateCountOfCommentsById(Long id, Long count) {
        linkRepository.updateCountOfCommentsById(id, count);
    }

    @Override
    public void updateCountOfAnswersById(Long id, Long count) {
        linkRepository.updateCountOfAnswersById(id, count);

    }

}
