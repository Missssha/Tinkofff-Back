package edu.java.scrapper.repository;

import edu.java.dto.Chat;
import edu.java.dto.Link;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@Rollback
@Transactional
public class JdbcLinkRepositoryTest extends IntegrationTest {

    @Autowired
    private JdbcLinkRepository linkRepository;

    @Test
    public void testAdding() throws URISyntaxException {
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

        assertThat(links).hasSize(1);
        assertThat(links.get(0).getUrl()).isEqualTo(link.getUrl());
    }

    @Test
    public void testRemoving() throws URISyntaxException {

        Link link = new Link();
        Chat chat = new Chat(1L);
        List<Chat> chats = new ArrayList<>();

        chats.add(chat);
        link.setUrl(new URI("https://api.github.com/"));
        link.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        link.setLastCheckTime(new Timestamp(System.currentTimeMillis()));
        link.setChats(chats);
        linkRepository.remove(1L);

        List<Link> links = linkRepository.findAll();
        assertThat(links).isEmpty();
    }

    @Test
    public void testFinding() throws URISyntaxException {
        Link link1 = new Link();
        Link link2 = new Link();
        Chat chat1 = new Chat(1L);
        Chat chat2 = new Chat(2L);
        List<Chat> chats1 = new ArrayList<>();
        List<Chat> chats2 = new ArrayList<>();

        chats1.add(chat1);
        chats2.add(chat2);

        link1.setUrl(new URI("https://api.github.com/"));
        link1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        link1.setLastCheckTime(new Timestamp(System.currentTimeMillis()));
        link1.setChats(chats1);

        link2.setUrl(new URI("https://api.stackexchange.com/2.3"));
        link2.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        link2.setLastCheckTime(new Timestamp(System.currentTimeMillis()));
        link2.setChats(chats2);

        linkRepository.add(link1);
        linkRepository.add(link2);
        List<Link> links = linkRepository.findAll();

        assertThat(links).isNotNull();


    }

    @Test
    @Transactional
    @Rollback
    public void testRemoving() throws URISyntaxException {
        Link link = new Link();
        Chat chat = new Chat(1L);
        List<Chat> chats = new ArrayList<>();

        chats.add(chat);
        link.setUrl(new URI("https://api.github.com/"));
        link.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        link.setLastCheckTime(new Timestamp(System.currentTimeMillis()));
        link.setChats(chats);
        linkRepository.add(link);
        linkRepository.remove(1L);
        List<Link> links = linkRepository.findAll();

        assertEquals(0, links.size());
    }

    @Test
    @Transactional
    @Rollback
    public void testFinding() throws URISyntaxException {
        Link link1 = new Link();
        Link link2 = new Link();
        Chat chat1 = new Chat(1L);
        Chat chat2 = new Chat(2L);
        List<Chat> chats1 = new ArrayList<>();
        List<Chat> chats2 = new ArrayList<>();

        chats1.add(chat1);
        chats2.add(chat2);

        link1.setUrl(new URI("https://api.github.com/"));
        link1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        link1.setLastCheckTime(new Timestamp(System.currentTimeMillis()));
        link1.setChats(chats1);

        link2.setUrl(new URI("https://api.stackexchange.com/2.3"));
        link2.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        link2.setLastCheckTime(new Timestamp(System.currentTimeMillis()));
        link2.setChats(chats2);

        linkRepository.add(link1);
        linkRepository.add(link2);
        List<Link> links = linkRepository.findAll();

        assertEquals(2, links.size());
        linkRepository.remove(link1.getId());
        linkRepository.remove(link2.getId());
    }

}
