package com.gindho.client.animation;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Utility class for common animations.
 */
public class AnimationManager {
    
    public static void fadeIn(Node node, double durationMs) {
        FadeTransition fade = new FadeTransition(Duration.millis(durationMs), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
    
    public static void slideInFromRight(Node node, double durationMs) {
        TranslateTransition slide = new TranslateTransition(Duration.millis(durationMs), node);
        slide.setFromX(50);
        slide.setToX(0);
        slide.setInterpolator(Interpolator.EASE_OUT);
        
        FadeTransition fade = new FadeTransition(Duration.millis(durationMs), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        
        ParallelTransition parallel = new ParallelTransition(slide, fade);
        parallel.play();
    }
    
    public static void slideInFromBottom(Node node, double durationMs) {
        TranslateTransition slide = new TranslateTransition(Duration.millis(durationMs), node);
        slide.setFromY(30);
        slide.setToY(0);
        slide.setInterpolator(Interpolator.EASE_OUT);
        
        FadeTransition fade = new FadeTransition(Duration.millis(durationMs), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        
        ParallelTransition parallel = new ParallelTransition(slide, fade);
        parallel.play();
    }
    
    public static void pulseAnimation(Node node) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), node);
        scaleUp.setToX(1.05);
        scaleUp.setToY(1.05);
        
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), node);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);
        
        SequentialTransition pulse = new SequentialTransition(scaleUp, scaleDown);
        pulse.setCycleCount(2);
        pulse.play();
    }
    
    public static void chartGrowAnimation(Node chartNode) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(800), chartNode);
        scale.setFromX(0);
        scale.setFromY(0);
        scale.setToX(1);
        scale.setToY(1);
        scale.setInterpolator(Interpolator.EASE_OUT);
        scale.play();
    }
    
    public static void staggeredLoad(Pane container, double delayBetweenMs) {
        int index = 0;
        for (Node child : container.getChildren()) {
            child.setOpacity(0);
            PauseTransition delay = new PauseTransition(Duration.millis(index * delayBetweenMs));
            delay.setOnFinished(e -> slideInFromBottom(child, 400));
            delay.play();
            index++;
        }
    }
    
    public static void shakeAnimation(Node node) {
        TranslateTransition tt1 = new TranslateTransition(Duration.millis(50), node);
        tt1.setFromX(0);
        tt1.setToX(-8);
        
        TranslateTransition tt2 = new TranslateTransition(Duration.millis(50), node);
        tt2.setFromX(-8);
        tt2.setToX(8);
        
        TranslateTransition tt3 = new TranslateTransition(Duration.millis(50), node);
        tt3.setFromX(8);
        tt3.setToX(-5);
        
        TranslateTransition tt4 = new TranslateTransition(Duration.millis(50), node);
        tt4.setFromX(-5);
        tt4.setToX(0);
        
        SequentialTransition shake = new SequentialTransition(tt1, tt2, tt3, tt4);
        shake.play();
    }
    
    public static void bounceAnimation(Node node) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), node);
        scaleUp.setToX(1.1);
        scaleUp.setToY(1.1);
        
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), node);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);
        
        SequentialTransition bounce = new SequentialTransition(scaleUp, scaleDown);
        bounce.setCycleCount(2);
        bounce.play();
    }
    
    public static void hoverScale(Node node) {
        node.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), node);
            st.setToX(1.02);
            st.setToY(1.02);
            st.play();
        });
        
        node.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), node);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }
}