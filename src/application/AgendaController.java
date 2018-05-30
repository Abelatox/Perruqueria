package application;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.javafx.tk.Toolkit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AgendaController {

	@FXML
	private Button btnBack;

	@FXML
	private Canvas canvas;

	GraphicsContext gc;

	double SIZEX;
	double SIZEY;

	static final int LEFT_OFFSET = 5, TOP_OFFSET = 5, BOTTOM_OFFSET = 20, RIGHT_OFFSET = 10;

	double CELLX;
	double CELLY;

	int ROWS = 21;
	int COLUMNS;

	ArrayList<Treballador> listTreballadors = new ArrayList<Treballador>();

	static final int LINE_WIDTH = 2;

	public void initialize() throws SQLException {
		SIZEX = canvas.getWidth() - RIGHT_OFFSET;
		SIZEY = canvas.getHeight() - BOTTOM_OFFSET;

		getTreballadors();

		// Nº Columnes = una per treballador + una per hores
		COLUMNS = listTreballadors.size() + 1;

		// Mida en píxels de cada cel·la individual
		CELLX = SIZEX / COLUMNS;
		CELLY = SIZEY / ROWS;

		gc = canvas.getGraphicsContext2D();

		canvas.setOnMouseClicked(event -> {
			double x = event.getX() - LEFT_OFFSET;
			double y = event.getY() - TOP_OFFSET;

			int casellaX = (int) (x / CELLX);
			int casellaY = (int) (y / CELLY);

			escriureACasella(casellaX, casellaY, "X");

			// TODO eliminar funció
			if (1 == 1) {
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
		omplirTaula();
	}

	/**
	 * Per tornar al menú principal
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

	/**
	 * Obté i carrega les dades dels treballadors
	 * 
	 * @throws SQLException
	 */
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
		for (double x = LEFT_OFFSET; x <= SIZEX + LEFT_OFFSET; x += CELLX) {
			gc.fillRect(x, TOP_OFFSET, LINE_WIDTH, SIZEY);
		}

		// Horitzontals
		for (double y = TOP_OFFSET; y <= SIZEY + TOP_OFFSET; y += CELLY) {
			gc.fillRect(LEFT_OFFSET, y, SIZEX, LINE_WIDTH);
		}

	}

	private void omplirTaula() {
		escriureACasella(0, 0, "HORA");

		// Nom
		for (int i = 0; i < listTreballadors.size(); i++) {
			escriureACasella(i + 1, 0, listTreballadors.get(i).getNom());
		}

		// :00
		for (int i = 0; i < ROWS / 2; i++) {
			escriureACasella(0, (i + 1) * 2 - 1, 9 + i + ":00");
		}

		// :30
		for (int i = 0; i < ROWS / 2; i++) {
			escriureACasella(0, (i + 1) * 2, 9 + i + ":30");
		}

		// Dades dels treballadors
		new Thread() {
			public void run() {
				for (Treballador t : listTreballadors) {
					try {
						String consulta = " select a.* from treballador t inner join agenda a on t.dni = a.treballador inner join servei s on s.id=a.servei where a.data_servei = current_date and t.name = ? ";
						PreparedStatement st = Main.getConnection().prepareStatement(consulta);
						st.setString(1, t.getNom());
						ResultSet rs = st.executeQuery();

						while (rs.next()) {
							// TODO Posar les dades a les caselles corresponents
							Agenda a = new Agenda(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
									rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8));
							posarDataAAgenda(a);
							System.out.println(a.dataServei + " " + a.horaInici + " "
									+ (a.clientGuardat == -1 ? a.client : a.clientGuardat));

						}

					} catch (SQLException e) {
						e.printStackTrace();
					}

				}
			}

		}.start();

	}

	private void posarDataAAgenda(Agenda a) {
		int col = -1;
		int row = 6;

		for (int i = 0; i < listTreballadors.size(); i++) {
			if (listTreballadors.get(i).getDni().equals(a.treballador)) {
				col = i;
			}
		}
		
		
		escriureACasella(col + 1, row, a.client);
	}

	public void escriureACasella(int x, int y, String text) {
		float width = Toolkit.getToolkit().getFontLoader().computeStringWidth(text, gc.getFont());
		float height = Toolkit.getToolkit().getFontLoader().getFontMetrics(gc.getFont()).getLineHeight();
		double XCENTER = LEFT_OFFSET + (x * CELLX + CELLX / 2);
		double YCENTER = TOP_OFFSET + (y * CELLY + CELLY / 0.9F);
		gc.fillText(text, XCENTER - width / 2, YCENTER - height / 2);
	}

}