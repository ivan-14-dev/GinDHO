package com.gindho.client.service;

import com.gindho.client.model.AuthResponse;
import com.gindho.client.model.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {

    private static final String BASE_URL = "http://localhost:8080/api";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ApiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public AuthResponse login(LoginRequest request) throws Exception {
        String jsonBody = objectMapper.writeValueAsString(request);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), AuthResponse.class);
        } else {
            throw new RuntimeException("Erreur d'authentification: " + response.body());
        }
    }

    // Additional methods to be implemented:
    // - getPatients()
    // - createPatient()
    // - updatePatient()
    // - getRendezVous()
    // - createRendezVous()
    // - etc.
}
