package com.gindho.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GinDhoClient extends Application {

    private static Stage primaryStage;
    private static String authToken;
    private static String userRole;
    private static Long userId;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        stage.setTitle("GinDHO - Système Médical");
        
        // Load login view
        showLoginView();
        
        stage.setWidth(1024);
        stage.setHeight(768);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void showLoginView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GinDhoClient.class.getResource("/views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
    }

    public static void showDashboardView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GinDhoClient.class.getResource("/views/dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
    }

    public static void showPatientView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GinDhoClient.class.getResource("/views/patient.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
    }

    public static void showMedecinView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GinDhoClient.class.getResource("/views/medecin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
    }

    public static void showRendezVousView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GinDhoClient.class.getResource("/views/rendezvous.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String token) {
        authToken = token;
    }

    public static String getUserRole() {
        return userRole;
    }

    public static void setUserRole(String role) {
        userRole = role;
    }

    public static Long getUserId() {
        return userId;
    }

    public static void setUserId(Long id) {
        userId = id;
    }

    public static void main(String[] args) {
        launch();
    }
}
