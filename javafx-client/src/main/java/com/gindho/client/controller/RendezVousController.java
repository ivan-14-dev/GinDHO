package com.gindho.client.controller;

import com.gindho.client.GinDhoClient;
import com.gindho.client.service.ApiService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class RendezVousController {

    @FXML
    private TableView<RendezVousRow> rendezVousTable;

    @FXML
    private Button calendarBtn;

    @FXML
    private Button newRdvBtn;

    @FXML
    private Button refreshBtn;

    @FXML
    private Button backBtn;

    private final ApiService apiService = new ApiService();

    @FXML
    private void initialize() {
        calendarBtn.setOnAction(event -> handleCalendar());
        newRdvBtn.setOnAction(event -> handleNewRdv());
        refreshBtn.setOnAction(event -> handleRefresh());
        backBtn.setOnAction(event -> handleBack());
    }

    private void handleCalendar() {
        showAlert("Info", "Vue calendrier en cours de développement", Alert.AlertType.INFORMATION);
    }

    private void handleNewRdv() {
        showAlert("Info", "Création de rendez-vous en cours de développement", Alert.AlertType.INFORMATION);
    }

    private void handleRefresh() {
        showAlert("Info", "Liste actualisée", Alert.AlertType.INFORMATION);
    }

    private void handleBack() {
        try {
            GinDhoClient.showDashboardView();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de retourner au tableau de bord", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class RendezVousRow {
        private Long id;
        private String date;
        private String heure;
        private String patient;
        private String medecin;
        private String statut;

        public RendezVousRow(Long id, String date, String heure, String patient, String medecin, String statut) {
            this.id = id;
            this.date = date;
            this.heure = heure;
            this.patient = patient;
            this.medecin = medecin;
            this.statut = statut;
        }

        public Long getId() { return id; }
        public String getDate() { return date; }
        public String getHeure() { return heure; }
        public String getPatient() { return patient; }
        public String getMedecin() { return medecin; }
        public String getStatut() { return statut; }
    }
}
