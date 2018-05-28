package application;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.javafx.tk.Toolkit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AgendaController {

	@FXML
	private Canvas canvas;

	GraphicsContext gc;

	double SIZEX;
	double SIZEY;

	static final int OFFSETX = 10;
	static final int OFFSETY = 5;

	double CELLX;
	double CELLY;

	// int horaInicial = 9;
	// int horaFinal = 18;

	// int ROWS = (horaFinal - horaInicial) * 2 + 1; // Tantes files com hores obert
	// * 2 (sencera i mitja) + capçalera
	int ROWS = 20;
	int COLUMNS;

	ArrayList<Treballador> listTreballadors = new ArrayList<Treballador>();

	static final int LINE_WIDTH = 2;

	public void initialize() throws SQLException {
		SIZEX = canvas.getWidth();
		//canvas.setHeight(720);
		SIZEY = canvas.getHeight();// canvas.getHeight();

		getTreballadors();

		COLUMNS = listTreballadors.size() + 1;
		CELLX = SIZEX / COLUMNS;
		CELLY = SIZEY / ROWS;// - LINE_WIDTH / 2;

		//SIZEX = CELLX * COLUMNS;
		//SIZEY = SIZEY * ROWS;

		gc = canvas.getGraphicsContext2D();

		canvas.setOnMouseClicked(event -> {
			double x = event.getX() - OFFSETX;
			double y = event.getY() - OFFSETY;

			int casellaX = (int) (x / CELLX);
			int casellaY = (int) (y / CELLY);
			System.out.println(casellaX + ":" + x + "," + casellaY + ":" + y);

			escriureACasella(casellaX, casellaY, "X");
			System.out.println(SIZEX + "/" + CELLX+"/"+COLUMNS);
			System.out.println(SIZEY + "/" + CELLY+"/"+ROWS);

			if (1 == 2) {
				Pane root;
				try {
					root = FXMLLoader.load(getClass().getResource("/application/AgendaController.fxml"));
					Scene scene = new Scene(root);
					Stage stage = (Stage) canvas.getScene().getWindow();
					Util.openGUI(scene, stage, Strings.TITLE_MAIN);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		dibuixarTaula();
		emplenarTaula();
	}

	private void getTreballadors() throws SQLException {
		String consulta = " select dni, name, nick, telefon, correu from treballador ";
		PreparedStatement st = Main.getConnection().prepareStatement(consulta);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			listTreballadors.add(
					new Treballador(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)));
		}

	}

	private void dibuixarTaula() {
		gc.setFill(Color.rgb(0, 0, 0));

		// Verticals
		for (int x = OFFSETX; x <= CELLX * COLUMNS + OFFSETX; x += CELLX) {
			gc.fillRect(x, OFFSETY, LINE_WIDTH, SIZEY);
		}

		// Horitzontals
		for (int y = OFFSETY; y <= CELLY * ROWS + OFFSETY; y += CELLY) {
			gc.fillRect(OFFSETX, y, SIZEX, LINE_WIDTH);
		}

	}

	private void emplenarTaula() {
		escriureACasella(0, 0, "HORA");

		// Nom
		for (int i = 0; i < listTreballadors.size(); i++) {
			escriureACasella(i + 1, 0, listTreballadors.get(i).nom);
		}

		// :00
		for (int i = 0; i < ROWS + 1; i++) {
			escriureACasella(0, (i + 1) * 2 - 1, 9 + i + ":00");
		}

		// :30
		for (int i = 0; i < ROWS + 1; i++) {
			escriureACasella(0, (i + 1) * 2, 9 + i + ":30");
		}

	}

	public void escriureACasella(int x, int y, String text) {
		float width = Toolkit.getToolkit().getFontLoader().computeStringWidth(text, gc.getFont());
		float height = Toolkit.getToolkit().getFontLoader().getFontMetrics(gc.getFont()).getLineHeight();
		double XCENTER = OFFSETX + (x * CELLX + CELLX / 2);
		double YCENTER = OFFSETY + (y * CELLY + CELLY / 0.9F);
		gc.fillText(text, XCENTER - width / 2, YCENTER - height / 2);
	}

}