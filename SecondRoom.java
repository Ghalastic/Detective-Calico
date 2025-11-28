package DetectiveCalico;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;

public class SecondRoom extends Application {
    BorderPane root;
    Label errorLabel;
    Button restartButton;
    Label scoreLabel;
    Label timerLabel;
    ImageView catView;
    Image catFront;
    Image catBack;
    int errorCount = 0;
    int score = 0;
    int timeElapsed = 0;
    Timeline timer;

    class Card {
        String name;
        Image image;
        
        Card(String name, Image image) {
            this.name = name;
            this.image = image;
        }
        
        public String toString() {
            return name;
        }
    }
    
    String[] cardList = {
        "toucan",
        "cheetah",
        "horse",
        "monkey",
        "tiger",
        "gorilla",
        "parrot",
        "frog",
        "snake",
        "elephant"
    };
    
    int rows = 5;
    int columns = 5;
    
    int cardWidth = 90;
    int cardHeight = 128;
    
    ArrayList<Card> cardSet;
    Image cardBack;
    
    ArrayList<Button> board = new ArrayList<>();
    
    Button card1Selected = null;
    Button card2Selected = null;
    
    boolean gameReady = false;
    
    // New fields for next room screen
    private boolean gameCompleted = false;
    
    
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        stage.setTitle("Second Room: Match the Cards");
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);

        // Get screen dimensions for full-screen background
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        // Background ImageView
        Image bgImage = new Image(new FileInputStream("img/thirdroom.jpg"));
        ImageView bgView = new ImageView(bgImage);
        bgView.setPreserveRatio(false);
        bgView.setFitWidth(screenWidth);
        bgView.setFitHeight(screenHeight);
        
        // --- CAT IMAGES ---
        catFront = new Image(new FileInputStream("img/walkingright1.png"));
        catBack  = new Image(new FileInputStream("img/walkingback1.png"));

        catView = new ImageView(catFront);
        catView.setFitWidth(350);
        catView.setPreserveRatio(true);

        // Put cat at bottom-left
        StackPane.setAlignment(catView, Pos.BOTTOM_LEFT);
        StackPane.setMargin(catView, new Insets(100, 0, 100, 300)); 

        // --- Introduction Text ---
        VBox introVBox = new VBox(30);
        introVBox.setAlignment(Pos.CENTER);
        Label title = new Label("Welcome To the Second Room");
        title.setStyle("-fx-font-size: 38px; -fx-font-weight: bold; -fx-text-fill: white; -fx-text-alignment: center;");
        Label description = new Label(
                "In this room, you must solve a card-matching puzzle. You will see a grid of facedown cards, each hiding an animal.\n" +
                "To play: click any two cards to flip them over. If they match, they stay revealed. If not, they flip back over, and you must remember their positions.\n" +
                "Your score increases each time you correctly match a pair. The error count goes up whenever you flip two cards that do not match.\n" +
                "Try to find all matching pairs with the fewest errors possible."
        );
        description.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-text-alignment: center;");
        description.setWrapText(true);
        Label escLabel = new Label("Press ESC to exit the game view");
        escLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: rgba(0,0,0,0.6); -fx-padding: 10; -fx-background-radius: 20;");
        introVBox.getChildren().addAll(title, description, escLabel);

        // Splash layer: background + introduction text
        StackPane splashLayer = new StackPane(bgView, introVBox);

        errorLabel = new Label("Errors: 0");
        scoreLabel = new Label("Score: 0");
        timerLabel = new Label("Time: 0s");

        setupCards();
        shuffleCards();

        root = new BorderPane();
        root.setStyle("-fx-background-color: transparent;");

        // --- Top label ---
        errorLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: rgba(0,0,0,0.6); -fx-padding: 5; -fx-background-radius: 10;");
        scoreLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: rgba(0,0,0,0.6); -fx-padding: 5; -fx-background-radius: 10;");
        timerLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-background-color: rgba(0,0,0,0.6); -fx-padding: 5; -fx-background-radius: 10;");

        // Updated: Control buttons on left, labels perfectly centered in middle
        HBox topPane = new HBox();
        topPane.setPrefHeight(40);
        topPane.setStyle("-fx-background-color: rgba(0,0,0,1); -fx-padding: 10;");
        topPane.getChildren().add(Controllers.createControlButtons());
        
        Region leftSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        
        HBox labelsBox = new HBox(40, errorLabel, scoreLabel, timerLabel);
        labelsBox.setAlignment(Pos.CENTER);

        // move the whole group a bit left
        HBox.setMargin(labelsBox, new Insets(0, 0, 0, -180)); // tweak -120 to taste

        
        Region rightSpacer = new Region();
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);
        
        topPane.getChildren().addAll(leftSpacer, labelsBox, rightSpacer);
        root.setTop(topPane);

        // --- Board setup ---
        GridPane boardPane = new GridPane();
        boardPane.setAlignment(Pos.CENTER);

        for (int i = 0; i < cardSet.size(); i++) {
            Button tile = new Button();
            tile.setPrefSize(cardWidth, cardHeight);

            ImageView imgView = new ImageView(cardSet.get(i).image);
            imgView.setFitWidth(cardWidth);
            imgView.setFitHeight(cardHeight);
            tile.setGraphic(imgView);

            int index = i;

            tile.setOnAction(e -> {
                if (!gameReady || gameCompleted) return;

                if (((ImageView) tile.getGraphic()).getImage() == cardBack) {
                    // First card selected
                    if (card1Selected == null) {
                        card1Selected = tile;
                        showCard(tile, index);
                    }
                    // Second card selected
                    else if (card2Selected == null && tile != card1Selected) {
                        card2Selected = tile;
                        showCard(tile, index);

                        Image img1 = ((ImageView) card1Selected.getGraphic()).getImage();
                        Image img2 = ((ImageView) card2Selected.getGraphic()).getImage();

                        if (img1 != img2) {
                            errorCount++;
                            errorLabel.setText("Errors: " + errorCount);
                            hideTwoCards();
                        } else {
                            score++;
                            scoreLabel.setText("Score: " + score);

                            if (score == 10) {
                                stopTimer();
                                showNextRoom(stage); // Call the next room method
                            }
                            
                            card1Selected = null;
                            card2Selected = null;
                        }
                    }
                }
            });

            board.add(tile);
            boardPane.add(tile, i % columns, i / columns);
        }

        root.setCenter(boardPane);

        // Restart button
        Button restartBtn = new Button("Restart Game");
        restartBtn.setOnAction(e -> restartGame());
        restartBtn.setDisable(true);
        restartBtn.setStyle("-fx-background-color: rgba(0,0,0,0.6); -fx-text-fill: white; -fx-padding: 5; -fx-background-radius: 10;");

        HBox bottomPane = new HBox(restartBtn);
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setPrefHeight(50);
        bottomPane.setStyle("-fx-background-color: rgba(0,0,0,1); -fx-padding: 10;");
        root.setBottom(bottomPane);

        restartButton = restartBtn;

        root.setVisible(false); // Hidden at launch

        // === Main Layer: Background, Card Game, Splash Overlay ===
        StackPane mainLayer = new StackPane(bgView, catView, root, splashLayer);

        Scene scene = new Scene(mainLayer, screenWidth, screenHeight);

        // ESC handler works at any time
        scene.setOnKeyPressed(e -> {
            if ("ESCAPE".equals(e.getCode().toString())) {
                stage.close();
            }
        });

        // Show splash for 10 seconds, then start the game
        PauseTransition pause = new PauseTransition(Duration.seconds(6));
        pause.setOnFinished(event -> {
            splashLayer.setVisible(false);
            root.setVisible(true);
            catView.setImage(catBack);
            hideAllCards();
            StackPane.setAlignment(catView, Pos.CENTER_LEFT);
            StackPane.setMargin(catView, new Insets(0, 0, -500, 200));
        });
        pause.play();

        stage.setScene(scene);
        stage.show();
    }
    
 // New method to show the next room screen
    private void showNextRoom(Stage stage) {
        gameCompleted = true;
        try {
            Image nextRoomBg = new Image(new FileInputStream("img/nextRoom.png"));

            double screenWidth  = Screen.getPrimary().getVisualBounds().getWidth();
            double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

            ImageView nextRoomBgView = new ImageView(nextRoomBg);
            nextRoomBgView.setFitWidth(screenWidth);
            nextRoomBgView.setFitHeight(screenHeight);
            nextRoomBgView.setPreserveRatio(false);

            // Updated: Use StackPane for overlay control, position controls inside background
            StackPane rootStack = new StackPane();
            rootStack.getChildren().add(nextRoomBgView);

            // Top controls at top-left, with margin to stay inside background
            HBox topControls = Controllers.createControlButtons();

            // remove any extra padding if you want them very close to the corner
            topControls.setPadding(new Insets(0));   // <-- important
            topControls.setAlignment(Pos.TOP_LEFT);

            // make the StackPane put this HBox in the top-left
            StackPane.setAlignment(topControls, Pos.TOP_LEFT);
            StackPane.setMargin(topControls, new Insets(10, 0, 0, 10)); // distance from edges

            // Ensure buttons work in the outro scene
            Button pauseBtn = (Button) topControls.getChildren().get(0);
            Button menuBtn = (Button) topControls.getChildren().get(1);
            // Temporary: Add background to make buttons visible for testing
            pauseBtn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-padding: 8 15;");
            menuBtn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-padding: 8 15;");
            pauseBtn.setOnAction(e -> {
                System.out.println("Pause button clicked!"); // Debug print
                Alert pauseAlert = new Alert(Alert.AlertType.CONFIRMATION);
                pauseAlert.setTitle("Game Paused");
                pauseAlert.setHeaderText("What would you like to do?");
                ButtonType resume = new ButtonType("Resume");
                ButtonType menu = new ButtonType("Main Menu");
                pauseAlert.getButtonTypes().setAll(resume, menu);
                pauseAlert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == menu) {
                        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                        confirm.setTitle("Leave Game");
                        confirm.setHeaderText("Return to main menu?");
                        confirm.setContentText("Your progress will be lost.");
                        if (confirm.showAndWait().get() == ButtonType.OK) {
                            // Add your menu navigation logic here
                            System.out.println("Going to main menu...");
                        }
                    }
                });
            });
            menuBtn.setOnAction(e -> {
                System.out.println("Menu button clicked!"); // Debug print
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Leave Game");
                confirm.setHeaderText("Return to main menu?");
                confirm.setContentText("Your progress will be lost.");
                if (confirm.showAndWait().get() == ButtonType.OK) {
                    // Add your menu navigation logic here
                    System.out.println("Going to main menu...");
                }
            });

            // Centered VBox with labels
            VBox centerContent = new VBox(20);
            centerContent.setAlignment(Pos.CENTER);
            centerContent.setStyle("-fx-background-color: transparent;");

            Label greatJobLabel = new Label("Great job");
            greatJobLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");

            Label hintLabel = new Label("Hint 2: A faint smell of sea salt was found on the glass case where the map was stored.");
            hintLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
            hintLabel.setWrapText(true);
            hintLabel.setMaxWidth(screenWidth * 0.6); // avoid going off screen

            Label errorsLabel = new Label("Number of Errors: " + errorCount);
            errorsLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

            centerContent.getChildren().addAll(greatJobLabel, hintLabel, errorsLabel);

            // Let StackPane keep this VBox perfectly centered
            rootStack.getChildren().add(centerContent);
            StackPane.setAlignment(centerContent, Pos.CENTER);

            // Pane overlay for the button at a fixed position (original place)
            Pane overlayPane = new Pane();
            overlayPane.setPickOnBounds(false); // allow clicks to pass through empty areas

            Button nextRoomButton = new Button("Next Room");
            nextRoomButton.setStyle(
                    "-fx-font-size: 18px; -fx-padding: 8px 16px; " +
                    "-fx-background-color: white; -fx-text-fill: black;"
            );
            nextRoomButton.setOnAction(e -> {
                System.out.println("Next Room button clicked!"); // Debug print
                stage.close();
            });
            // Temporary: Make button visible for testing (remove after confirming it works)
            nextRoomButton.setStyle(
                    "-fx-font-size: 18px; -fx-padding: 8px 16px; " +
                    "-fx-background-color: white; -fx-text-fill: black;" // Changed to red for visibility
            );
            double doorButtonX = 1060; // adjust as needed
            double doorButtonY = 450;
            nextRoomButton.setLayoutX(doorButtonX);
            nextRoomButton.setLayoutY(doorButtonY);

            overlayPane.getChildren().add(nextRoomButton);

            // Put overlay on top
            rootStack.getChildren().add(overlayPane);

            // Add topControls LAST to ensure it's on top and clickable
            rootStack.getChildren().add(topControls);

            Scene nextRoomScene = new Scene(rootStack, screenWidth, screenHeight);
            nextRoomScene.setOnKeyPressed(e -> {
                if ("ESCAPE".equals(e.getCode().toString())) stage.close();
            });

            stage.setScene(nextRoomScene);

        } catch (FileNotFoundException e) {
            System.out.println("Next room background image not found: img/nextRoom.png");
            e.printStackTrace();
        }
    }

        
    void setupCards() {
        cardSet = new ArrayList<>();
        
        for (String name : cardList) {
            try {
                Image img = new Image(new FileInputStream("img/" + name + ".jpg"));
                cardSet.add(new Card(name, img));
            } 
            catch (FileNotFoundException e) {
                System.out.println("Could not load: img/" + name + ".jpg");
                e.printStackTrace();
            }
        }
        
        // Duplicate cards for matching pairs
        cardSet.addAll(new ArrayList<>(cardSet));
        
        // Load the card back image
        try {
            cardBack = new Image(new FileInputStream("img/back.jpg"));
        } 
        catch (FileNotFoundException e) {
            System.out.println("Could not load: img/back.jpg");
            e.printStackTrace();
        }
    }
    
    
    void shuffleCards() {
        java.util.Collections.shuffle(cardSet);
    }
    
    void showCard(Button tile, int index) {
        ImageView view = new ImageView(cardSet.get(index).image);
        view.setFitWidth(cardWidth);
        view.setFitHeight(cardHeight);
        tile.setGraphic(view);
    }
    
    void hideAllCards() {
    PauseTransition pause = new PauseTransition(Duration.seconds(2));
    pause.setOnFinished(e -> {
        for (Button tile : board) {
            ImageView view = new ImageView(cardBack);
            view.setFitWidth(cardWidth);
            view.setFitHeight(cardHeight);
            tile.setGraphic(view);
        }
        gameReady = true;
        restartButton.setDisable(false);
        startTimer();
    });
    pause.play();
    }
    
    void hideTwoCards() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1.0));
        pause.setOnFinished(e -> {
            ImageView back1 = new ImageView(cardBack);
            back1.setFitWidth(cardWidth);
            back1.setFitHeight(cardHeight);
            
            ImageView back2 = new ImageView(cardBack);
            back2.setFitWidth(cardWidth);
            back2.setFitHeight(cardHeight);
            
            card1Selected.setGraphic(back1);
            card2Selected.setGraphic(back2);
            
            card1Selected = null;
            card2Selected = null;
        });
        pause.play();
    }
    
    void restartGame() {
        if (gameCompleted) return;
        
        gameReady = false;
        card1Selected = null;
        card2Selected = null;
        
        shuffleCards();
        
        for (int i = 0; i < board.size(); i++) {
            ImageView faceView = new ImageView(cardSet.get(i).image);
            faceView.setFitWidth(cardWidth);
            faceView.setFitHeight(cardHeight);
            board.get(i).setGraphic(faceView);
        }
        
        errorCount = 0;
        errorLabel.setText("Errors: 0");
        
        score = 0;
        scoreLabel.setText("Score: 0");
        
        resetTimer();
        
        hideAllCards();
    }
    
    // New method to start the timer
    void startTimer() {
        timeElapsed = 0;
        timerLabel.setText("Time: 0s");
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeElapsed++;
            timerLabel.setText("Time: " + timeElapsed + "s");
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }
    
    // New method to stop the timer
    void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }
    
    // New method to reset the timer
    void resetTimer() {
        stopTimer();
        timeElapsed = 0;
        timerLabel.setText("Time: 0s");
    }
    
    public static void main(String[] args) {
        launch();
    }
}
