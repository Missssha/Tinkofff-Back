package edu.java.repository.jpa;

import edu.java.dto.Link;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    List<Link> findAll();

    @Modifying
    @Query("SELECT l FROM Link l WHERE (CURRENT_TIMESTAMP() - l.lastCheckTime) > 30000"
        + "ORDER BY l.lastCheckTime ASC LIMIT 100")
    List<Link> findOldLinks();

    Link save(Link link);

    Link findLinkById(Long id);

    void removeLinkById(Long id);

    @Modifying
    @Query(value = "delete from chat_link where link_id = ?1",
           nativeQuery = true)
    void removeLinksInChatsLinks(Long id);

    @Modifying
    @Query("update Link set  lastCheckTime = :lastCheckTime where id = :linkId")
    void updateLinkLastCheckTimeById(Timestamp lastCheckTime, Long linkId);
}
