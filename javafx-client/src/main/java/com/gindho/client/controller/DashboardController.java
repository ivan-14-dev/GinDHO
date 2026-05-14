package com.gindho.client.controller;

import com.gindho.client.GinDhoClient;
import com.gindho.client.animation.AnimationManager;
import com.gindho.client.components.WidgetCard;
import com.gindho.client.dragdrop.DragAndDropManager;
import com.gindho.client.i18n.I18nManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * Controller for the main dashboard view with drag-and-drop widgets and i18n support.
 */
public class DashboardController {

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userRoleLabel;

    @FXML
    private GridPane widgetsGrid;

    @FXML
    private Button logoutBtn;

    private final DragAndDropManager dragAndDropManager;
    private final I18nManager i18n = I18nManager.getInstance();

    public DashboardController() {
        this.dragAndDropManager = new DragAndDropManager(); // Will set grid after FXML loads
    }

    @FXML
    private void initialize() {
        // Initialize drag-and-drop manager with the grid
        dragAndDropManager.setGrid(widgetsGrid);

        // Set up user info (to be implemented from session)
        setupUserInfo();

        // Create and add widget cards to the grid with initial positions
        createAndAddWidgets();

        // Apply entrance animations
        applyEntranceAnimations();

        // Set up logout button
        setupLogoutButton();
    }

    private void setupUserInfo() {
        // Get user info from session
        String userName = GinDhoClient.getUserEmail() != null ? GinDhoClient.getUserEmail() : "John Doe";
        String userRole = GinDhoClient.getUserRole();

        userNameLabel.setText(userName);
        userRoleLabel.setText("Rôle: " + (userRole != null ? userRole : "N/A"));

        // Apply i18n to labels
        i18n.localeProperty().addListener((obs, oldLocale, newLocale) -> {
            String rolePrefix = i18n.getString("dashboard.role.prefix");
            userRoleLabel.setText(rolePrefix + " " + (userRole != null ? userRole : "N/A"));
        });
    }

    private void createAndAddWidgets() {
        // Define widget data: [iconText, titleKey, buttonTextKey, row, column, actionHandler]
        Object[][] widgetData = {
                {"🏥", "widget.patients.title", "widget.patients.button", 0, 0, (javafx.event.EventHandler<javafx.event.ActionEvent>) this::handlePatients},
                {"👨‍⚕️", "widget.medecins.title", "widget.medecins.button", 0, 1, (javafx.event.EventHandler<javafx.event.ActionEvent>) this::handleMedecins},
                {"📅", "widget.rendezvous.title", "widget.rendezvous.button", 0, 2, (javafx.event.EventHandler<javafx.event.ActionEvent>) this::handleRendezVous},
                {"📊", "widget.analytics.title", "widget.analytics.button", 1, 0, (javafx.event.EventHandler<javafx.event.ActionEvent>) this::handleAnalytics},
                {"💰", "widget.revenue.title", "widget.revenue.button", 1, 1, (javafx.event.EventHandler<javafx.event.ActionEvent>) this::handleRevenue},
                {"⏰", "widget.appointments.title", "widget.appointments.button", 1, 2, (javafx.event.EventHandler<javafx.event.ActionEvent>) this::handleAppointments},
                {"📝", "widget.prescriptions.title", "widget.prescriptions.button", 2, 0, (javafx.event.EventHandler<javafx.event.ActionEvent>) this::handlePrescriptions}
        };

        for (Object[] data : widgetData) {
            String iconText = (String) data[0];
            String titleKey = (String) data[1];
            String buttonKey = (String) data[2];
            int row = (int) data[3];
            int col = (int) data[4];
            javafx.event.EventHandler<javafx.event.ActionEvent> action = (javafx.event.EventHandler<javafx.event.ActionEvent>) data[5];

            // Create widget card with i18n support
            WidgetCard card = new WidgetCard(iconText, titleKey, buttonKey, action);
            
            // Make draggable and set up as drop target
            dragAndDropManager.makeDraggable(card);
            dragAndDropManager.setupDropTarget(card);

            // Add to grid
            widgetsGrid.add(card, col, row);
        }
    }

    private void applyEntranceAnimations() {
        // Fade in the entire dashboard
        widgetsGrid.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(800), widgetsGrid);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    private void setupLogoutButton() {
        logoutBtn.setText(i18n.getString("sidebar.logout"));
        logoutBtn.setOnAction(event -> handleLogout());

        // Update logout button text when locale changes
        i18n.localeProperty().addListener((obs, oldLocale, newLocale) -> {
            logoutBtn.setText(i18n.getString("sidebar.logout"));
        });
    }

    // Event handlers for widget buttons
    private void handlePatients(javafx.event.ActionEvent event) {
        try {
            GinDhoClient.showPatientView();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible d'ouvrir la vue patients", Alert.AlertType.ERROR);
        }
    }

    private void handleMedecins(javafx.event.ActionEvent event) {
        try {
            GinDhoClient.showMedecinView();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible d'ouvrir la vue médecins", Alert.AlertType.ERROR);
        }
    }

    private void handleRendezVous(javafx.event.ActionEvent event) {
        try {
            GinDhoClient.showRendezVousView();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible d'ouvrir la vue rendez-vous", Alert.AlertType.ERROR);
        }
    }

    private void handleAnalytics(javafx.event.ActionEvent event) {
        showAlert("Info", "Fonctionnalité d'analyses en cours de développement", Alert.AlertType.INFORMATION);
    }

    private void handleRevenue(javafx.event.ActionEvent event) {
        showAlert("Info", "Fonctionnalité de revenus en cours de développement", Alert.AlertType.INFORMATION);
    }

    private void handleAppointments(javafx.event.ActionEvent event) {
        showAlert("Info", "Fonctionnalité de rendez-vous à venir en cours de développement", Alert.AlertType.INFORMATION);
    }

    private void handlePrescriptions(javafx.event.ActionEvent event) {
        showAlert("Info", "Fonctionnalité de prescriptions en cours de développement", Alert.AlertType.INFORMATION);
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