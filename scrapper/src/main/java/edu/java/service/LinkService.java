package edu.java.service;

import edu.java.dto.Link;
import java.sql.Timestamp;
import java.util.List;

public interface LinkService {
    List<Link> getLinks();

    int addLink(Link link);

    int removeLink(Long id);

    void updateLinkLastCheckTime(Long id, Timestamp lastCheckTime);

    List<Link> getUnUpdatedLinks();

}
