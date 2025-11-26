package DetectiveCalico;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;


public class MainMenu extends Application {

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        stage.setTitle("Detective Calico - Main Menu");
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);

        // Get screen dimensions for fullscreen
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        // Background ImageView (changed to menu.jpg)
        Image bgImage = new Image(new FileInputStream("img/menu.jpg"));
        ImageView bgView = new ImageView(bgImage);
        bgView.setPreserveRatio(false);
        bgView.setFitWidth(screenWidth);
        bgView.setFitHeight(screenHeight);

        // Title ImageView
        Image titleImage = new Image(new FileInputStream("img/Detective-Calico.png"));
        ImageView titleImageView = new ImageView(titleImage);
        titleImageView.setFitWidth(400);  // Adjust as needed
        titleImageView.setFitHeight(100); // Adjust as needed
        titleImageView.setPreserveRatio(true);

        // Play Button (with image)
        Image playImage = new Image(new FileInputStream("img/Play.png"));
        ImageView playImageView = new ImageView(playImage);
        playImageView.setFitWidth(100);  // Adjust size as needed
        playImageView.setFitHeight(50);
        playImageView.setPreserveRatio(true);
        Button playButton = new Button();
        playButton.setGraphic(playImageView);
        playButton.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5;");
        playButton.setOnAction(e -> System.out.println("Play button clicked - Launch game here"));

        // Scoreboard Button (with image)
        Image scoreboardImage = new Image(new FileInputStream("img/Scoreboard.png"));
        ImageView scoreboardImageView = new ImageView(scoreboardImage);
        scoreboardImageView.setFitWidth(250);
        scoreboardImageView.setFitHeight(200);
        scoreboardImageView.setPreserveRatio(true);
        Button scoreboardButton = new Button();
        scoreboardButton.setGraphic(scoreboardImageView);
        scoreboardButton.setStyle("-fx-background-color: gray; -fx-border-color: black; -fx-border-width: 2; -fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5;");
        scoreboardButton.setOnAction(e -> System.out.println("Scoreboard button clicked - Show scores here"));

        // Help Button (with image)
        Image helpImage = new Image(new FileInputStream("img/Help.png"));
        ImageView helpImageView = new ImageView(helpImage);
        helpImageView.setFitWidth(100);
        helpImageView.setFitHeight(50);
        helpImageView.setPreserveRatio(true);
        Button helpButton = new Button();
        helpButton.setGraphic(helpImageView);
        helpButton.setStyle("-fx-background-color: gray; -fx-border-color: black; -fx-border-width: 2; -fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5;");
        helpButton.setOnAction(e -> System.out.println("Help button clicked - Show help here"));

        HBox container = new HBox();
        container.setPadding(new Insets(0, 0, 0, 200));  // 200px left padding (adjust as needed)
        container.setAlignment(Pos.BOTTOM_LEFT);
        
        // Layout: VBox for vertical stacking (left-aligned and shifted left)
        VBox menuVBox = new VBox(100);  // 30px spacing
        menuVBox.setAlignment(Pos.BOTTOM_LEFT);  // Left-justify the elements
        menuVBox.setTranslateX(-screenWidth / 12);  // Smaller left shift (move right from previous)
        menuVBox.setTranslateY(-screenHeight / 8);  // Move up by 12.5% of screen height
        menuVBox.getChildren().addAll(titleImageView, playButton, scoreboardButton, helpButton);

        container.getChildren().add(menuVBox);
        
        // Main Layer: Background + Menu
        StackPane mainLayer = new StackPane(bgView, container);

        Scene scene = new Scene(mainLayer, screenWidth, screenHeight);

        // ESC handler to exit
        scene.setOnKeyPressed(e -> {
            if ("ESCAPE".equals(e.getCode().toString())) {
                stage.close();
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
