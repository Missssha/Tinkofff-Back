package edu.java.repository;

import edu.java.dto.Link;
import edu.java.jooq.tables.records.LinkRecord;
import edu.java.jooq.tables.records.LinksSofRecord;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static edu.java.jooq.Tables.LINK;
import static edu.java.jooq.Tables.LINKS_SOF;

@Repository
public class JooqLinkRepository {

    @Autowired
    private final DSLContext dslContext;

    public JooqLinkRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void add(Link entity) {
        dslContext.insertInto(LINK)
            .set(LINK.URL, entity.getUrl().toString())
            .set(LINK.CHAT_ID, entity.getChatId())
            .set(LINK.LAST_CHECK_TIME, offsetDateTimeFromTimestamp(entity.getLastCheckTime()))
            .set(LINK.CREATED_AT, offsetDateTimeFromTimestamp(entity.getCreatedAt()))
            .returning(LINK)
            .fetchOne();
    }

    public List<LinkRecord> findAll() {
        return dslContext.selectFrom(LINK).fetch();
    }

    public void remove(Long id) {
        dslContext.deleteFrom(LINK)
            .where(LINK.ID.equal(id))
            .execute();
    }

    public List<Link> findUnUpdatedLinks() {
        return dslContext.resultQuery("select * from LINK where EXTRACT(SECOND FROM (now() -last_check_time )) > 30")
            .fetchInto(Link.class); //TODO refactor?
    }

    public void updateLinkLastCheckTimeById(Long id, Timestamp timestamp) {
        dslContext.update(LINK)
            .set(LINK.LAST_CHECK_TIME, offsetDateTimeFromTimestamp(timestamp))
            .where(LINK.ID.eq(id))
            .returning(LINK)
            .fetchOne();
    }

    public LinksSofRecord getLinkPropertiesById(Long id) {
        return dslContext.selectFrom(LINKS_SOF)
            .where(LINKS_SOF.LINK_ID.eq(id))
            .fetchOne();

    }

    public void updateCountOfCommentsById(Long id, Long count) {
        dslContext.update(LINKS_SOF)
            .set(LINKS_SOF.COUNTOFCOMMENTS, count)
            .where(LINKS_SOF.LINK_ID.eq(id))
            .returning(LINKS_SOF)
            .fetchOne();
    }

    public void updateCountOfAnswersById(Long id, Long count) {
        dslContext.update(LINKS_SOF)
            .set(LINKS_SOF.COUNTOFANSWERS, count)
            .where(LINKS_SOF.LINK_ID.eq(id))
            .returning(LINKS_SOF)
            .fetchOne();
    }

    private OffsetDateTime offsetDateTimeFromTimestamp(Timestamp timestamp) {
        return OffsetDateTime.ofInstant(timestamp.toInstant(), ZoneId.of("UTC"));
    }

}
