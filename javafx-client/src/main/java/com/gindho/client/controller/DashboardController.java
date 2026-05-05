package com.gindho.client.controller;

import com.gindho.client.GinDhoClient;
import com.gindho.client.service.ApiService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userRoleLabel;

    @FXML
    private Button patientsBtn;

    @FXML
    private Button medecinsBtn;

    @FXML
    private Button rendezvousBtn;

    @FXML
    private Button logoutBtn;

    private final ApiService apiService = new ApiService();

    @FXML
    private void initialize() {
        // Set user info from session (to be implemented)
        String role = GinDhoClient.getUserRole();
        userRoleLabel.setText("Rôle: " + (role != null ? role : "N/A"));

        patientsBtn.setOnAction(event -> handlePatients());
        medecinsBtn.setOnAction(event -> handleMedecins());
        rendezvousBtn.setOnAction(event -> handleRendezVous());
        logoutBtn.setOnAction(event -> handleLogout());

        // Hide/show buttons based on role
        configureButtonsByRole(role);
    }

    private void configureButtonsByRole(String role) {
        if ("PATIENT".equals(role)) {
            medecinsBtn.setVisible(false);
        } else if ("MEDECIN".equals(role)) {
            // Medecin can see all
        } else if ("ADMIN".equals(role)) {
            // Admin can see all
        }
    }

    private void handlePatients() {
        try {
            GinDhoClient.showPatientView();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible d'ouvrir la vue patients", Alert.AlertType.ERROR);
        }
    }

    private void handleMedecins() {
        // Navigate to medecins view (to be implemented)
        showAlert("Info", "Fonctionnalité en cours de développement", Alert.AlertType.INFORMATION);
    }

    private void handleRendezVous() {
        try {
            GinDhoClient.showRendezVousView();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible d'ouvrir la vue rendez-vous", Alert.AlertType.ERROR);
        }
    }

    private void handleLogout() {
        GinDhoClient.setAuthToken(null);
        GinDhoClient.setUserRole(null);
        GinDhoClient.setUserId(null);
        try {
            GinDhoClient.showLoginView();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de retourner à la page de connexion", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
