package com.wojcik.patryk.githubproxy.web.rest.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.wojcik.patryk.githubproxy.AbstractTest
import com.wojcik.patryk.githubproxy.domain.github.branch.model.GitHubBranch
import com.wojcik.patryk.githubproxy.domain.github.commit.model.GitHubCommit
import com.wojcik.patryk.githubproxy.domain.github.owner.model.GitHubOwner
import com.wojcik.patryk.githubproxy.domain.github.repository.model.GitHubRepository
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.reactive.function.server.MockServerRequest
import reactor.test.StepVerifier
import spock.lang.Unroll

class GitHubHandlerTest extends AbstractTest {

    @Autowired
    private GitHubHandler handler

    @Autowired
    private MockWebServer server

    @Unroll("Routing should return #status for #mediaType / forked: #forked")
    def 'routing should return repository'() {
        given:
        def repository = getRepository(forked)
        def branch = getBranch()
        def request = MockServerRequest.builder()
                .pathVariable("login", "test")
                .header(HttpHeaders.ACCEPT, mediaType)
                .build()
        server.enqueue(new MockResponse()
                .setBody(new ObjectMapper().writeValueAsString(List.of(repository)))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
        server.enqueue(new MockResponse()
                .setBody(new ObjectMapper().writeValueAsString(List.of(branch)))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))

        when:
        def result = handler.handleGetRepositories(request)

        then:
        StepVerifier.create(result)
                .expectNextMatches { it.statusCode() == status }
                .verifyComplete()

        where:
        mediaType                        | forked | status
        MediaType.APPLICATION_JSON_VALUE | false  | HttpStatus.OK
        MediaType.APPLICATION_JSON_VALUE | true   | HttpStatus.OK
        MediaType.APPLICATION_XML_VALUE  | false  | HttpStatus.NOT_ACCEPTABLE
    }

    private GitHubRepository getRepository(Boolean fork) {
        new GitHubRepository(
                "test",
                new GitHubOwner("test"),
                fork,
                "http://localhost:" + server.getPort() + "/repos/test/test/branches{/branch}"
        )
    }

    private GitHubBranch getBranch() {
        new GitHubBranch(
                "test",
                new GitHubCommit("test")
        )
    }
}
