package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TreballadorController {

	@FXML
	private Button btnRegistrar;

	@FXML
	private TextField tfNom, tfContrassenya;

	public void initialize() {
		
	}

	@FXML
	void btnRegistrar(ActionEvent event) throws Exception {
		/*if (validarLogin(tfUsuari.getText(), tfContrassenya.getText())) {
			Pane root = FXMLLoader.load(getClass().getResource("/application/MainController.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage) btnAccedir.getScene().getWindow();
			Util.openGUI(scene, stage, Strings.TITLE_MAIN);
		}*/
	}
	
	@FXML
	void cmdAbout(ActionEvent event) throws Exception {
		System.out.println("Showing About");
		new AboutController().initialize(new Stage());
	}

}