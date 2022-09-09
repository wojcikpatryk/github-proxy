package com.wojcik.patryk.githubproxy.config.github.mediatype;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GitHubMediaType {

    public final static MediaType APPLICATION_GIT_HUB = MediaType.parseMediaType("application/vnd.github+json");
}
