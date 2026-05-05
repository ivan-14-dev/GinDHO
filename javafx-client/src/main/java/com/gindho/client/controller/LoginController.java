package com.gindho.client.controller;

import com.gindho.client.GinDhoClient;
import com.gindho.client.model.AuthResponse;
import com.gindho.client.model.LoginRequest;
import com.gindho.client.service.ApiService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;

    private final ApiService apiService = new ApiService();

    @FXML
    private void initialize() {
        loginBtn.setOnAction(event -> handleLogin());
        registerBtn.setOnAction(event -> handleRegister());
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs", Alert.AlertType.ERROR);
            return;
        }

        try {
            LoginRequest request = new LoginRequest(email, password);
            AuthResponse response = apiService.login(request);

            GinDhoClient.setAuthToken(response.getToken());
            GinDhoClient.setUserRole(response.getRole());
            GinDhoClient.setUserId(response.getUserId());

            GinDhoClient.showDashboardView();
        } catch (Exception e) {
            showAlert("Erreur", "Échec de la connexion: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void handleRegister() {
        // Navigate to registration view (to be implemented)
        showAlert("Info", "Fonctionnalité en cours de développement", Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
