package com.gindho.client.controller;

import com.gindho.client.GinDhoClient;
import com.gindho.client.service.ApiService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class RendezVousController {

    @FXML
    private Button calendarBtn;

    @FXML
    private Button newRdvBtn;

    @FXML
    private Button refreshBtn;

    private final ApiService apiService = new ApiService();

    @FXML
    private void initialize() {
        calendarBtn.setOnAction(event -> handleCalendar());
        newRdvBtn.setOnAction(event -> handleNewRdv());
        refreshBtn.setOnAction(event -> handleRefresh());
    }

    private void handleCalendar() {
        // TODO: Implement calendar view
        showAlert("Info", "Vue calendrier en cours de développement", Alert.AlertType.INFORMATION);
    }

    private void handleNewRdv() {
        // TODO: Implement new appointment dialog
        showAlert("Info", "Création de rendez-vous en cours de développement", Alert.AlertType.INFORMATION);
    }

    private void handleRefresh() {
        // TODO: Reload appointments
        showAlert("Info", "Liste actualisée", Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
