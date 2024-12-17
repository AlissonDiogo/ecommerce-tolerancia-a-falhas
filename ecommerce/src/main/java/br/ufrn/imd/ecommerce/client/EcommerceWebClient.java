package br.ufrn.imd.ecommerce.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class EcommerceWebClient {
    private final WebClient webClient;
    private String host;
    private String port;

    public EcommerceWebClient(String host, String port) {
        this.host = host;
        this.port = port;
        this.webClient = WebClient.builder()
                .baseUrl(host.concat(port))
                .build();
    }

    public String send(String endpoint) {
        return webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
