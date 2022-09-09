package com.wojcik.patryk.githubproxy.web.rest.handler;

import com.wojcik.patryk.githubproxy.core.exception.GitHubException;
import com.wojcik.patryk.githubproxy.domain.github.GitHubFacade;
import com.wojcik.patryk.githubproxy.web.rest.dto.error.GitHubErrorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubHandler {

    private final GitHubFacade gitHubFacade;

    public Mono<ServerResponse> handleGetRepositories(ServerRequest request) {
        return checkRequest(request)
                .flatMapMany(req -> gitHubFacade.getRepositories(getLogin(request)))
                .collectList()
                .doOnSuccess(repositories -> log.info("Success - " + repositories.size() + " repositories found: " + repositories))
                .flatMap(ServerResponse.ok()::bodyValue)
                .doOnError(throwable -> log.error("Error during request for repositories: " + throwable.getMessage(), throwable))
                .onErrorResume(
                        GitHubException.class,
                        throwable -> ServerResponse
                                .status(HttpStatus.NOT_FOUND)
                                .bodyValue(GitHubErrorDTO.of(throwable))
                )
                .onErrorResume(
                        InvalidMediaTypeException.class,
                        throwable -> ServerResponse
                                .status(HttpStatus.NOT_ACCEPTABLE)
                                .bodyValue(new GitHubErrorDTO(HttpStatus.NOT_ACCEPTABLE.value(), throwable.getMessage()))
                );
    }

    private Mono<ServerRequest> checkRequest(ServerRequest request) {
        return Mono.just(request)
                .filter(req -> req.headers().accept().contains(MediaType.APPLICATION_JSON))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new InvalidMediaTypeException(getMediaType(request), "Invalid media type header"))));
    }

    private String getMediaType(ServerRequest request) {
        return request.headers().accept().stream()
                .findFirst()
                .map(MimeType::toString)
                .orElse(null);
    }

    private String getLogin(ServerRequest request) {
        return Optional.of(request.pathVariable("login"))
                .orElse(null);
    }
}
