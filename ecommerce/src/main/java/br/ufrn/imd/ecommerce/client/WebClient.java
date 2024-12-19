package br.ufrn.imd.ecommerce.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WebClient {

    private String address;

    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    public WebClient(String address) {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        this.objectMapper = new ObjectMapper();
        this.address = address;
    }

    public HttpResponse<String> post(String endpoint, Object body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address.concat(endpoint)))
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public <T> T get(String endpoint, Class<T> responseType) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address.concat(endpoint)))
                .version(HttpClient.Version.HTTP_2)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Falha ao consumir endpoint:" + endpoint + ", status code: " + response.statusCode());
        }

        return objectMapper.readValue(response.body(), responseType);
    }
}
