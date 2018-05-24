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
import javafx.stage.Stage;

public class AgendaController {

	@FXML
	private Canvas canvas;

	static final int SIZE = 500;
	static final int DIMENSIONS = 10;
	static final int DIVIDER = SIZE/DIMENSIONS;
	
	@FXML
	void cmdAdmin(ActionEvent event) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/application/admin/MainAdmin.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) canvas.getScene().getWindow();
		Util.openGUI(scene, stage, Strings.TITLE_MAIN);
	}
	
	public void initialize() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.rgb(0,0,0));
		
		for (int x = DIVIDER; x <= SIZE; x+=DIVIDER) {
			gc.fillRect(x, 1, 1, SIZE);
		}
		for (int y = DIVIDER; y <= SIZE; y+=DIVIDER) {
			gc.fillRect(1, y, SIZE, 1);
		}

	}

}