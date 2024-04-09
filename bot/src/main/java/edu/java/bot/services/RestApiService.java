package edu.java.bot.services;

import request.LinkUpdateRequest;

public interface RestApiService {
    void sendNotification(LinkUpdateRequest linkUpdateRequest);

}
