package edu.java.repository;

import edu.java.dto.Link;
import edu.java.dto.LinkSof;
import java.sql.Timestamp;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class LinkRepository {
    private final JdbcClient jdbcClient;

    @Transactional
    public int add(Link entity) {
        try {
            String sql =
                "insert into link(url, chat_id, last_check_time,created_at) "
                    + "values(:url,:chatId,:CheckTime,:createdAt)";
            jdbcClient.sql(sql)
                .param("url", entity.getUrl().toString())
                .param("chatId", entity.getChatId())
                .param("CheckTime", entity.getLastCheckTime())
                .param("createdAt", entity.getCreatedAt())
                .update();

        } catch (Exception e) {
            return -1;
        }
        return 1;
    }

    @Transactional
    public int remove(Long id) {
        try {
            String sql = "delete from link where id = ?";
            int count = jdbcClient.sql(sql).param(1, id).update();
            if (count == 0) {
                throw new RuntimeException("link not found");
            }
        } catch (RuntimeException e) {
            return -1;
        }
        return 1;
    }

    @Transactional(readOnly = true)
    public List<Link> findAll() {
        String sql = "select * from link";
        return jdbcClient.sql(sql).query(Link.class).list();
    }

    @Transactional(readOnly = true)
    public List<Link> findUnUpdatedLinks() {
        String sql = "select * from link where EXTRACT(SECOND FROM (now() -last_check_time )) > 30";
        return jdbcClient.sql(sql).query(Link.class).list();
    }

    @Transactional
    public void updateLinkLastCheckTimeById(Long id, Timestamp lastCheckTime) {
        String sql =
            "update link set  last_check_time = (:lastCheckTime) where id = :link_id";
        jdbcClient.sql(sql)
            .param("link_id", id)
            .param("lastCheckTime", lastCheckTime)
            .update();
    }

    @Transactional(readOnly = true)
    public LinkSof getLinkPropertiesById(Long id) {
        String sql = "select * from links_sof where link_id = ? ";
        return jdbcClient.sql(sql).param(1, id).query(LinkSof.class).single();
    }

    @Transactional
    public void updateCountOfCommentsById(Long id, Long count) {
        String sql =
            "update links_sof set  \"countOfComments\" = ? where link_id = ?";
        jdbcClient.sql(sql)
            .param(1, count)
            .param(2, id)
            .update();

    }

    @Transactional
    public void updateCountOfAnswersById(Long id, Long count) {
        String sql =
            "update links_sof set \"countOfAnswers\" = ? where link_id = ?";
        jdbcClient.sql(sql)
            .param(1, count)
            .param(2, id)
            .update();
    }

}
