package detectiveCalico;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Controllers {
    
    // Create Pause + Menu buttons, bound to this stage
    public static HBox createControlButtons(Stage primaryStage) {
        HBox controls = new HBox(10);
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.CENTER_RIGHT);
        
        Button pauseBtn = new Button("⏸ Pause");
        Button menuBtn = new Button("🏠 Menu");
        
        String buttonStyle = "-fx-background-color: #34495e; -fx-text-fill: white; -fx-padding: 8 15;";
        pauseBtn.setStyle(buttonStyle);
        menuBtn.setStyle(buttonStyle);
        
        pauseBtn.setOnAction(e -> showSimplePause(primaryStage));
        menuBtn.setOnAction(e -> showMenuConfirmation(primaryStage));
        
        controls.getChildren().addAll(pauseBtn, menuBtn);
        return controls;
    }
    
    private static void showSimplePause(Stage primaryStage) {
        Alert pauseAlert = new Alert(Alert.AlertType.CONFIRMATION);
        pauseAlert.setTitle("Game Paused");
        pauseAlert.setHeaderText("What would you like to do?");
        
        ButtonType resume = new ButtonType("Resume");
        ButtonType menu = new ButtonType("Main Menu");
        
        pauseAlert.getButtonTypes().setAll(resume, menu);
        
        pauseAlert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == menu) {
                showMenuConfirmation(primaryStage);
            }
        });
    }
    
    private static void showMenuConfirmation(Stage primaryStage) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Leave Game");
        confirm.setHeaderText("Return to main menu?");
        confirm.setContentText("Your progress will be lost.");
        
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            goToMenu(primaryStage);
        }
    }
    
    // Main menu
    public static void goToMenu(Stage primaryStage) {
        try {
            new Menu().start(primaryStage);
            primaryStage.setFullScreenExitHint("");
            primaryStage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Second room (from FirstRoom outro)
    public static void goToSecondRoom(Stage primaryStage) {
        try {
            SecondRoom second = new SecondRoom();
            second.start(primaryStage);
            primaryStage.setFullScreenExitHint("");
            primaryStage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Third room (from SecondRoom outro)
    public static void goToThirdRoom(Stage primaryStage) {
        try {
            ThirdRoom third = new ThirdRoom();
            third.setPage1(primaryStage);   // your intro page
            primaryStage.setFullScreenExitHint("");
            primaryStage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 NEW: FinalAnswer (from ThirdRoom next button)
    public static void goToFinalAnswer(Stage primaryStage) {
        try {
            FinalAnswer finalScreen = new FinalAnswer();
            finalScreen.start(primaryStage);
            primaryStage.setFullScreenExitHint("");
            primaryStage.setFullScreen(true);   // force fullscreen when opened from ThirdRoom
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

