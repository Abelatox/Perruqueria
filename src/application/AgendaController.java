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

	int HORA_INICI = 9;

	ArrayList<Treballador> listTreballadors = new ArrayList<Treballador>();

	ArrayList<Agenda> listAgenda = new ArrayList<Agenda>();

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

			if (casellaX > 0 && casellaX < listTreballadors.size() + 1 && casellaY > 0 && casellaY < ROWS) {
				Agenda a = getAgendaFromCell(casellaX, casellaY);
				if (a != null) {
					System.out.println(a.client);
				} else {
					System.out.println("Nova cita");
				}
			}
		});

		dibuixarTaula();
		omplirTaula();
	}

	private Agenda getAgendaFromCell(int casellaX, int casellaY) {
		Treballador t = listTreballadors.get(casellaX - 1);
		System.out.println(t.getNom());
		int hora = (HORA_INICI + casellaY / 2);
		int mins = 0;
		if (casellaY % 2 == 0) {
			hora--;
			mins = 30;
		}

		// Formateja el temps
		String time = (hora < 10 ? "0" + hora : hora) + ":" + (mins == 0 ? "00:00" : mins + ":00");

		// Si la agenda coincideix en treballador i hora (columna i fila) la retorna
		for (Agenda a : listAgenda) {
			if (a.getHoraInici().equals(time) && a.getTreballador().equals(t.getDni())) {
				return a;
			}
		}

		return null;
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
			escriureACasella(0, (i + 1) * 2 - 1, HORA_INICI + i + ":00");
		}

		// :30
		for (int i = 0; i < ROWS / 2; i++) {
			escriureACasella(0, (i + 1) * 2, HORA_INICI + i + ":30");
		}

		// Dades de la agenda
		new Thread() {
			public void run() {
				for (Treballador t : listTreballadors) {
					try {
						// Seleccionem totes les dades en l'agenda d'un treballador en concret (bucle)
						String consulta = " select a.*,s.nom from treballador t inner join agenda a on t.dni = a.treballador inner join servei s on s.id=a.servei where a.data_servei = current_date and t.name = ? ";
						PreparedStatement st = Main.getConnection().prepareStatement(consulta);
						st.setString(1, t.getNom());
						ResultSet rs = st.executeQuery();

						while (rs.next()) {
							// Posem les dades a les caselles corresponents
							Agenda a = new Agenda(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
									rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8));
							a.setServei(rs.getString(9));// No funcionava en el constructor
							posarDataAAgenda(a);

							/*
							 * System.out.println(a.dataServei + " " + a.horaInici + " " + (a.clientGuardat
							 * == -1 ? a.client : a.clientGuardat));
							 */
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	/**
	 * Posa una data a la casella a partir d'una agenda
	 * 
	 * @param a
	 *            Agenda a introduïr
	 */
	private void posarDataAAgenda(Agenda a) {
		int col = -1;
		int row = -1;

		for (int i = 0; i < listTreballadors.size(); i++) {
			if (listTreballadors.get(i).getDni().equals(a.treballador)) {
				col = i;
			}
		}
		String[] time = a.horaInici.split(":");
		int hora = Integer.parseInt(time[0]);
		int min = Integer.parseInt(time[1]);

		// La fila serà la hora - la inicial (9) * 2 (hora i mitja hora)
		row = (hora - HORA_INICI) * 2;
		// Si els minuts son més de 0 la row incrementarà (:30)
		row = min != 0 ? row + 1 : row;

		escriureACasella(col + 1, row + 1, a.client + " - " + a.servei);

		listAgenda.add(a);
	}

	public void escriureACasella(int x, int y, String text) {
		// Longitud (px) del text d'amplada
		float width = Toolkit.getToolkit().getFontLoader().computeStringWidth(text, gc.getFont());
		// Longitud (px) de la font d'alçada
		float height = Toolkit.getToolkit().getFontLoader().getFontMetrics(gc.getFont()).getLineHeight();
		// Punt mig de la cel·la (longitud de la cel·la * num de cel·la + longitud de
		// cel·la / 2.
		double XCENTER = LEFT_OFFSET + (x * CELLX + CELLX / 2);
		double YCENTER = TOP_OFFSET + (y * CELLY + CELLY / 0.9F);
		gc.fillText(text, XCENTER - width / 2, YCENTER - height / 2);
	}

}