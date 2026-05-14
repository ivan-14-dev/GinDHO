package com.gindho.client.controller;

import com.gindho.client.GinDhoClient;
import com.gindho.client.animation.AnimationManager;
import com.gindho.client.i18n.I18nManager;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginController implements Initializable {

    @FXML private StackPane rootPane;
    @FXML private VBox leftPanel;
    @FXML private VBox rightPanel;
    @FXML private VBox loginForm;
    @FXML private Label systemName;
    @FXML private Label systemSubtitle;
    @FXML private Label welcomeLabel;
    @FXML private Label signInLabel;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private CheckBox rememberMe;
    @FXML private Button continueButton;
    @FXML private Hyperlink forgotPassword;
    @FXML private Hyperlink ssoLink;
    @FXML private Label emailLabel;
    @FXML private Label passwordLabel;
    @FXML private HBox languageSelector;
    @FXML private Circle logoCircle;
    @FXML private Label logoText;

    private I18nManager i18n = I18nManager.getInstance();
    
    // HTTP client for API calls
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_BASE_URL = "http://localhost:8080/api";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupAnimations();
        setupBindings();
        setupEventHandlers();
        setupLanguageSelector();
    }

    private void setupAnimations() {
        TranslateTransition leftSlide = new TranslateTransition(Duration.millis(800), leftPanel);
        leftSlide.setFromX(-100);
        leftSlide.setToX(0);
        FadeTransition leftFade = new FadeTransition(Duration.millis(800), leftPanel);
        leftFade.setFromValue(0);
        leftFade.setToValue(1);
        ParallelTransition leftAnim = new ParallelTransition(leftSlide, leftFade);

        TranslateTransition rightSlide = new TranslateTransition(Duration.millis(800), rightPanel);
        rightSlide.setFromX(100);
        rightSlide.setToX(0);
        FadeTransition rightFade = new FadeTransition(Duration.millis(800), rightPanel);
        rightFade.setFromValue(0);
        rightFade.setToValue(1);
        ParallelTransition rightAnim = new ParallelTransition(rightSlide, rightFade);
        rightAnim.setDelay(Duration.millis(200));

        TranslateTransition formSlide = new TranslateTransition(Duration.millis(600), loginForm);
        formSlide.setFromY(50);
        formSlide.setToY(0);
        FadeTransition formFade = new FadeTransition(Duration.millis(600), loginForm);
        formFade.setFromValue(0);
        formFade.setToValue(1);
        ParallelTransition formAnim = new ParallelTransition(formSlide, formFade);
        formAnim.setDelay(Duration.millis(500));

        leftAnim.play();
        rightAnim.play();
        formAnim.play();
        AnimationManager.pulseAnimation(logoCircle);
    }

    private void setupBindings() {
        welcomeLabel.textProperty().bind(
                i18n.localeProperty().map(l -> i18n.getString("login.welcome"))
        );
        signInLabel.textProperty().bind(
                i18n.localeProperty().map(l -> i18n.getString("login.signin"))
        );
        emailLabel.textProperty().bind(
                i18n.localeProperty().map(l -> i18n.getString("login.email"))
        );
        passwordLabel.textProperty().bind(
                i18n.localeProperty().map(l -> i18n.getString("login.password"))
        );
        rememberMe.textProperty().bind(
                i18n.localeProperty().map(l -> i18n.getString("login.remember"))
        );
        continueButton.textProperty().bind(
                i18n.localeProperty().map(l -> i18n.getString("login.continue"))
        );
        forgotPassword.textProperty().bind(
                i18n.localeProperty().map(l -> i18n.getString("login.forgot"))
        );
        ssoLink.textProperty().bind(
                i18n.localeProperty().map(l -> i18n.getString("login.sso"))
        );

        systemName.setText("GinDHO");
        systemSubtitle.setText("Gestion Intelligente D'Hôpitaux");
    }

    private void setupEventHandlers() {
        continueButton.setOnAction(e -> handleLogin());
        emailField.setOnAction(e -> passwordField.requestFocus());
        passwordField.setOnAction(e -> handleLogin());
        forgotPassword.setOnAction(e -> showForgotPassword());
        ssoLink.setOnAction(e -> handleSSO());

        emailField.focusedProperty().addListener((obs, old, newVal) -> {
            if (newVal) {
                AnimationManager.pulseAnimation(emailField);
            }
        });
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        AnimationManager.pulseAnimation(continueButton);

        continueButton.textProperty().unbind();
        continueButton.setDisable(true);
        continueButton.setText("...");

        new Thread(() -> {
            try {
                String json = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", 
                    email.replace("\"", "\\\""), 
                    password.replace("\"", "\\\"")
                );
                
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_BASE_URL + "/auth/login"))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(json))
                    .build();

                HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

                Platform.runLater(() -> {
                    if (response.statusCode() == 200) {
                        try {
                            @SuppressWarnings("unchecked")
                            Map<String, Object> loginResp = objectMapper.readValue(response.body(), Map.class);
                            String token = (String) loginResp.get("token");
                            String respEmail = (String) loginResp.get("email");
                            String role = (String) loginResp.get("role");
                            Number userIdNum = (Number) loginResp.get("userId");
                            Long userId = userIdNum != null ? userIdNum.longValue() : null;
                            
                            // Store auth data
                            GinDhoClient.setAuthToken(token);
                            GinDhoClient.setUserRole(role);
                            GinDhoClient.setUserId(userId);
                            GinDhoClient.setUserEmail(respEmail);
                            
                            FadeTransition fadeOut = new FadeTransition(Duration.millis(500), rootPane);
                            fadeOut.setFromValue(1);
                            fadeOut.setToValue(0);
                            fadeOut.setOnFinished(e -> {
                                try {
                                    loadDashboard();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                    showAlert("Erreur", "Impossible d'ouvrir le dashboard", Alert.AlertType.ERROR);
                                }
                            });
                            fadeOut.play();
                        } catch (IOException e) {
                            e.printStackTrace();
                            continueButton.textProperty().bind(
                                i18n.localeProperty().map(l -> i18n.getString("login.continue"))
                            );
                            continueButton.setDisable(false);
                            showAlert("Erreur", "Réponse serveur invalide", Alert.AlertType.ERROR);
                        }
                    } else {
                        continueButton.textProperty().bind(
                            i18n.localeProperty().map(l -> i18n.getString("login.continue"))
                        );
                        continueButton.setDisable(false);
                        AnimationManager.shakeAnimation(loginForm);
                        showAlert("Erreur", "Email ou mot de passe incorrect", Alert.AlertType.ERROR);
                    }
                });
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    continueButton.textProperty().bind(
                        i18n.localeProperty().map(l -> i18n.getString("login.continue"))
                    );
                    continueButton.setDisable(false);
                    showAlert("Erreur", "Impossible de contacter le serveur: " + e.getMessage(), Alert.AlertType.ERROR);
                });
            }
        }).start();
    }

    private void loadDashboard() throws IOException {
        Parent dashboard = FXMLLoader.load(getClass().getResource("/views/dashboard.fxml"));
        Scene scene = new Scene(dashboard);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setMaximized(true);

        dashboard.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(600), dashboard);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    private void showForgotPassword() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("GinDHO");
        alert.setHeaderText(null);
        alert.setContentText("Contactez votre administrateur système pour réinitialiser votre mot de passe.");
        alert.showAndWait();
    }

    private void handleSSO() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("GinDHO - SSO");
        alert.setHeaderText(null);
        alert.setContentText("Redirection vers le fournisseur d'identité...");
        alert.showAndWait();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setupLanguageSelector() {
        ComboBox<String> langBox = new ComboBox<>();
        langBox.getItems().addAll("🇫🇷 Français", "🇺🇸 English", "🇪🇸 Español");
        langBox.setValue("🇫🇷 Français");
        langBox.setStyle("-fx-background-color: transparent; -fx-border-color: rgba(255,255,255,0.3); -fx-border-radius: 20; -fx-text-fill: white;");

        langBox.setOnAction(e -> {
            String selected = langBox.getValue();
            if (selected.contains("Français")) {
                i18n.setLocale(java.util.Locale.FRENCH);
            } else if (selected.contains("English")) {
                i18n.setLocale(java.util.Locale.ENGLISH);
            } else if (selected.contains("Español")) {
                i18n.setLocale(new java.util.Locale("es"));
            }
        });

        languageSelector.getChildren().add(langBox);
    }
}
