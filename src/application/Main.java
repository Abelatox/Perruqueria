package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	static Scene scene;
	static Connection con;

	@Override
	public void start(Stage primaryStage) {
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost/perruqueria", "postgres", "dam");
			con = DriverManager.getConnection("jdbc:postgresql://localhost/perruqueria", "postgres", "smx");
			
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Pane root = FXMLLoader.load(getClass().getResource("/application/LoginController.fxml"));
			Scene scene = new Scene(root);
			Util.openGUI(scene, primaryStage, Strings.TITLE_MAIN);
		}catch(IOException e){
			System.out.println("Problema accedint al FXML");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Problema amb SQL");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Classe no trobada");
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return con;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Scene getMainScene() {
		return scene;
	}
}
