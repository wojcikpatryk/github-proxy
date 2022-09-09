package com.wojcik.patryk.githubproxy.config.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Profile("!test")
public class WebClientConfig {

    private final String gitHubApiUrl;

    public WebClientConfig(@Value("${github.api.server.url}") String gitHubApiUrl) {
        this.gitHubApiUrl = gitHubApiUrl;
    }

    @Bean
    WebClient webClient() {
        return WebClient.builder()
                .baseUrl(gitHubApiUrl)
                .build();
    }
}
