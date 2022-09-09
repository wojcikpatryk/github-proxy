package com.wojcik.patryk.githubproxy.domain.github.repository.predicate;

import com.wojcik.patryk.githubproxy.domain.github.repository.model.GitHubRepository;
import org.springframework.stereotype.Component;

@Component
public class GitHubForkedRepositoryPredicate implements GitHubRepositoryPredicate {

    @Override
    public boolean test(GitHubRepository repository) {
        return !repository.fork();
    }
}
