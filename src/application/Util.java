package application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Util {
	static final int[] RESOLUCIO = { 1080, 720 };

	public static void openGUI(Scene scene, Stage stage, String title) {
		Main.scene = scene;
		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
	}

	public static void openGUI(Scene scene, Stage stage, StageStyle style, String title) {
		Main.scene = scene;
		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle(title);
		stage.initStyle(style);
		stage.show();

	}
}
