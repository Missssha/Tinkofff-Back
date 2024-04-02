package edu.java.service.jooq;

import edu.java.domain.jooq.tables.records.LinkRecord;
import edu.java.domain.jooq.tables.records.LinksSofRecord;
import edu.java.dto.Link;
import edu.java.dto.LinkSof;
import edu.java.repository.jooq.JooqLinkRepository;
import edu.java.service.LinkService;
import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JooqLinkService implements LinkService {

    private final JooqLinkRepository linkRepository;

    @Override
    public List<Link> getLinks() {
        try {
            List<LinkRecord> records = linkRepository.findAll();
            List<Link> links = new ArrayList<>();
            for (LinkRecord linkRecord : records) {
                Link link = new Link();
                link.setLastCheckTime(Timestamp.valueOf(linkRecord.getLastCheckTime().toString()));
                link.setCreatedAt(Timestamp.valueOf(linkRecord.getCreatedAt().toString()));
                link.setUrl(URI.create(linkRecord.getUrl()));
                link.setId(linkRecord.getId());
                links.add(link);
            }
            return links;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error getting a link");
        }
    }

    @Override
    public void addLink(Link link) {
        linkRepository.add(link);
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
        return linkRepository.findUnUpdatedLinks();
    }

    @Override
    public void updateLinkLastCheckTimeById(Long id, Timestamp lastCheckTime) {
        linkRepository.updateLinkLastCheckTimeById(id, lastCheckTime);
    }

    @Override
    public LinkSof getLinkPropertiesById(Long id) {
        LinksSofRecord linksSofRecord = linkRepository.getLinkPropertiesById(id);
        LinkSof linkSof = new LinkSof();
        linkSof.setLinkId(linksSofRecord.getId());
        linkSof.setId(linksSofRecord.getId());
        linkSof.setCountOfAnswer(linksSofRecord.getCountofanswers());
        linkSof.setCountOfComments(linksSofRecord.getCountofcomments());

        return linkSof;
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
