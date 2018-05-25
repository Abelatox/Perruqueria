package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AgendaController {

	@FXML
	private Canvas canvas;
	
	GraphicsContext gc;

	static final int SIZEX = 1000;
	static final int SIZEY = 400;
	
	static final int OFFSETX = 100;
	static final int OFFSETY = 50;
	
	static final int ROWS = 8;
	static final int COLUMNS = 10;
	
	static final int DIVIDERX = SIZEX / COLUMNS;
	static final int DIVIDERY = SIZEY / ROWS;
	
	//Pixels per character
	static final int PPC = 5;
	
	public void initialize() {
		//canvas.setWidth(SIZEX+1);
		//canvas.setHeight(SIZEY);
		gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.rgb(0, 0, 0));

		// Verticals
		for (int x = OFFSETX; x <= SIZEX+OFFSETX; x += DIVIDERX) {
			gc.fillRect(x, OFFSETY, 1, SIZEY);
		}

		// Horitzontals
		for (int y = OFFSETY; y <= SIZEY+OFFSETY; y += DIVIDERY) {
			gc.fillRect(OFFSETX, y, SIZEX, 1);
		}

		canvas.setOnMouseClicked(event -> {
			int x = (int) event.getX();
			int y = (int) event.getY();
			System.out.println(x / DIVIDERX + "," + (y / DIVIDERY));

			Pane root;
			try {
				root = FXMLLoader.load(getClass().getResource("/application/AgendaController.fxml"));
				Scene scene = new Scene(root);
				Stage stage = (Stage) canvas.getScene().getWindow();
				Util.openGUI(scene, stage, Strings.TITLE_MAIN);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		prepararTaula();
		
	}

	private void prepararTaula() {
		for(int i=0;i<COLUMNS;i++) {
			escriureACasella(i, 0, "Treballador");
		}
		
	}

	public void escriureACasella(int x, int y, String text) {
		float XCENTER = OFFSETX + (x * DIVIDERX + DIVIDERX / 2);
		float YCENTER = OFFSETY + (y * DIVIDERY + DIVIDERY / 3 * 1.7F);
		gc.fillText(text, XCENTER - text.length() * PPC / 2, YCENTER);
	}

}