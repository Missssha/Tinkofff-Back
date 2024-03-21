package edu.java.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        @Parameter(in = ParameterIn.HEADER, description = "", required = true, schema = @Schema())
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId,
        @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
        @Valid @RequestBody RemoveLinkRequest body
    ) {
        jdbcLinkService.removeLink(tgChatId);
        try {
            return new ResponseEntity<LinkResponse>(objectMapper.readValue(
                "{\n  \"id\" : 1,\n  \"url\" : \"http://example.com/aeiou\"\n}",
                LinkResponse.class
            ), HttpStatus.OK);
        } catch (IOException e) {
            LOGGER.error(errorString, e);
            return new ResponseEntity<LinkResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ListLinksResponse> linksGet(
        @Parameter(in = ParameterIn.HEADER, description = "", required = true, schema = @Schema())
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId
    ) {
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

    public ResponseEntity<LinkResponse> linksPost(
        @Parameter(in = ParameterIn.HEADER, description = "", required = true, schema = @Schema())
        @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId,
        @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody
        AddLinkRequest body
    ) {
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
