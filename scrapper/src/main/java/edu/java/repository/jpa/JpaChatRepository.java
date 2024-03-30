package edu.java.repository.jpa;

import edu.java.dto.Chat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface JpaChatRepository extends JpaRepository<Chat, Long> {
    Chat save(Chat chatEntity);

    void removeById(Long chatId);

    @Modifying
    @Query(value = "delete from chat_link where chat_id = ?1",
           nativeQuery = true)
    void removeChatInChatsLinks(Long id);

    List<Chat> findAll();

    Chat findChatById(Long id);
}