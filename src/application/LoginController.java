package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private Button btnAccedir;

	@FXML
	void btnAccedir(ActionEvent event) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/application/MainController.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) btnAccedir.getScene().getWindow();
		Util.openGUI(scene, stage, Strings.TITLE_MAIN);
	}
/*
	@FXML
	void cmdAdmin(ActionEvent event) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/application/admin/MainAdmin.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) btnAccedir.getScene().getWindow();
		Util.openGUI(scene, stage, Strings.TITLE_MAIN);
	}
*/
	@FXML
	void cmdAbout(ActionEvent event) throws Exception {
		System.out.println("Showing About");
		new AboutController().initialize(new Stage());
	}

	public void initialize(Stage primaryStage) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("Main.fxml"));
			primaryStage.setTitle("Perruqueria");
			primaryStage.setScene(new Scene(parent));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initialize() {
		
	}

}