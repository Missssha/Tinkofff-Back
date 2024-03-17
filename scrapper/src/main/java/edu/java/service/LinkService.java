package edu.java.service;

import edu.java.dto.Link;
import edu.java.dto.LinkSof;
import java.sql.Timestamp;
import java.util.List;

public interface LinkService {
    List<Link> getLinks();

    List<Link> getOldLinks();

    int addLink(Link link);

    int removeLink(Long id);

    void updateLinkLastCheckTimeById(Long id, Timestamp lastCheckTime);

    LinkSof getLinkPropertiesById(Long id);

    void updateCountOfCommentsById(Long id, Long count);

    void updateCountOfAnswersById(Long id, Long count);

}
