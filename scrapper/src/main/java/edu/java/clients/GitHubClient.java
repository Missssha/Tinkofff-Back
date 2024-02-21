package edu.java.clients;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class GitHubClient {
    private Long id;
    private String name;
    private String fullname;
    private String discription;
    private String language;
    private String visibility;

    public GitHubClient(long newId, String newnName, String newFullname,
        String newDiscription, String newLanguage, String newVisibility) {
        this.id = newId;
        this.name = newnName;
        this.fullname = newFullname;
        this.discription = newDiscription;
        this.language = newLanguage;
        this.visibility = newVisibility;
    }


}
