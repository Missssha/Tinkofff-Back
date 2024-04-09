package edu.java.controllers;

import edu.java.dto.Link;
import edu.java.service.jdbc.JdbcLinkService;
import io.github.bucket4j.Bucket;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URI;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class LinksApiController implements LinksApi {

    private final JdbcLinkService jdbcLinkService;
    private final Bucket bucket;

    @Autowired
    public LinksApiController(JdbcLinkService jdbcLinkService, Bucket bucket) {
        this.jdbcLinkService = jdbcLinkService;
        this.bucket = bucket;
    }

    public void linksDelete(
        @Parameter(in = ParameterIn.HEADER, description = "", required = true, schema = @Schema())
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long id
    ) {
        try {
            bucket.tryConsume(1);
            jdbcLinkService.removeLink(id);
        } catch (Error e) {
            log.info(e);
        }

    }

    public void linksGet() {
        try {
            bucket.tryConsume(1);
            jdbcLinkService.getLinks();
        } catch (Error e) {
            log.info(e);
        }
    }

    public void linksPost(
        @Parameter(in = ParameterIn.HEADER, description = "", required = true, schema = @Schema())
        @RequestHeader(value = "url", required = true) URI url
    ) {
        try {
            bucket.tryConsume(1);
            Link link = new Link(url);
            jdbcLinkService.addLink(link);
        } catch (Error e) {
            log.info(e);
        }

    }

}
