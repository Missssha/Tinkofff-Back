package edu.java.bot.services;

import request.LinkUpdateRequest;

public interface RestApiServiceInterface {

    void sendNotification(LinkUpdateRequest linkUpdateRequest);

}
