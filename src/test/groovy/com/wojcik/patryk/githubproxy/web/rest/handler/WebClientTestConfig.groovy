package com.wojcik.patryk.githubproxy.web.rest.handler

import okhttp3.mockwebserver.MockWebServer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@Profile("test")
class WebClientTestConfig {

    @Bean
    MockWebServer mockWebServer() {
        def mockWebServer = new MockWebServer()
        mockWebServer.start()
        mockWebServer
    }

    @Bean
    WebClient testWebClient(MockWebServer server) {
        WebClient.builder()
                .baseUrl("http://localhost:" + server.getPort())
                .build()
    }
}
