package com.wojcik.patryk.githubproxy.domain.github.repository.service;

import com.wojcik.patryk.githubproxy.config.github.mediatype.GitHubMediaType;
import com.wojcik.patryk.githubproxy.core.exception.GitHubException;
import com.wojcik.patryk.githubproxy.domain.github.error.model.GitHubError;
import com.wojcik.patryk.githubproxy.domain.github.repository.model.GitHubRepository;
import com.wojcik.patryk.githubproxy.domain.github.repository.predicate.GitHubRepositoryValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubRepositoryService {

    private final WebClient client;
    private final GitHubRepositoryValidator validator;

    public Flux<GitHubRepository> getRepositories(String login) {
        log.info("Sending request to GitHub for " + login + "'s repositories...");
        return client.get()
                .uri("/users/" + login + "/repos")
                .accept(GitHubMediaType.APPLICATION_GIT_HUB)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, this::error)
                .bodyToFlux(GitHubRepository.class)
                .filter(validator::validate);
    }

    private Mono<Throwable> error(ClientResponse response) {
        return response
                .bodyToMono(GitHubError.class)
                .flatMap(error -> Mono.defer(() -> Mono.error(new GitHubException(error.message(), response.statusCode()))));
    }
}
