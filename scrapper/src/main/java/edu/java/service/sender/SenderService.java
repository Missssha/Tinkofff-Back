package edu.java.service.sender;

import org.springframework.stereotype.Service;
import request.LinkUpdateRequest;


@Service
public interface SenderService {
    void updateLink(LinkUpdateRequest linkUpdateRequest);
}
