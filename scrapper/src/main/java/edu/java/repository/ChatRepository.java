
package edu.java.repository;

import edu.java.dto.Chat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ChatRepository {
    private final JdbcClient jdbcClient;

    @Transactional
    public void add(Chat chatEntity) {
        String sql = "insert into chat(chat_id) values(:chatId)";
        jdbcClient.sql(sql)
            .param("chatId", chatEntity.getChatId())
            .update();
    }

    @Transactional
    public void remove(Long id) {
        String sqlRemoveChat = "delete from chat where chat_id = ?";
        jdbcClient.sql(sqlRemoveChat).param(1, id).update();

        String sqlRemoveChatLink = "delete from chat_link where chat_id = ?";
        jdbcClient.sql(sqlRemoveChatLink).param(1, id).update();
    }

    @Transactional(readOnly = true)
    public List<Chat> findAll() {
        String sql = "select * from chat";
        return jdbcClient.sql(sql).query(Chat.class).list();
    }
}
