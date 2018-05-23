package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
	static Scene scene;

	@Override
	public void start(Stage primaryStage) {
		try {
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Pane root = FXMLLoader.load(getClass().getResource("/application/LoginController.fxml"));
			Scene scene = new Scene(root);
			Util.openGUI(scene, primaryStage, Strings.TITLE_MAIN);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static Scene getMainScene() {
		return scene;
	}
}
