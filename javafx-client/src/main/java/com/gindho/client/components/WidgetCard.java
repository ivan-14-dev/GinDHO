package com.gindho.client.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A card representing a widget with an icon, title, and action button.
 * Supports internationalization (i18n) for title and button text.
 */
public class WidgetCard extends VBox {
    
    private final Button actionButton;
    private final javafx.scene.text.Text titleText;
    private final ObjectProperty<String> titleKey = new SimpleObjectProperty<>();
    private final ObjectProperty<String> buttonKey = new SimpleObjectProperty<>();
    private final ObjectProperty<Locale> locale = new SimpleObjectProperty<>(Locale.getDefault());
    private final javafx.scene.text.Text iconTextNode;
    
    public WidgetCard(String iconText, String titleKey, String buttonKey, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        getStyleClass().add("widget-card");
        setPadding(new Insets(15));
        setSpacing(10);
        setPrefWidth(250);
        setMaxWidth(Double.MAX_VALUE);
        
        // Icon
        iconTextNode = new javafx.scene.text.Text(iconText);
        iconTextNode.setFont(Font.font("System", FontWeight.BOLD, 24));
        iconTextNode.setFill(javafx.scene.paint.Color.web("#FF6B6B"));
        
        // Title text
        titleText = new javafx.scene.text.Text();
        titleText.setFont(Font.font("System", FontWeight.BOLD, 16));
        
        // Action button
        actionButton = new Button();
        actionButton.getStyleClass().add("widget-action-btn");
        actionButton.setOnAction(action);
        actionButton.setPrefWidth(Double.MAX_VALUE);
        
        // Layout
        HBox header = new HBox(10, iconTextNode, titleText);
        header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        getChildren().addAll(header, actionButton);
        
        // Set keys and bind texts
        setTitleKey(titleKey);
        setButtonKey(buttonKey);
        
        // Update texts when locale changes
        locale.addListener((obs, oldLoc, newLoc) -> {
            updateTexts();
        });
    }
    
    public String getTitleKey() {
        return titleKey.get();
    }
    
    public void setTitleKey(String key) {
        titleKey.set(key);
        updateTexts();
    }
    
    public ObjectProperty<String> titleKeyProperty() {
        return titleKey;
    }
    
    public String getButtonKey() {
        return buttonKey.get();
    }
    
    public void setButtonKey(String key) {
        buttonKey.set(key);
        updateTexts();
    }
    
    public ObjectProperty<String> buttonKeyProperty() {
        return buttonKey;
    }
    
    public void setLocale(Locale locale) {
        this.locale.set(locale);
    }
    
    public Locale getLocale() {
        return locale.get();
    }
    
    public ObjectProperty<Locale> localeProperty() {
        return locale;
    }
    
    public Button getActionButton() {
        return actionButton;
    }
    
    private void updateTexts() {
        Locale currentLocale = locale.get();
        ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", currentLocale);
        
        String titleValue = "!" + titleKey.get() + "!";
        String buttonValue = "!" + buttonKey.get() + "!";
        
        try {
            titleValue = bundle.getString(titleKey.get());
        } catch (Exception e) {
            // Keep default
        }
        
        try {
            buttonValue = bundle.getString(buttonKey.get());
        } catch (Exception e) {
            // Keep default
        }
        
        titleText.setText(titleValue);
        actionButton.setText(buttonValue);
    }
}