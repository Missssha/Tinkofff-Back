package edu.java.repository.jooq;

import edu.java.domain.jooq.tables.records.ChatRecord;
import edu.java.dto.Chat;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static edu.java.domain.jooq.Tables.CHAT;

@Repository
public class JooqChatRepository {
    @Autowired
    private final DSLContext dslContext;

    public JooqChatRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void add(Chat entity) {
        dslContext.insertInto(CHAT)
            .set(CHAT.CHAT_ID, entity.getChatId())
            .returning(CHAT)
            .fetchOne();
    }

    public List<ChatRecord> findAll() {
        return dslContext.selectFrom(CHAT).fetch();
    }

    public void remove(Long id) {
        dslContext.deleteFrom(CHAT)
            .where(CHAT.ID.equal(id))
            .execute();
    }
}
