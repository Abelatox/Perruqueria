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

public class ClientController {

	@FXML
	private Button btnRegistre, btnSortir;

	@FXML
	private TextField tfNom, tfMovil, tfCorreu;

	@FXML
	private Label lNom, lSexe, lMovil, lCorreu, lblInfo;

	@FXML
	private ToggleGroup gender;

	@FXML
	private RadioButton rbHome, rbDona;

	ToggleGroup group = new ToggleGroup();

	/**
	 * Registrar a un treballador a partir dels camps
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void btnRegistre(ActionEvent event) throws Exception {
		String c;
		if (tfNom.getText().equals("") || tfMovil.getText().equals("") || tfCorreu.getText().equals("")
				|| (rbDona.isDisable() || rbHome.isDisable())) {
			Util.timedLabel("No pot haver-hi camps buits", lblInfo, 2000);
		} else {
			String sql = " insert into client (name,sexe,telefon,correu) values (?,?,?,?) ";
			PreparedStatement st = Main.getConnection().prepareStatement(sql);
			c = rbHome.isSelected() ? "H" : "D";

			st.setString(1, tfNom.getText());
			st.setString(2, c);
			st.setString(3, lMovil.getText());
			st.setString(4, tfCorreu.getText());
			st.execute();

			Pane root = FXMLLoader.load(getClass().getResource("/application/MainController.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage) tfNom.getScene().getWindow();
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
	void btnSortir(ActionEvent event) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/application/MainController.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) btnSortir.getScene().getWindow();
		Util.openGUI(scene, stage, Strings.TITLE_MAIN);
	}
}
