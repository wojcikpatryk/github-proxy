package com.wojcik.patryk.githubproxy.web.rest;

import com.wojcik.patryk.githubproxy.web.rest.handler.GitHubHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GitHubRouting {

    @Bean
    public RouterFunction<ServerResponse> route(final GitHubHandler handler) {
        return RouterFunctions.route()
                .GET("/api/github/{login}/repositories", handler::handleGetRepositories)
                .build();
    }
}
