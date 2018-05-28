package application;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Util {
	//static final int[] RESOLUCIO = { 1080, 720 };

	public static void openGUI(Scene scene, Stage stage, String title) {
		Main.scene = scene;
       // stage.setMaximized(true);

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
        //stage.setMaximized(true);
		stage.show();

	}
	
	public static void timedLabel(String text, Label label, int ms) {
		label.setText(text);
		timedLabel(label, ms);
	}

	public static void timedLabel(Label label, int ms) {
		label.setVisible(true);

		new Thread() { // Thread per que mostri la label un temps
			@Override
			public void run() {
				try {
					Thread.sleep(ms);
					label.setVisible(false);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static boolean esInt(String text) {
		try {
			Integer.parseInt(text);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

}