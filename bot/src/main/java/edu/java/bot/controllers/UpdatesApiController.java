package edu.java.bot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.bot.models.Request.LinkUpdateRequest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdatesApiController implements UpdatesApi {

    private final ObjectMapper objectMapper;

    @Autowired
    public UpdatesApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<Void> updatesPost(
        @Parameter(in = ParameterIn.HEADER, description = "Accept header", required = true, schema = @Schema())
        @RequestHeader("Accept") String accept,
        @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid
        @RequestBody LinkUpdateRequest body
    ) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updatesPost(LinkUpdateRequest body) {
        return null;
    }
}
