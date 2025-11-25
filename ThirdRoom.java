package javaProject;
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
import javafx.scene.image.ImageView;

public class ThirdRoom extends Application {
	 private Button numSelected=null;
	 private int score=0;
	 private int error=0;
    public void start(Stage firstPage) {
    	firstPage.setTitle("Sudoku");
    	firstPage.setFullScreenExitHint("");
    	firstPage.setFullScreen(true);

    	Pane background=new Pane();
    	background.setStyle(
    		    "-fx-background-image: url('/images/thirdroom.png'); " +
    		    "-fx-background-size: cover;"
    		);
    	
        ImageView cat = new ImageView("/images/walkingright2.png");
    	cat.setFitWidth(300);
    	cat.setFitHeight(500);
    	cat.setLayoutX(250);   
    	cat.setLayoutY(300);
    	background.getChildren().add(cat);
    	
    
    	Pane pane=new Pane();
    	
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
    	
    	b.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent e)->{
    	  
    
    	   Pane pane2=new Pane();
    	   
    	   Label errorText=new Label("Error: 0");
    	   errorText.setFont(Font.font("Arial",FontWeight.BOLD,20));
    	   errorText.setTextFill(Color.WHITE);
    	   errorText.setLayoutX(660);
    	   errorText.setLayoutY(20);
    	   pane2.getChildren().add(errorText);
    	   
    	   
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
    						  if(cell.getText() != "  ") {
    							  return;
    						  }
    						  if(numSelectedText.equals(solutionText)) {
    	    					cell.setText(numSelectedText);
    	    					numSelected=null;
    	    					score++;
    	    					
    	    				}else {
    	    					numSelected=null;
    	    					error++;
    	    					errorText.setText("Error= "+String.valueOf(error));
    	    				}
    						  }
    					  if(score==46) {
    						  Pane pane3=new Pane();
   						   
   						   Label Hint3=new Label("\t      Great goob\n Hint3: ");
   						   Hint3.setFont(Font.font("Centry gothic",FontWeight.BOLD,30));
   						   Hint3.setTextFill(Color.WHITE);
   						   Hint3.setLayoutX(500);
   						   Hint3.setLayoutY(200);
   						   pane3.getChildren().add(Hint3);
   						   
   						   
   						   Button Home=new Button("Home");
   						   Home.setLayoutX(670);
   					       Home.setLayoutY(460);
   					       Home.setFont(new Font("Arail",15));
   						   pane3.getChildren().add(Home);
   						   
   						   StackPane root3 = new StackPane();
   				           root3.getChildren().add(background);
   				           root3.getChildren().add(pane3);
   				           
   				           Scene scene3 = new Scene(root3, 800, 650);
   				           firstPage.setScene(scene3);
   				           firstPage.show();
   				           firstPage.setFullScreenExitHint("");
   				           firstPage.setFullScreen(true);
    			    		  }
    					  
    				  });
    				 
    		   }
        	      
        	   }
    	   
          
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
        
        	firstPage.setScene(scene2);
        	firstPage.setFullScreenExitHint("");
        	firstPage.setFullScreen(true);
         	firstPage.show();
       	
    	});
    	
    	
    	StackPane root = new StackPane();
        root.getChildren().add(background);
        root.getChildren().add(pane);

        Scene scene = new Scene(root, 800, 650);
    	
    	firstPage.setScene(scene);
    	firstPage.show();
    	
    
    	
    }

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
    launch(args);
   
	}

}

