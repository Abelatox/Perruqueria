package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AboutController {
	@FXML
	Button btnCloseAbout;

	@FXML
	Label noms;

	@FXML
	void cmdCloseAbout(ActionEvent event) {
		Stage stage = (Stage) btnCloseAbout.getScene().getWindow();
		stage.close();
	}

	public void initialize(Stage aboutStage) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("About.fxml"));
			aboutStage.setScene(new Scene(parent));
			aboutStage.setTitle("About");
			aboutStage.initModality(Modality.WINDOW_MODAL);
			aboutStage.initOwner(Main.getMainScene().getWindow());
			aboutStage.setResizable(false);
			aboutStage.initStyle(StageStyle.UTILITY);
			aboutStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
