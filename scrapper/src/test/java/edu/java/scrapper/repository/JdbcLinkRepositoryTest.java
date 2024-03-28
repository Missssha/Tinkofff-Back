package edu.java.scrapper.repository;

import edu.java.dto.Chat;
import edu.java.dto.Link;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.scrapper.IntegrationTest;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class JdbcLinkRepositoryTest extends IntegrationTest {

    @Autowired
    @Mock
    private JdbcLinkRepository linkRepository;


    @Test
    @Transactional
    @Rollback
    public void testAdding() throws URISyntaxException, SQLException, FileNotFoundException, LiquibaseException {
        Database database = IntegrationTest.runMigrations(POSTGRES);
        assertThat(database).isNotNull();

        Link link = new Link();
        Chat chat = new Chat(1L);
        List<Chat> chats = new ArrayList<>();

        chats.add(chat);
        link.setUrl(new URI("https://api.github.com/"));
        link.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        link.setLastCheckTime(new Timestamp(System.currentTimeMillis()));
        link.setChats(chats);
        linkRepository.add(link);
        List<Link> links = linkRepository.findAll();

        assertEquals(new URI("https://api.github.com/"), links.get(0).getUrl());
        linkRepository.remove(1L);
    }

}
