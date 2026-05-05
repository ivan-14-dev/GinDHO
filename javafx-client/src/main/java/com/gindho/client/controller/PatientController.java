package com.gindho.client.controller;

import com.gindho.client.GinDhoClient;
import com.gindho.client.service.ApiService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PatientController {

    @FXML
    private TableView<PatientRow> tableView;

    @FXML
    private TableColumn<PatientRow, String> nomColumn;

    @FXML
    private TableColumn<PatientRow, String> numeroColumn;

    @FXML
    private TableColumn<PatientRow, String> telephoneColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Button addBtn;

    @FXML
    private Button refreshBtn;

    private final ApiService apiService = new ApiService();
    private final ObservableList<PatientRow> patientData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        setupTableColumns();
        tableView.setItems(patientData);
        
        addBtn.setOnAction(event -> handleAdd());
        refreshBtn.setOnAction(event -> loadPatients());
        searchField.textProperty().addListener((obs, oldVal, newVal) -> searchPatients(newVal));
        
        loadPatients();
    }

    private void setupTableColumns() {
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numeroPatient"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
    }

    private void loadPatients() {
        try {
            // TODO: Implement actual API call
            patientData.clear();
            // patientData.addAll(apiService.getPatients());
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de charger les patients", Alert.AlertType.ERROR);
        }
    }

    private void searchPatients(String searchTerm) {
        // TODO: Implement search via API
        if (searchTerm == null || searchTerm.isEmpty()) {
            loadPatients();
        }
    }

    private void handleAdd() {
        // TODO: Implement add patient dialog
        showAlert("Info", "Fonctionnalité en cours de développement", Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class PatientRow {
        private final String nom;
        private final String numeroPatient;
        private final String telephone;

        public PatientRow(String nom, String numeroPatient, String telephone) {
            this.nom = nom;
            this.numeroPatient = numeroPatient;
            this.telephone = telephone;
        }

        public String getNom() { return nom; }
        public String getNumeroPatient() { return numeroPatient; }
        public String getTelephone() { return telephone; }
    }
}
