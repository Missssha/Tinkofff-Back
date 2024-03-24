package edu.java.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.dto.Link;
import edu.java.service.jdbc.JdbcLinkService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinksApiController implements LinksApi {

    private final JdbcLinkService jdbcLinkService;

    @Autowired
    public LinksApiController(JdbcLinkService jdbcLinkService, ObjectMapper objectMapper) {
        this.jdbcLinkService = jdbcLinkService;
    }

    public void linksDelete(
        @Parameter(in = ParameterIn.HEADER, description = "", required = true, schema = @Schema())
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long id
    ) {
        jdbcLinkService.removeLink(id);
    }

    public void linksGet() {
        jdbcLinkService.getLinks();
    }

    public void linksPost(
        @Parameter(in = ParameterIn.HEADER, description = "", required = true, schema = @Schema())
        @RequestHeader(value = "url", required = true) URI url
    ) {
        Link link = new Link(url);

        jdbcLinkService.addLink(link);

    }

}
