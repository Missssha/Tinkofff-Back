package edu.java.repository;

import edu.java.dto.Link;
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
            String sql1 =
                "insert into link(url, last_check_time, created_at) "
                    + "values(:url,:lastCheckTime,:createdAt)";

            jdbcClient.sql(sql1)
                .param("url", entity.getUrl().toString())
                .param("lastCheckTime", entity.getLastCheckTime())
                .param("createdAt", entity.getCreatedAt())
                .update();

            String sql2 =
                "insert into chat_link(chat_id, link_id)"
                    + "values(:chatId, :linkId)";
            jdbcClient.sql(sql2)
                .param("chatId", entity.getChatId())
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
        String sql = "select * from link where EXTRACT(SECOND FROM (now() - last_check_time )) > 30";
        return jdbcClient.sql(sql).query(Link.class).list();
    }
}
