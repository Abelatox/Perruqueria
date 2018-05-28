package application;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Util {

	/**
	 * Obre una pantalla
	 * 
	 * @param scene
	 * @param stage
	 * @param title
	 */
	public static void openGUI(Scene scene, Stage stage, String title) {
		Main.scene = scene;
		// stage.setMaximized(true);

		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle(title);
		stage.show();
	}

	/**
	 * Obre una pantalla amb un StageStyle passat per paràmetre
	 * 
	 * @param scene
	 * @param stage
	 * @param style
	 * @param title
	 */
	public static void openGUI(Scene scene, Stage stage, StageStyle style, String title) {
		Main.scene = scene;
		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle(title);
		stage.initStyle(style);
		// stage.setMaximized(true);
		stage.show();

	}

	/**
	 * Ensenya i oculta una etiqueta passat un temps amb un text
	 * 
	 * @param text
	 * @param label
	 * @param ms
	 */
	public static void timedLabel(String text, Label label, int ms) {
		label.setText(text);
		timedLabel(label, ms);
	}

	/**
	 * Ensenya i oculta una etiqueta passat un temps
	 * 
	 * @param label
	 * @param ms
	 */
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

	/**
	 * Funció per comprovar si és int
	 * 
	 * @param text
	 * @return
	 */
	public static boolean esInt(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}