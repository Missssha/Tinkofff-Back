package edu.java.bot.users;

import edu.java.bot.models.SessionState;
import java.net.URI;
import java.util.List;
import lombok.Data;

@Data
public class User {
    private Long id;
    private List<URI> sites;

    public User(SessionState sessionState) {
        this.state = sessionState;
    }

    public void setState(SessionState state) {
        this.state = state;
    }

    private SessionState state;

    public User(Long id, List<URI> sites, SessionState state) {
        this.id = id;
        this.sites = sites;
        this.state = state;

    }

    public User(Long id, SessionState state) {
        this.id = id;
        this.state = state;
    }

    public User() {}

    public void setId(long id) {
        this.id = id;
    }

    public void setSites(List<URI> sites) {
        this.sites = sites;
    }
}
