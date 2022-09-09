package com.wojcik.patryk.githubproxy.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class GitHubException extends Exception {

    @Getter
    private final HttpStatus status;


    public GitHubException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
