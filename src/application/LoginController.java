package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private Button btnAccedir;

	@FXML
	private Label lOutput;

	@FXML
	private TextField tfUsuari, tfContrassenya;

	public void initialize() {
		tfContrassenya.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				btnAccedir.fire();
			}
		});
	}

	@FXML
	void btnAccedir(ActionEvent event) throws Exception {
		if (validarLogin(tfUsuari.getText(), tfContrassenya.getText())) {
			Pane root = FXMLLoader.load(getClass().getResource("/application/MainController.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage) btnAccedir.getScene().getWindow();
			Util.openGUI(scene, stage, Strings.TITLE_MAIN);
		}
	}

	private boolean validarLogin(String nick, String pass) {
		try {
			String consulta = " select password from treballador where nick = ? ";
			PreparedStatement st = Main.getConnection().prepareStatement(consulta);
			st.setString(1, nick);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				if (pass.equals(rs.getString("password"))) {
					return true;
				} else {
					Util.timedLabel("Password incorrecta", lOutput, 2000);
				}
			} else {
				Util.timedLabel("Usuari no existeix", lOutput, 2000);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@FXML
	void cmdAbout(ActionEvent event) throws Exception {
		System.out.println("Showing About");
		new AboutController().initialize(new Stage());
	}

}