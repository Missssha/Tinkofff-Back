package edu.java.bot.controllers;

import edu.java.bot.services.RestApiService;
import io.github.bucket4j.Bucket;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import request.LinkUpdateRequest;

@RestController
public class UpdatesApiController implements UpdatesApi {
    private final RestApiService restApiService;
    private final Bucket bucket;

    @Autowired
    public UpdatesApiController(RestApiService restApiService, Bucket bucket) {
        this.restApiService = restApiService;
        this.bucket = bucket;
    }

    public void updatesPost(
        @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid
        @RequestBody LinkUpdateRequest body
    ) {
        bucket.tryConsume(1);
        restApiService.sendNotification(body);
    }
}
