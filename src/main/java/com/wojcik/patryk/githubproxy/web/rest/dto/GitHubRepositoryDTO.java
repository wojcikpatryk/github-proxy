package com.wojcik.patryk.githubproxy.web.rest.dto;

import com.wojcik.patryk.githubproxy.domain.github.branch.model.GitHubBranch;
import com.wojcik.patryk.githubproxy.domain.github.repository.model.GitHubRepository;
import lombok.Builder;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record GitHubRepositoryDTO(String name,
                                  String owner,
                                  Set<GitHubBranchDTO> branches) {

    public static GitHubRepositoryDTO of(GitHubRepository repository,
                                         Set<GitHubBranch> branches) {
        return builder()
                .name(repository.name())
                .owner(repository.owner().login())
                .branches(branches.stream()
                        .map(GitHubBranchDTO::of)
                        .collect(Collectors.toSet())
                )
                .build();
    }
}
