package edu.java.bot.models.exception;

import org.springframework.web.reactive.function.client.ClientResponse;

public class ApiException extends Exception {
    public final ClientResponse response;

    public ApiException(ClientResponse response, String msg) {
        super(msg);
        this.response = response;
    }

}
