package com.wojcik.patryk.githubproxy.domain.github.repository.predicate;

import com.wojcik.patryk.githubproxy.domain.github.repository.model.GitHubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GitHubRepositoryValidator {

    private final List<GitHubRepositoryPredicate> predicates;

    public boolean validate(GitHubRepository repository) {
        return predicates.stream()
                .allMatch(predicate -> predicate.test(repository));
    }
}
