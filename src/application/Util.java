package application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Util {
	//static final int[] RESOLUCIO = { 1080, 720 };

	public static void openGUI(Scene scene, Stage stage, String title) {
		Main.scene = scene;
       // stage.setMaximized(true);

		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
	}

	public static void openGUI(Scene scene, Stage stage, StageStyle style, String title) {
		Main.scene = scene;
		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle(title);
		stage.initStyle(style);
        //stage.setMaximized(true);
		stage.show();

	}
}


/*package application;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FlotaMain extends Application {

	@Override
	public void start(Stage stage) {
		initUI(stage);
	}
	
	static Scanner sc = new Scanner(System.in);
	static ArrayList<Barco> flota = new ArrayList<Barco>();

	static final int SIZE = 300;
	static final int DIMENSIONS = 10;
	static final int DIVIDER = SIZE/DIMENSIONS;
	
	private void initUI(Stage stage) {
		carregarBarcos();
		sc.nextLine(); //Linea separadora del taulell i jugades
				
		Pane root = new Pane();
		Canvas canvas = new Canvas(SIZE,SIZE);
		canvas.setOnMouseClicked(event -> {
	        int x = (int) event.getX();
	        int y = (int) event.getY();
	        System.out.println(x/DIVIDER+1+","+(y/DIVIDER+1));
	        
	    });
		GraphicsContext gc = canvas.getGraphicsContext2D();
		dibuixarTaulell(gc);
		
		dibuixarBarcos(gc);

		root.getChildren().add(canvas);
		Scene scene = new Scene(root, SIZE,SIZE, Color.WHITESMOKE);
		stage.setTitle("Flota");
		stage.setScene(scene);
		stage.show();
	}

	private void dibuixarTaulell(GraphicsContext gc) {
		for (int x = DIVIDER; x <= SIZE; x+=DIVIDER) {
			gc.setFill(Color.rgb(0,0,0));
			gc.fillRect(x, 1, 1, SIZE);
		}
		for (int y = DIVIDER; y <= SIZE; y+=DIVIDER) {
			gc.setFill(Color.rgb(0,0,0));
			gc.fillRect(1, y, SIZE, 1);
		}
		//System.exit(0);		
	}
	
	void dibuixarBarcos(GraphicsContext gc){
		for(Barco b : flota) {//Per cada barco
			for(Segment s : b.segments) { //Per a cada segment del barco
				int[] coords = getCentreCoords(s.coordX, s.coordY);
				dibuixarBarco(gc, coords);
				
			}
		}
	}
	
	private void dibuixarBarco(GraphicsContext gc, int[] coords) {
		int x = coords[0], y = coords[1];
		gc.beginPath();
		gc.moveTo(x,y);
		gc.lineTo(x-DIVIDER/3,y-DIVIDER/3);
		gc.lineTo(x+DIVIDER/3,y+DIVIDER/3);
		gc.moveTo(x,y);
		gc.lineTo(x+DIVIDER/3,y-DIVIDER/3);
		gc.lineTo(x-DIVIDER/3,y+DIVIDER/3);
		gc.stroke();
		//gc.setFill(Color.rgb(0,0,0));
		//gc.fillRect(coords[0], coords[1], 1, 1);
	}

	int[] getCentreCoords(int x, int y) {
		x--;
		y--;
		int[] coords = new int[2];
		coords[0] = DIVIDER * x + DIVIDER / 2;
		coords[1] = DIVIDER * y + DIVIDER / 2;
		return coords;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private static void carregarBarcos() {
		int counter = 0;
		while (counter <= 5) {
			String linia = sc.nextLine();
			ArrayList<Segment> segmentsBarco = new ArrayList<Segment>();

			String[] coords = linia.split(",");
			for (int i = 0; i < coords.length; i += 2) {
				segmentsBarco.add(new Segment(counter, Integer.parseInt(coords[i]), Integer.parseInt(coords[i + 1])));
			}
			flota.add(new Barco(segmentsBarco));

			counter++;
		}

	}

}*/
