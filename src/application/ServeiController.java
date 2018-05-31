package application;

import java.sql.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ServeiController {
	@FXML
	private Button btnBack,btnGuardar;

	@FXML
	private TextField tfServei, tfPreu;
	
	@FXML
	private Label lInfo;

	/**
	 * Registrar a un servei a partir dels camps
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void btnGuardar(ActionEvent event) throws Exception {
		
		if (tfPreu.getText().equals("") || tfServei.getText().equals("")) {
			Util.timedLabel("No pot haver-hi camps buits", lInfo, 2000);
		} else {
			String sql = " insert into servei (nom,preu) values (?,?) ";
			PreparedStatement st = Main.getConnection().prepareStatement(sql);
			
			
			st.setString(1, tfServei.getText());
			st.setDouble(2, Double.parseDouble(tfPreu.getText()));
			st.execute();

			Pane root = FXMLLoader.load(getClass().getResource("/application/MainController.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage) tfServei.getScene().getWindow();
			Util.openGUI(scene, stage, Strings.TITLE_MAIN);

		}
	}

	/**
	 * Per tornar al men� principal
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void btnBack(ActionEvent event) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/application/MainController.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) btnBack.getScene().getWindow();
		Util.openGUI(scene, stage, Strings.TITLE_MAIN);
	}
}
