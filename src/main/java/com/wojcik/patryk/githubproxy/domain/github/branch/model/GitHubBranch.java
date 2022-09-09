package com.wojcik.patryk.githubproxy.domain.github.branch.model;

import com.wojcik.patryk.githubproxy.domain.github.commit.model.GitHubCommit;

public record GitHubBranch(String name,
                           GitHubCommit commit) {

}
