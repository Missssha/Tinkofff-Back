package edu.java.scrapper.service;

import edu.java.dto.Link;
import edu.java.repository.JdbcLinkRepository;
import edu.java.service.jdbc.JdbcLinkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class JdbcLinkServiceTest {

    @Mock
    private JdbcLinkRepository linkRepository;

    @InjectMocks
    private JdbcLinkService linkService;

    @Test
    public void testGetLinksCallsRepository() {
        List<Link> mockLinks = Arrays.asList(new Link());
        Mockito.when(linkRepository.findAll()).thenReturn(mockLinks);
        List<Link> links = linkService.getLinks();
        Mockito.verify(linkRepository).findAll();
        assertEquals(mockLinks, links);
    }
    @Test
    public void testAddLinkCallsRepositoryOnSuccess() {
        Link newLink = new Link();
        Mockito.doNothing().when(linkRepository).add(newLink);

        linkService.addLink(newLink);
        Mockito.verify(linkRepository).add(newLink);
    }

    @Test
    public void testGetUnUpdatedLinksCallsRepository() {
        List<Link> mockLinks = Arrays.asList(new Link());
        Mockito.when(linkRepository.getUnUpdatedLinks()).thenReturn(mockLinks);
        List<Link> links = linkService.getUnUpdatedLinks();
        Mockito.verify(linkRepository).getUnUpdatedLinks();
        assertEquals(mockLinks, links);
    }

}

