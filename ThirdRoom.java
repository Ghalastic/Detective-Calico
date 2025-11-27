package javaProject;
import java.util.Timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.ImageView;

public class ThirdRoom extends Application {
	 private Button numSelected=null;
	 private int score=0;
	 private int error=0;
	 private int seconds = 0;
	 private Timeline timeline;
	 final String[] puzzle = {
  			"--74916-5",
  	        "2---6-3-9",
  	        "-----7-1-",
  	        "-586----4",
  	        "--3----9-",
  	        "--62--187",
  	        "9-4-7---2",
  	        "67-83----",
  	        "81--45---"
 		    };
 	final String[] solution = {
 		        "387491625",
 		        "241568379",
 		        "569327418",
 		        "758619234",
 		        "123784596",
 		        "496253187",
 		        "934176852",
 		        "675832941",
 		        "812945763"
 		    };
  	
 	
 	
    public void start(Stage stage) {
    	stage.setTitle("Sudoku");
    	stage.setFullScreenExitHint("");
    	stage.setFullScreen(true);
    	setPage1(stage);
    	 	   
    }
    	
public void setPage3(Stage winPage) {
	          Pane background2=new Pane();
         	  background2.setStyle(
  		    "-fx-background-image: url('/images/nextRoom.png'); " +
  		    "-fx-background-size: cover;"
  	  	     );
		       Pane pane3=new Pane();
		       pane3.getChildren().add(Controllers.createControlButtons());
			   
			   Label Hint3=new Label("\t\t\t        Great goob\n\n  Hint3:The thief removed the map carefully,\n     suggesting someone who has handled\n       fragile historical documents before.\n\t\t\t          Errors= "+error);
			   Hint3.setFont(Font.font("Centry gothic",FontWeight.BOLD,30));
			   Hint3.setTextFill(Color.WHITE);
			   Hint3.setLayoutX(350);
			   Hint3.setLayoutY(200);
			   pane3.getChildren().add(Hint3);
			   
			   
			   Button next=new Button("Next Room");
			   next.setLayoutX(950);
			   next.setLayoutY(450);
			   next.setFont(new Font("Arail",15));
			   pane3.getChildren().add(next);
			   
			   
			   
			   StackPane root3 = new StackPane();
	           root3.getChildren().add(background2);
	           root3.getChildren().add(pane3);
	           
	           Scene scene3 = new Scene(root3, 800, 650);
	           winPage.setScene(scene3);
	           winPage.show();
	           winPage.setFullScreenExitHint("");
	           winPage.setFullScreen(true);
  		  }
		  
	

public void setPage2(Stage mainGame) {
	Pane background = new Pane();
	background.setStyle(
	    "-fx-background-image: url('/images/thirdroom.png'); " +
	    "-fx-background-size: cover;"
	);
	   Pane pane2=new Pane();
	   pane2.getChildren().add(Controllers.createControlButtons());
	   
	   Label dataText=new Label("Errors:  "+String.valueOf(error)+"  Score:  "+String.valueOf(score)+"  Time: 0s ");
	   timer(dataText);
	   dataText.setFont(Font.font("Arial",FontWeight.BOLD,20));
	   dataText.setTextFill(Color.WHITE);
	   dataText.setLayoutX(560);
	   dataText.setLayoutY(20);
	   pane2.getChildren().add(dataText);
	   
	   
	   GridPane grid=new GridPane();
	   for(int r=0;r<9;r++) {
		   for(int c=0;c<9;c++) {
			   char tileChar=puzzle[r].charAt(c);
			   Button cell=new Button(String.valueOf(tileChar));
			   
			   if(tileChar !='-') {
					  cell.setFont(Font.font("Arail",FontWeight.BOLD,25));
					  cell.setText(String.valueOf(tileChar));
					  
				  }else {
					  cell.setFont(Font.font("Arail",25));
					  cell.setText("  ");
					  
				  }
			   
			   grid.add(cell,c,r);
			   
				  if ((r==2 && c==2) || (r==2 &&c==5) || (r==5&&c==2) || (r==5 &&c==5)) {
					  cell.setStyle("-fx-Border-color:black; -fx-Border-width:1 5 5 1");
				  }
				  else if(r==2 || r==5) {
					 
					  cell.setStyle("-fx-Border-color:black; -fx-Border-width:1 1 5 1");
				  }
				  else if(c==2 || c==5) {
					 
					  cell.setStyle("-fx-Border-color:black; -fx-Border-width:1 5 1 1");
					  
				  }else {
					  cell.setStyle("-fx-Border-color:black; -fx-Border-width:1 1 1 1");
				  }
				  final int row = r;
				  final int col = c;
				  cell.addEventHandler(MouseEvent.MOUSE_PRESSED,(MouseEvent e2)->{
					  String numSelectedText=numSelected.getText();
					  String solutionText=String.valueOf(solution[row].charAt(col));
					  if(numSelectedText != null) {
						  if(!cell.getText().equals("  ")) {
							  return;
						  }
						  if(numSelectedText.equals(solutionText)) {
	    					cell.setText(numSelectedText);
	    					numSelected=null;
	    					score++;
	    					dataText.setText("Errors:  "+String.valueOf(error)+"  Score:  "+String.valueOf(score)+"  Time: 0s ");
	    					
	    				}else {
	    					numSelected=null;
	    					error++;
	    					dataText.setText("Errors:  "+String.valueOf(error)+"  Score:  "+String.valueOf(score)+"  Time: 0s ");
	    				}
						  }
					  if(score==5) {
						  setPage3(mainGame);
						  timeline.stop();
					  }	});
 				 
 		   } }
 
 	   grid.setVgap(0.5);
 	   grid.setHgap(0.5);
 	   grid.setLayoutX(460);
		   grid.setLayoutY(60);
 	   pane2.getChildren().add(grid);
 	   
 	   
 	   GridPane grid2=new GridPane();
 	   for(int c=1;c<10;c++) {
 		   Button num=new Button(String.valueOf(c));
 		   num.setFont(Font.font("Arail",FontWeight.BOLD,25));
 		   grid2.add(num,c,1);
 		   num.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e3)->{
 			   numSelected=num;
 		   });
 	   }
 	 
 		
 	    grid2.setHgap(5);
 	    grid2.setLayoutX(450);
		grid2.setLayoutY(580);
 	    pane2.getChildren().add(grid2);
 	  
 	   
 	     StackPane root2 = new StackPane();
         root2.getChildren().add(background);
         root2.getChildren().add(pane2);

         Scene scene2 = new Scene(root2, 800, 650);
     
         mainGame.setScene(scene2);
         mainGame.setFullScreenExitHint("");
         mainGame.setFullScreen(true);
         mainGame.show();	
}
public void setPage1(Stage firstPage) {

	Pane background=new Pane();
	background.setStyle(
		    "-fx-background-image: url('/images/thirdroom.png'); " +
		    "-fx-background-size: cover;"
		);
	
    ImageView cat = new ImageView("/images/walkingright2.png");
	cat.setFitWidth(250);
	cat.setFitHeight(400);
	cat.setLayoutX(250);   
	cat.setLayoutY(300);
	background.getChildren().add(cat);
	

	Pane pane=new Pane();
	pane.getChildren().add(Controllers.createControlButtons());
	
	Label title=new Label("Welcome to the third room");
	title.setLayoutX(500);
	title.setLayoutY(80);
	title.setFont(Font.font("Centry gothic",FontWeight.BOLD,30));
	title.setTextFill(Color.WHITE);
	pane.getChildren().add(title);
	
	Label rule=new Label("In this room you have to solve Sudoku.You have a 9×9 grid and \n a set of numbers 1 to 9 at the bottom."
			     		  +"To play, pick a number\n(1–9) from the bottom, then click on any empty cell in the grid\n     to place it."+
			      		  " Remember the rule: a number must not repeat\n       in the same row, the same colum, or the same 3×3 box.");
	rule.setLayoutX(400); 
	rule.setLayoutY(250);
	rule.setFont(new Font("Centry gothic",20));
	rule.setTextFill(Color.WHITE);
	pane.getChildren().add(rule);
	
	
	Button b=new Button("Start");
	b.setLayoutX(670);
	b.setLayoutY(460);
	b.setFont(new Font("Arail",15));
	pane.getChildren().add(b);
	b.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e)->{
		 setPage2(firstPage);
		 });
	 
	StackPane root = new StackPane();
    root.getChildren().add(background);
    root.getChildren().add(pane);

    Scene scene = new Scene(root, 800, 650);
	
	firstPage.setScene(scene);
	firstPage.show();
	
}
public void timer(Label dataText) {
	timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
        seconds++;
        dataText.setText("Errors:  " + error + "  Score:  " + score + "  Time: " + seconds + "s");
    }));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
}
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
    launch(args);
   
	}

}
