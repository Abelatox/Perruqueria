package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.objectes.Servei;
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

		ArrayList<Servei> listServeis = new ArrayList<Servei>();

		String consulta = " select id,nom,preu from servei ";
		PreparedStatement st;
		try {
			st = Main.getConnection().prepareStatement(consulta);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				listServeis.add(new Servei(rs.getInt(1), rs.getString(2), rs.getDouble(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (tfPreu.getText().equals("") || tfServei.getText().equals("")) {
			Util.timedLabel("No pot haver-hi camps buits", lInfo, 2000);
		} else {
			String sql = " insert into servei (id,nom,preu) values (?,?,?) ";
			st = Main.getConnection().prepareStatement(sql);

			st.setInt(1, listServeis.size()+1);
			st.setString(2, tfServei.getText());
			st.setDouble(3, Double.parseDouble(tfPreu.getText()));
			st.execute();

			Pane root = FXMLLoader.load(getClass().getResource("/application/MainController.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage) tfServei.getScene().getWindow();
			Util.openGUI(scene, stage, Strings.TITLE_MAIN);

		}
	}

	/**
	 * Per tornar al menu principal
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
