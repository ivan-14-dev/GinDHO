package com.gindho.client.i18n;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ToggleButton;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Manages internationalization (i18n) for the application.
 */
public class I18nManager {
    
    private static I18nManager instance;
    private ObjectProperty<Locale> currentLocale;
    private ResourceBundle bundle;
    
    private I18nManager() {
        currentLocale = new SimpleObjectProperty<>(Locale.getDefault());
        loadBundle();
    }
    
    public static I18nManager getInstance() {
        if (instance == null) {
            instance = new I18nManager();
        }
        return instance;
    }
    
    private void loadBundle() {
        bundle = ResourceBundle.getBundle("i18n.messages", currentLocale.get());
    }
    
    public String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            return "!" + key + "!";
        }
    }
    
    public void setLocale(Locale locale) {
        currentLocale.set(locale);
        loadBundle();
    }
    
    public Locale getLocale() {
        return currentLocale.get();
    }
    
    public ObjectProperty<Locale> localeProperty() {
        return currentLocale;
    }
    
    // Helper for creating components binded to i18n
    public Label createLabel(String key) {
        Label label = new Label();
        label.textProperty().bind(
            currentLocale.map(l -> {
                ResourceBundle b = ResourceBundle.getBundle("i18n.messages", l);
                try {
                    return b.getString(key);
                } catch (Exception e) {
                    return "!" + key + "!";
                }
            })
        );
        return label;
    }
    
    public Button createButton(String key) {
        Button button = new Button();
        button.textProperty().bind(
            currentLocale.map(l -> {
                ResourceBundle b = ResourceBundle.getBundle("i18n.messages", l);
                try {
                    return b.getString(key);
                } catch (Exception e) {
                    return "!" + key + "!";
                }
            })
        );
        return button;
    }
    
    public ToggleButton createToggleButton(String key) {
        ToggleButton button = new ToggleButton();
        button.textProperty().bind(
            currentLocale.map(l -> {
                ResourceBundle b = ResourceBundle.getBundle("i18n.messages", l);
                try {
                    return b.getString(key);
                } catch (Exception e) {
                    return "!" + key + "!";
                }
            })
        );
        return button;
    }
    
    public Hyperlink createHyperlink(String key) {
        Hyperlink link = new Hyperlink();
        link.textProperty().bind(
            currentLocale.map(l -> {
                ResourceBundle b = ResourceBundle.getBundle("i18n.messages", l);
                try {
                    return b.getString(key);
                } catch (Exception e) {
                    return "!" + key + "!";
                }
            })
        );
        return link;
    }
    
    // Method to update text of existing components
    public void updateText(Label label, String key) {
        label.textProperty().bind(
            currentLocale.map(l -> {
                ResourceBundle b = ResourceBundle.getBundle("i18n.messages", l);
                try {
                    return b.getString(key);
                } catch (Exception e) {
                    return "!" + key + "!";
                }
            })
        );
    }
    
    public void updateText(Button button, String key) {
        button.textProperty().bind(
            currentLocale.map(l -> {
                ResourceBundle b = ResourceBundle.getBundle("i18n.messages", l);
                try {
                    return b.getString(key);
                } catch (Exception e) {
                    return "!" + key + "!";
                }
            })
        );
    }
    
    public void updateText(ToggleButton button, String key) {
        button.textProperty().bind(
            currentLocale.map(l -> {
                ResourceBundle b = ResourceBundle.getBundle("i18n.messages", l);
                try {
                    return b.getString(key);
                } catch (Exception e) {
                    return "!" + key + "!";
                }
            })
        );
    }
    
    public void updateText(Hyperlink link, String key) {
        link.textProperty().bind(
            currentLocale.map(l -> {
                ResourceBundle b = ResourceBundle.getBundle("i18n.messages", l);
                try {
                    return b.getString(key);
                } catch (Exception e) {
                    return "!" + key + "!";
                }
            })
        );
    }
}