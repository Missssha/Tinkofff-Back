package edu.java.bot.controllers;

import edu.java.bot.models.Request.LinkUpdateRequest;
import edu.java.bot.services.RestApiService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdatesApiController implements UpdatesApi {
    private final RestApiService restApiService;

    @Autowired
    public UpdatesApiController(RestApiService restApiService) {
        this.restApiService = restApiService;
    }

    public void updatesPost(
        @Parameter(in = ParameterIn.HEADER, description = "Accept header", required = true, schema = @Schema())
        @RequestHeader("Accept") String accept,
        @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid
        @RequestBody LinkUpdateRequest body
    ) {
        restApiService.sendNotification(body.getTgChatIds(), body.getUrl(), body.getDescription());
    }

    public void updatesPost(
        @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid
        @RequestBody LinkUpdateRequest body
    ) {
        restApiService.sendNotification(body.getTgChatIds(), body.getUrl(), body.getDescription());
    }
}
