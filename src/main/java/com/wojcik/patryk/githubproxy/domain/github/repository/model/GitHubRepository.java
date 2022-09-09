package com.wojcik.patryk.githubproxy.domain.github.repository.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wojcik.patryk.githubproxy.domain.github.owner.model.GitHubOwner;

public record GitHubRepository(String name,
                               GitHubOwner owner,
                               Boolean fork,
                               @JsonProperty("branches_url")
                               String branchesUrl) {

}
