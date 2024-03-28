package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class GitHubRepository {
    private Long id;
    private String name;
    @JsonProperty("default_branch")
    private String defaultBranch;
    @JsonProperty("pushed_at")
    private Timestamp lastPush;
}
