package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;

public class Controllers {
    
    public static VBox createControlButtons() {
        VBox controls = new VBox(10);
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.CENTER_LEFT);
        
        Button pauseBtn = new Button("⏸ Pause");
        Button menuBtn = new Button("🏠 Menu");
        
        // Basic styling
        String buttonStyle = "-fx-background-color: #34495e; -fx-text-fill: white; -fx-padding: 8 15;";
        pauseBtn.setStyle(buttonStyle);
        menuBtn.setStyle(buttonStyle);
        
        // Basic actions
        pauseBtn.setOnAction(e -> showSimplePause());
        menuBtn.setOnAction(e -> showMenuConfirmation());
        
        controls.getChildren().addAll(pauseBtn, menuBtn);
        return controls;
    }
    
    private static void showSimplePause() {
        Alert pauseAlert = new Alert(Alert.AlertType.CONFIRMATION);
        pauseAlert.setTitle("Game Paused");
        pauseAlert.setHeaderText("What would you like to do?");
        
        ButtonType resume = new ButtonType("Resume");
        ButtonType menu = new ButtonType("Main Menu");
        
        pauseAlert.getButtonTypes().setAll(resume, menu);
        
        pauseAlert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == menu) {
                showMenuConfirmation();
            }
        });
    }
    
    private static void showMenuConfirmation() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Leave Game");
        confirm.setHeaderText("Return to main menu?");
        confirm.setContentText("Your progress will be lost.");
        
        if (confirm.showAndWait().get() == ButtonType.OK) {
            // Add your menu navigation logic here
            System.out.println("Going to main menu...");
        }
    }
}