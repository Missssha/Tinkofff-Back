package edu.java.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.dto.Chat;
import edu.java.service.jdbc.JdbcChatService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TgChatApiController implements TgChatApi {

    private final JdbcChatService jdbcChatService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TgChatApiController.class);

    @Autowired
    public TgChatApiController(JdbcChatService jdbcChatService, ObjectMapper objectMapper, HttpServletRequest request) {
        this.jdbcChatService = jdbcChatService;
    }

    public void tgChatIdDelete(
        @Parameter(in = ParameterIn.PATH,
                   description = "",
                   required = true,
                   schema = @Schema())
        @PathVariable("id") Long id
    ) {
        jdbcChatService.removeChat(id);
    }

    public void tgChatIdPost(
        @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id")
        Long id
    ) {

        Chat chat = new Chat(id);

        jdbcChatService.addChat(chat);

    }

}
