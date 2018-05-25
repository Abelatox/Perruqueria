package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class AgendaController {

	@FXML
	private Canvas canvas;
	
	GraphicsContext gc;

	static final int SIZEX = 500;
	static final int SIZEY = 250;
	
	static final int ROWS = 8;
	static final int COLUMNS = 4;
	
	static final int DIVIDERX = SIZEX / COLUMNS;
	static final int DIVIDERY = SIZEY / ROWS;
	
	//Pixels per character
	static final int PPC = 5;

	public void initialize() {
		canvas.resize(SIZEX, SIZEY);
		gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.rgb(0, 0, 0));

		// Verticals
		for (int x = 0; x <= SIZEX; x += DIVIDERX) {
			gc.fillRect(x, 0, 1, SIZEY-1);
		}

		// Horitzontals
		for (int y = 0; y <= SIZEY; y += DIVIDERY) {
			gc.fillRect(0, y, SIZEX, 1);
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
		for(int i=0;i<ROWS;i++)
		escriureACasella(1, i, "Hola");
		escriureACasella(1, 3, "Que tal");
	}

	public void escriureACasella(int x, int y, String text) {
		int XCENTER = x * DIVIDERX + DIVIDERX / 2;
		int YCENTER = y * DIVIDERY + DIVIDERY / 3 * 2;
		gc.fillText(text, XCENTER - text.length() * PPC / 2, YCENTER);
	}

}