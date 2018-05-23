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

public class MainController {

	@FXML
	private Button btnClient;
	@FXML
	private Button btnAdmin;
	@FXML
	private MenuItem mnClose;
	@FXML
	private MenuItem closeButton;

	@FXML
	void cmdClient(ActionEvent event) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/application/client/MainClient.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) btnClient.getScene().getWindow();
		Util.openGUI(scene, stage, Strings.TITLE_MAIN);
	}

	@FXML
	void cmdAdmin(ActionEvent event) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/application/admin/MainAdmin.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) btnAdmin.getScene().getWindow();
		Util.openGUI(scene, stage, Strings.TITLE_MAIN);
	}

	@FXML
	void cmdClose(ActionEvent event) {
		System.out.println("Closing " + mnClose.getText());
		Platform.exit();
		System.exit(0);
	}

	@FXML
	void cmdAbout(ActionEvent event) throws Exception {
		System.out.println("Showing About");
		new AboutController().initialize(new Stage());
	}

	public void initialize(Stage primaryStage) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("Main.fxml"));
			primaryStage.setTitle("LuxyRestaurant");
			primaryStage.setScene(new Scene(parent));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initialize() {
		
	}

}