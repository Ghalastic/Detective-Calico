package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Scoreboard extends Application {

    static class PlayerResult {
        String name;
        double mean;
        String tier;
        int attempt;

        PlayerResult(String name, double mean, String tier, int attempt) {
            this.name = name;
            this.mean = mean;
            this.tier = tier;
            this.attempt = attempt;
        }
    }

   // tires based on the number of errors
    static String getTier(double mean) {
        if (mean >= 20) return "D";
        else if (mean >= 15) return "C";
        else if (mean >= 10) return "B";
        else if (mean >= 5) return "A";
        else return "S";
    }

    private List<PlayerResult> loadResults() throws IOException {
        List<String> names = new ArrayList<>();
        List<List<Integer>> scores = new ArrayList<>();

        // players file: one name per line
        BufferedReader nameReader = new BufferedReader(new FileReader("players.txt"));
        String line;
        while ((line = nameReader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                names.add(line);
            }
        }
        nameReader.close();

        // scores file: e.g. "2,10,3" per line
        BufferedReader scoreReader = new BufferedReader(new FileReader("scores.txt"));
        while ((line = scoreReader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(",");
            List<Integer> scoreSet = new ArrayList<>();
            for (String p : parts) {
                scoreSet.add(Integer.parseInt(p.trim()));
            }
            scores.add(scoreSet);
        }
        scoreReader.close();

        // build list of results
        List<PlayerResult> results = new ArrayList<>();
        int attempt = 1;
        int n = Math.min(names.size(), scores.size());
        for (int i = 0; i < n; i++) {
            List<Integer> scoreSet = scores.get(i);
            double sum = 0;
            for (int s : scoreSet) sum += s;
            double mean = scoreSet.isEmpty() ? 0 : sum / scoreSet.size();
            String tier = getTier(mean);
            results.add(new PlayerResult(names.get(i), mean, tier, attempt++));
        }
        return results;
    }

    @Override
    public void start(Stage stage) throws IOException {

        Image bg = new Image("file:background/ScoreBoard.png");
        ImageView background = new ImageView(bg);
        background.setFitHeight(720);
        background.setFitWidth(1300);
        

        Button back = new Button("←");
        back.setLayoutX(15);
        back.setLayoutY(10);
        back.setFont(Font.font("Arial", 15));
        back.setOnAction(e -> Controllers.goToMenu(stage));

        Pane pane = new Pane(background, back);

        // load data from files and add labels
        List<PlayerResult> results = loadResults();

        int i = 0;
        for (PlayerResult r : results) {
            String text = String.format("%-18d  %-23s  %s",
                    r.attempt, r.name, r.tier);
            Label row = new Label(text);
            row.setFont(Font.font("Consolas",FontWeight.BOLD, 30));
            row.setLayoutX(300);
            row.setLayoutY(150 + i * 50); // vertical spacing
            pane.getChildren().add(row);
            i++;
        }

        Scene scene = new Scene(pane);
        stage.setTitle("ScoreBoard");
        stage.setFullScreen(true); 
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
