package detectiveCalico;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FinalAnswer extends Application {
	
    public void start(Stage stage) {
        stage.setTitle("Detective calico");
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);

        // Get screen size so the scene matches your monitor
        double screenWidth  = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
    	
        Pane background = new Pane();
        background.setStyle(
            // 🔁 changed to project-relative path
            "-fx-background-image: url('file:Img/answer3.png'); " +
            "-fx-background-size: cover; " +
            "-fx-background-position: center center; " +
            "-fx-background-repeat: no-repeat;"
        );
   	    
        Pane pane = new Pane();

        // ✅ Top-right Pause/Menu controls (using Controllers with stage)
        HBox controls = Controllers.createControlButtons(stage);
        controls.setAlignment(Pos.TOP_RIGHT);
        controls.setPadding(new Insets(10));
        controls.setLayoutX(screenWidth - 300); // adjust if needed
        controls.setLayoutY(10);
        pane.getChildren().add(controls);

        Label choose = new Label("Choose the culprit:");
        choose.setFont(Font.font("Centry gothic", FontWeight.BOLD, 50));
        choose.setTextFill(Color.WHITE);
        choose.setLayoutX(500);
        choose.setLayoutY(100);
        pane.getChildren().add(choose);
	   
        HBox row = new HBox(300); 
        row.setLayoutX(250);
        row.setLayoutY(490);
	   
        ToggleGroup group = new ToggleGroup();
        RadioButton sailor = new RadioButton("Sailor");
        sailor.setFont(Font.font("Centry gothic", FontWeight.BOLD, 25));
        sailor.setTextFill(Color.WHITESMOKE);
        sailor.setToggleGroup(group);
	   
        RadioButton historian = new RadioButton("Historian");
        historian.setFont(Font.font("Centry gothic", FontWeight.BOLD, 25));
        historian.setTextFill(Color.WHITESMOKE);
        historian.setToggleGroup(group);
	   
        RadioButton security = new RadioButton("Security");
        security.setFont(Font.font("Centry gothic", FontWeight.BOLD, 25));
        security.setTextFill(Color.WHITESMOKE);
        security.setToggleGroup(group);
	   
        Button submit = new Button("Submit");
        submit.setFont(Font.font("Centry gothic", FontWeight.BOLD, 15));
        submit.setLayoutX(680);
        submit.setLayoutY(600);
       
        Label result = new Label();
        result.setFont(Font.font("Century Gothic", FontWeight.BOLD, 40));
	   
        submit.setOnAction(e -> {
            RadioButton selected = (RadioButton) group.getSelectedToggle();
            if (selected != null) {
                if (selected.getText().equalsIgnoreCase("Historian")) {
                    result.setText("Correct! The culprit is the Historian.");
                    result.setTextFill(Color.GREEN);
                    result.setLayoutX(400);
                    result.setLayoutY(650);
                } else {
                    result.setText("Wrong! Try again.");
                    result.setTextFill(Color.RED);
                    result.setLayoutX(550);
                    result.setLayoutY(650);
                }
            }
        });
	  
        row.getChildren().addAll(sailor, historian, security);
        pane.getChildren().addAll(row, submit, result);

        StackPane root = new StackPane();
        root.getChildren().add(background);
        root.getChildren().add(pane);
     
        // Use full screen size here so the background scales nicely
        Scene scene = new Scene(root, screenWidth, screenHeight);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)  {
        launch(args);
    }
}

