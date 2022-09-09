package com.wojcik.patryk.githubproxy.domain.github;

import com.wojcik.patryk.githubproxy.domain.github.branch.service.GitHubBranchService;
import com.wojcik.patryk.githubproxy.domain.github.repository.model.GitHubRepository;
import com.wojcik.patryk.githubproxy.domain.github.repository.service.GitHubRepositoryService;
import com.wojcik.patryk.githubproxy.web.rest.dto.GitHubRepositoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class GitHubFacade {

    private final GitHubRepositoryService repositoryService;
    private final GitHubBranchService branchService;

    public Flux<GitHubRepositoryDTO> getRepositories(String login) {
        return repositoryService.getRepositories(login)
                .flatMap(this::getBranches);
    }

    private Mono<GitHubRepositoryDTO> getBranches(GitHubRepository repository) {
        return branchService.getBranches(repository)
                .collectList()
                .map(branches -> GitHubRepositoryDTO.of(repository, Set.copyOf(branches)));
    }
}
