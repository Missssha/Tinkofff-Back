package edu.java.dto;

import lombok.Data;

@Data
public class GitHubRepository {
    private Long id;
    private String name;
    private String defaultBranch;
}
