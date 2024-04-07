package edu.java.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Entity
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Convert(converter = UriConverter.class)
    @Column(name = "url")
    private URI url;

    @Column(name = "last_check_time")
    private Timestamp lastCheckTime;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToMany
    private List<Chat> chats;

    public Link(URI url) {
        this.url = url;
    }
}
