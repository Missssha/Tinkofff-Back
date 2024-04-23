package edu.java.controllers;

import edu.java.dto.Chat;
import edu.java.service.ChatService;
import io.github.bucket4j.Bucket;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TgChatApiController implements TgChatApi {

    private final ChatService chatService;
    private final Bucket bucket;

    private static final Logger LOGGER = LoggerFactory.getLogger(TgChatApiController.class);

    @Autowired
    public TgChatApiController(ChatService chatService, Bucket bucket) {
        this.chatService = chatService;
        this.bucket = bucket;
    }

    public void tgChatIdDelete(
        @Parameter(in = ParameterIn.PATH,
                   description = "",
                   required = true,
                   schema = @Schema())
        @PathVariable("id") Long id
    ) {
        chatService.removeChat(id);
    }

    public void tgChatIdPost(
        @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("id")
        Long id
    ) {

        Chat chat = new Chat(id);

        chatService.addChat(chat);

    }

}
