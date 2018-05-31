package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TreballadorController {

	@FXML
	private Button btnRegistrar,btnBack;

	@FXML
	private TextField tfDNI, tfNom, tfNick, pfPassword, tfTelefon, tfCorreu;

	@FXML
	private Label lblInfo;

	/**
	 * Registrar a un treballador a partir dels camps
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void btnRegistrar(ActionEvent event) throws Exception {
		if (tfDNI.getText().equals("") || tfNom.getText().equals("") || tfNick.getText().equals("")
				|| pfPassword.getText().equals("") || tfTelefon.getText().equals("") || tfCorreu.getText().equals("")) {
			Util.timedLabel("No pot haver-hi camps buits", lblInfo, 2000);
		} else {
			if (Util.esInt(tfTelefon.getText())) {
				String sql = " insert into treballador values (?,?,?,?,?,?) ";
				PreparedStatement st = Main.getConnection().prepareStatement(sql);
				st.setString(1, tfDNI.getText());
				st.setString(2, tfNom.getText());
				st.setString(3, pfPassword.getText());
				st.setString(4, tfNick.getText());
				st.setInt(5, Integer.parseInt(tfTelefon.getText()));
				st.setString(6, tfCorreu.getText());
				st.execute();

				Pane root = FXMLLoader.load(getClass().getResource("/application/MainController.fxml"));
				Scene scene = new Scene(root);
				Stage stage = (Stage) tfDNI.getScene().getWindow();
				Util.openGUI(scene, stage, Strings.TITLE_MAIN);
			} else {
				Util.timedLabel("El tel�fon no es un n�mero", lblInfo, 2000);
			}
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