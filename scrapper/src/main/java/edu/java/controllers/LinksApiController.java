package edu.java.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.dto.Link;
import edu.java.models.Request.AddLinkRequest;
import edu.java.models.Request.RemoveLinkRequest;
import edu.java.models.Response.LinkResponse;
import edu.java.models.Response.ListLinksResponse;
import edu.java.service.jdbc.JdbcLinkService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinksApiController implements LinksApi {

    private final JdbcLinkService jdbcLinkService;
    private static final Logger LOGGER = LoggerFactory.getLogger(LinksApiController.class);
    private final String acceptString = "Accept";
    private final String applicationJsonString = "application/json";
    private final String errorString = "Couldn't serialize response for content type application/json";

    private final ObjectMapper objectMapper;

    @Autowired
    public LinksApiController(JdbcLinkService jdbcLinkService, ObjectMapper objectMapper) {
        this.jdbcLinkService = jdbcLinkService;
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<LinkResponse> linksDelete(
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId,
        @Valid
        @RequestBody
        RemoveLinkRequest body
    ) {
        jdbcLinkService.removeLink(tgChatId);
        String accept = acceptString;
        if (accept != null && accept.contains(applicationJsonString)) {
            return new ResponseEntity<LinkResponse>(new LinkResponse(), HttpStatus.OK);
        }

        return new ResponseEntity<LinkResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ListLinksResponse> linksGet(
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId
    ) {

        for (Link link : jdbcLinkService.getLinks()) {
            LOGGER.info(link.getUrl().toString());
        }

        String accept = acceptString;

        if (accept != null && accept.contains(applicationJsonString)) {
            try {
                return new ResponseEntity<ListLinksResponse>(objectMapper.readValue(
                    "{\n  \"size\" : 6,\n  \"links\" : [ {\n    \"id\" : 0,\n    \"url\" : \"http://example.com/aeiou\"\n  }, {\n    \"id\" : 0,\n    \"url\" : \"http://example.com/aeiou\"\n  } ]\n}",
                    ListLinksResponse.class
                ), HttpStatus.OK);
            } catch (IOException e) {
                LOGGER.error(errorString, e);
                return new ResponseEntity<ListLinksResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<ListLinksResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<LinkResponse> linksPost(
        @Parameter(in = ParameterIn.HEADER, description = "", required = true, schema = @Schema())
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId,
        @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody
        AddLinkRequest body
    ) {

        int time = Integer.parseInt(System.getenv("time"));
        Link link = new Link();
        link.setChatId(tgChatId);
        link.setUrl(URI.create("https://github.com/Missssha/Tinkofff-Back"));
        link.setCreatedAt(new Timestamp(time));
        link.setLastCheckTime(new Timestamp(time));

        jdbcLinkService.addLink(link);

        try {
            return new ResponseEntity<LinkResponse>(objectMapper.readValue(
                "{\n  \"id\" : 0,\n  \"url\" : \"http://example.com/aeiou\"\n}",
                LinkResponse.class
            ), HttpStatus.OK);
        } catch (IOException e) {
            LOGGER.error(errorString, e);
            return new ResponseEntity<LinkResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
