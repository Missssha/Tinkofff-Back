package edu.java.service.jdbc;

import edu.java.dto.Link;
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
        try {
            return linkRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error getting links");
        }
    }

    @Override
    public void addLink(Link link) {
        try {
            linkRepository.add(link);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error adding a link");
        }
    }

    @Override
    public void removeLink(Long id) {
        try {
            linkRepository.remove(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("link not found");
        }
    }

    @Override
    public void updateLinkLastCheckTime(Long id, Timestamp lastCheckTime) {
        linkRepository.updateLinkLastCheckTimeById(id, lastCheckTime);
    }

    @Override
    public List<Link> getUnUpdatedLinks() {
        return linkRepository.getUnUpdatedLinks();
    }

}
