package DetectiveCalico;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;

public class MainMenu extends Application {

    private Stage primaryStage;
    private Scene mainMenuScene;  // To store the main menu scene for switching back

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        primaryStage = stage;
        stage.setTitle("Detective Calico - Main Menu");
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);

        // Get screen dimensions for full-screen
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        // Background ImageView (changed to menu.png as per your request)
        Image bgImage = new Image(new FileInputStream("img/menu.jpg"));
        ImageView bgView = new ImageView(bgImage);
        bgView.setPreserveRatio(false);
        bgView.setFitWidth(screenWidth);
        bgView.setFitHeight(screenHeight);

        // Title ImageView
        Image titleImage = new Image(new FileInputStream("img/Detective-Calico.png"));
        ImageView titleImageView = new ImageView(titleImage);
        titleImageView.setFitWidth(700);  // Adjust as needed
        titleImageView.setFitHeight(400); // Adjust as needed
        titleImageView.setPreserveRatio(true);

        // Play Button (with image)
        Image playImage = new Image(new FileInputStream("img/Play.png"));
        ImageView playImageView = new ImageView(playImage);
        playImageView.setFitWidth(100);  // Adjust size as needed
        playImageView.setFitHeight(50);
        playImageView.setPreserveRatio(true);
        Button playButton = new Button();
        playButton.setGraphic(playImageView);
        playButton.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 2; -fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5;");
        playButton.setOnAction(e -> {
            try {
                showNameInputScene();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        // Scoreboard Button (with image)
        Image scoreboardImage = new Image(new FileInputStream("img/Scoreboard.png"));
        ImageView scoreboardImageView = new ImageView(scoreboardImage);
        scoreboardImageView.setFitWidth(250);
        scoreboardImageView.setFitHeight(200);
        scoreboardImageView.setPreserveRatio(true);
        Button scoreboardButton = new Button();
        scoreboardButton.setGraphic(scoreboardImageView);
        scoreboardButton.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 2; -fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5;");
        scoreboardButton.setOnAction(e -> System.out.println("Scoreboard button clicked - Show scores here"));

        // Help Button (with image)
        Image helpImage = new Image(new FileInputStream("img/Help.png"));
        ImageView helpImageView = new ImageView(helpImage);
        helpImageView.setFitWidth(100);
        helpImageView.setFitHeight(50);
        helpImageView.setPreserveRatio(true);
        Button helpButton = new Button();
        helpButton.setGraphic(helpImageView);
        helpButton.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 2; -fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5;");
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
        mainMenuScene = scene;  // Store the main menu scene
        stage.show();
    }

    // New method to show the name input scene
    private void showNameInputScene() throws FileNotFoundException {
        // Get screen dimensions
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        // Background (same as menu)
        Image bgImage = new Image(new FileInputStream("img/menu.jpg"));
        ImageView bgView = new ImageView(bgImage);
        bgView.setPreserveRatio(false);
        bgView.setFitWidth(screenWidth);
        bgView.setFitHeight(screenHeight);

        // Name ImageView
        Image nameImage = new Image(new FileInputStream("img/Name.png"));
        ImageView nameImageView = new ImageView(nameImage);
        nameImageView.setFitWidth(150);  // Adjust size as needed
        nameImageView.setFitHeight(50);
        nameImageView.setPreserveRatio(true);

        // TextField for name input
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.setStyle("-fx-font-size: 18px; -fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5;");
        nameField.setPrefWidth(200);

        // HBox for "Name.png" + TextField
        HBox nameHBox = new HBox(20);  // 20px spacing
        nameHBox.setAlignment(Pos.CENTER);
        nameHBox.getChildren().addAll(nameImageView, nameField);

        // Submit Button (with image)
        Image submitImage = new Image(new FileInputStream("img/Submit.png"));
        ImageView submitImageView = new ImageView(submitImage);
        submitImageView.setFitWidth(100);  // Adjust size as needed
        submitImageView.setFitHeight(50);
        submitImageView.setPreserveRatio(true);
        Button submitButton = new Button();
        submitButton.setGraphic(submitImageView);
        submitButton.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 2; -fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5;");
        submitButton.setOnAction(e -> {
            String playerName = nameField.getText().trim();
            if (!playerName.isEmpty()) {
                System.out.println("Player name: " + playerName);
                // TODO: Launch MatchCards with playerName (e.g., new MatchCards().start(primaryStage, playerName);)
                // For now, just print
            } else {
                System.out.println("Name cannot be empty!");
            }
        });

        // Back Button (with image)
        Image backImage = new Image(new FileInputStream("img/Back.png"));
        ImageView backImageView = new ImageView(backImage);
        backImageView.setFitWidth(100);  // Adjust size as needed
        backImageView.setFitHeight(50);
        backImageView.setPreserveRatio(true);
        Button backButton = new Button();
        backButton.setGraphic(backImageView);
        backButton.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-border-width: 2; -fx-padding: 10; -fx-background-radius: 5; -fx-border-radius: 5;");
        backButton.setOnAction(e -> primaryStage.setScene(mainMenuScene));  // Go back to main menu

        // Handle Enter key in TextField
        nameField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                submitButton.fire();  // Trigger submit
            }
        });

        // VBox for centering the input and buttons
        VBox inputVBox = new VBox(30);
        inputVBox.setAlignment(Pos.CENTER);
        inputVBox.getChildren().addAll(nameHBox, submitButton, backButton);

        // Main Layer for input scene
        StackPane inputLayer = new StackPane(bgView, inputVBox);

        Scene inputScene = new Scene(inputLayer, screenWidth, screenHeight);

        // ESC to go back to main menu
        inputScene.setOnKeyPressed(e -> {
            if ("ESCAPE".equals(e.getCode().toString())) {
                primaryStage.setScene(mainMenuScene);
            }
        });

        primaryStage.setScene(inputScene);
    }

    public static void main(String[] args) {
        launch();
    }
}
