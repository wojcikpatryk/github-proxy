package com.wojcik.patryk.githubproxy.domain.github.branch.service;

import com.wojcik.patryk.githubproxy.domain.github.branch.model.GitHubBranch;
import com.wojcik.patryk.githubproxy.domain.github.repository.model.GitHubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.net.URI;

@Slf4j

@Service
@RequiredArgsConstructor
public class GitHubBranchService {

    private final WebClient client;

    public Flux<GitHubBranch> getBranches(GitHubRepository repository) {
        return client.get()
                .uri(branchesUri(repository))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(GitHubBranch.class);
    }

    private URI branchesUri(GitHubRepository repository) {
        return URI
                .create(repository.branchesUrl()
                        .transform(url -> url.substring(0, url.indexOf("{"))));
    }
}
