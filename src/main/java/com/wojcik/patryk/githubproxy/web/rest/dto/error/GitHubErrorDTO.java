package com.wojcik.patryk.githubproxy.web.rest.dto.error;

import com.wojcik.patryk.githubproxy.core.exception.GitHubException;

public record GitHubErrorDTO(Integer statusCode,
                             String message) {

    public static GitHubErrorDTO of(GitHubException exception) {
        return new GitHubErrorDTO(exception.getStatus().value(), exception.getMessage());
    }
}
