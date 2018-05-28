package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController {

	@FXML
	private Button btnAgenda, btnClient, btnTreballador;

	/**
	 * Menu Agenda
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void btnAgenda(ActionEvent event) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/application/AgendaController.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) btnAgenda.getScene().getWindow();
		Util.openGUI(scene, stage, Strings.TITLE_MAIN);
	}
	
	/**
	 * Menu Client
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void btnClient(ActionEvent event) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/application/ClientController.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) btnAgenda.getScene().getWindow();
		Util.openGUI(scene, stage, Strings.TITLE_MAIN);
	}
	
	/**
	 * Pantalla per afegir un treballador
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void btnTreballador(ActionEvent event) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/application/TreballadorController.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) btnAgenda.getScene().getWindow();
		Util.openGUI(scene, stage, Strings.TITLE_MAIN);
	}
	
	public void initialize() {

	}

}