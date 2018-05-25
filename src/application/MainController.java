package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController {

	@FXML
	private Button btnAgenda;

	@FXML
	void btnAgenda(ActionEvent event) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/application/AgendaController.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) btnAgenda.getScene().getWindow();
		Util.openGUI(scene, stage, Strings.TITLE_MAIN);
	}


	/*public void initialize(Stage primaryStage) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("Main.fxml"));
			primaryStage.setTitle("Perruqueria");
			primaryStage.setScene(new Scene(parent));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	public void initialize() {

	}

}