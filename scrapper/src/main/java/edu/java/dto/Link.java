package edu.java.dto;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private long id;
    private URI url;
    private Timestamp lastCheckTime;
    private Timestamp createdAt;
    private List<Chat> chats;

    public Link(URI url) {
        this.url = url;
    }
}
