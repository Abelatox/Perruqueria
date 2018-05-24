package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private Button btnAccedir;
	
	@FXML
	private TextField tfUsuari, tfContrassenya;

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
			Statement st = Main.getConnection().createStatement();
			ResultSet rs = st.executeQuery(" select password from treballador where nick = '"+nick+"' ");
			if(rs.next()) {
				System.out.println("usuari trobat, comprovant password");
				if(pass.equals(rs.getString("password"))){
					System.out.println("Password correcta, benvingut");
					return true;
				} else {
					System.out.println("Password incorrecta");
				}

			} else {
				System.out.println("Usuari no existeix");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * @FXML void cmdAdmin(ActionEvent event) throws Exception { Pane root =
	 * FXMLLoader.load(getClass().getResource("/application/admin/MainAdmin.fxml"));
	 * Scene scene = new Scene(root); Stage stage = (Stage)
	 * btnAccedir.getScene().getWindow(); Util.openGUI(scene, stage,
	 * Strings.TITLE_MAIN); }
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