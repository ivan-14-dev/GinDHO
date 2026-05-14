package com.gindho.client.controller;

import com.gindho.client.GinDhoClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;

public class MedecinController {

    @FXML
    private TableView<Medecin> medecinTable;

    @FXML
    private TableColumn<Medecin, Long> idColumn;

    @FXML
    private TableColumn<Medecin, String> nomColumn;

    @FXML
    private TableColumn<Medecin, String> prenomColumn;

    @FXML
    private TableColumn<Medecin, String> specialiteColumn;

    @FXML
    private TableColumn<Medecin, String> emailColumn;

    @FXML
    private Button addBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button backBtn;

    @FXML
    private void initialize() {
        backBtn.setOnAction(event -> handleBack());
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

    public static class Medecin {
        private Long id;
        private String nom;
        private String prenom;
        private String specialite;
        private String email;

        public Medecin(Long id, String nom, String prenom, String specialite, String email) {
            this.id = id;
            this.nom = nom;
            this.prenom = prenom;
            this.specialite = specialite;
            this.email = email;
        }

        public Long getId() { return id; }
        public String getNom() { return nom; }
        public String getPrenom() { return prenom; }
        public String getSpecialite() { return specialite; }
        public String getEmail() { return email; }
    }
}