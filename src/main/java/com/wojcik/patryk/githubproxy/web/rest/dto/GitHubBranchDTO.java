package com.wojcik.patryk.githubproxy.web.rest.dto;

import com.wojcik.patryk.githubproxy.domain.github.branch.model.GitHubBranch;

public record GitHubBranchDTO(String name,
                              String sha) {

    public static GitHubBranchDTO of(GitHubBranch branch) {
        return new GitHubBranchDTO(branch.name(), branch.commit().sha());
    }
}
