package edu.java.repository;

import edu.java.dto.Chat;
import edu.java.dto.Link;
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
            String sqlFromLink =
                "insert into link(url, last_check_time, created_at) "
                    + "values(:url,:last_check_time,:createdAt)";

            jdbcClient.sql(sqlFromLink)
                .param("url", entity.getUrl().toString())
                .param("last_check_time", entity.getLastCheckTime())
                .param("createdAt", entity.getCreatedAt())
                .update();

            Chat lastChat = entity.getChats().get(entity.getChats().size() - 1);
            long chatId = lastChat.getId();

            String sqlFromChatLink =
                "insert into chat_link(chat_id, link_id) "
                    + "values(:chatId,:linkId)";

            jdbcClient.sql(sqlFromChatLink)
                .param("chatId", chatId)
                .param("linkId", entity.getId())
                .update();

        } catch (Exception e) {
            return -1;
        }
        return 1;
    }

    @Transactional
    public int remove(Long id) {
        try {
            String sqlRemoveLink = "delete from link where id = ?";
            int count = jdbcClient.sql(sqlRemoveLink).param(1, id).update();

            String sqlRemoveChatLink = "delete from chat_link where link_id = ?";
            jdbcClient.sql(sqlRemoveChatLink).param(1, id).update();

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
    public List<Link> getUnUpdatedLinks() {
        String sql = "select * from link where EXTRACT(SECOND FROM (now() - last_check_time )) > 30";
        return jdbcClient.sql(sql).query(Link.class).list();
    }
}
