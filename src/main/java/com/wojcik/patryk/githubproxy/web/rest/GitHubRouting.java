package com.wojcik.patryk.githubproxy.web.rest;

import com.wojcik.patryk.githubproxy.web.rest.dto.GitHubRepositoryDTO;
import com.wojcik.patryk.githubproxy.web.rest.dto.error.GitHubErrorDTO;
import com.wojcik.patryk.githubproxy.web.rest.handler.GitHubHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GitHubRouting {

    @RouterOperations({
            @RouterOperation(
                    path = "/api/github/{login}/repositories",
                    beanClass = GitHubHandler.class,
                    beanMethod = "handleGetRepositories",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    operation = @Operation(operationId = "getRepositories", responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Successful get repositories",
                                    content = @Content(schema = @Schema(implementation = GitHubRepositoryDTO.class))
                            ),
                            @ApiResponse(
                                    responseCode = "404",
                                    description = "User not found",
                                    content = @Content(schema = @Schema(implementation = GitHubErrorDTO.class))
                            ),
                            @ApiResponse(
                                    responseCode = "406",
                                    description = "Invaid media type value",
                                    content = @Content(schema = @Schema(implementation = GitHubErrorDTO.class))
                            )},
                            parameters = @Parameter(in = ParameterIn.PATH, name = "login"))
            )
    })
    @Bean
    public RouterFunction<ServerResponse> route(final GitHubHandler handler) {
        return RouterFunctions.route()
                .GET("/api/github/{login}/repositories", handler::handleGetRepositories)
                .build();
    }
}
