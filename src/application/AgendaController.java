package application;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.sun.javafx.tk.Toolkit;

import application.objectes.Agenda;
import application.objectes.Client;
import application.objectes.Servei;
import application.objectes.Treballador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class AgendaController {

	@FXML
	private Button btnBack, btnGuardar;
	@FXML
	private DatePicker dpData, dpDataVisita;
	@FXML
	private TextField tfHoraInici, tfHoraFi, tfClient;
	@FXML
	private ComboBox<String> cClient, cServei, cTreballdor;

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

	ArrayList<Servei> listServeis = new ArrayList<Servei>();

	ArrayList<Client> listClients = new ArrayList<Client>();

	ArrayList<Agenda> listAgenda = new ArrayList<Agenda>();

	static final int LINE_WIDTH = 2;

	enum PART {
		TOP, BOTTOM
	}

	enum ALIGN {
		TOP, CENTER, BOTTOM
	}

	public void initialize() throws SQLException, InterruptedException {
		SIZEX = canvas.getWidth() - RIGHT_OFFSET;
		SIZEY = canvas.getHeight() - BOTTOM_OFFSET;

		getDades();

		dpData.setValue(LocalDate.now());

		// N� Columnes = una per treballador + una per hores
		COLUMNS = listTreballadors.size() + 1;

		// Mida en p�xels de cada cel�la individual
		CELLX = SIZEX / COLUMNS;
		CELLY = SIZEY / ROWS;

		gc = canvas.getGraphicsContext2D();

		canvas.setOnMouseClicked(event -> {
			double x = event.getX() - LEFT_OFFSET;
			double y = event.getY() - TOP_OFFSET;

			int casellaX = (int) (x / CELLX);
			int casellaY = (int) (y / CELLY);

			// escriureACasella(casellaX, casellaY, "X");

			// TODO eliminar funci�
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

			/*if (event.getButton() == MouseButton.PRIMARY)
				pintarMitja(casellaX, casellaY, PART.TOP);
			else if (event.getButton() == MouseButton.SECONDARY)
				pintarMitja(casellaX, casellaY, PART.BOTTOM);
			 */
			if (casellaX > 0 && casellaX < listTreballadors.size() + 1 && casellaY > 0 && casellaY < ROWS) {
				Agenda a = getAgendaFromCell(casellaX, casellaY);
				if (a != null) {
					//System.out.println(a.getClient());
				} else {
					String time = getTimeFromCell(casellaY);

					tfHoraInici.setText(time);
					dpDataVisita.setValue(dpData.getValue());
					cTreballdor.setValue(listTreballadors.get(casellaX-1).getNom());
				}
			}
		});

		ArrayList<String> tNom = new ArrayList<>();
		for (Treballador t : listTreballadors) {
			tNom.add(t.getNom());
		}

		cTreballdor.getItems().addAll(tNom);

		ArrayList<String> sNom = new ArrayList<>();
		for (Servei s : listServeis) {
			System.out.println(s.getNom());
			sNom.add(s.getNom());
		}

		cServei.getItems().addAll(sNom);

		ArrayList<String> cNom = new ArrayList<>();
		for (Client c : listClients) {
			cNom.add(c.getNom());
		}
		cClient.getItems().addAll(cNom);

		dibuixarTaula();
		omplirTaula();

	};

	private String getTimeFromCell(int row) {
		int hora = (HORA_INICI + row / 2);
		int mins = 0;
		if (row % 2 == 0) {
			hora--;
			mins = 30;
		}

		// Formateja el temps
		String time = (hora < 10 ? "0" + hora : hora) + ":" + (mins == 0 ? "00:00" : mins + ":00");
		return time;
		
	}

	private Agenda getAgendaFromCell(int casellaX, int casellaY) {
		Treballador t = listTreballadors.get(casellaX - 1);
		// System.out.println(t.getNom());
		String time = getTimeFromCell(casellaY);

		// Si la agenda coincideix en treballador i hora (columna i fila) la retorna
		for (Agenda a : listAgenda) {
			if (a.getHoraInici().equals(time) && a.getTreballador().equals(t.getDni())) {
				return a;
			}
		}

		return null;
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

	/**
	 * Per posar una cita.
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void btnGuardar(ActionEvent event) throws Exception {
		System.out.println("Hola");
		
		String sql = " insert into agenda (moment_trucada,client,servei,data_servei,hora_inici,hora_fi,treballador,client_guardat) values (?,?,?,?,?,?,?) ";
		PreparedStatement st = Main.getConnection().prepareStatement(sql);
		
		
		st.setString(1, dpDataVisita.getValue().toString());
		st.setString(2, );
		st.execute();
	}

	/**
	 * Canvia el dia del calendari actualitzar els events del calendari
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void dpData(ActionEvent event) throws Exception {
		// System.out.println(dpData.getValue());
		gc.clearRect(0, 0, SIZEX, SIZEY);
		dibuixarTaula();
		omplirTaula();
	}

	/**
	 * Obt� i carrega les dades dels treballadors
	 * 
	 * @throws SQLException
	 */
	private void getTreballadors() throws SQLException {
		

	}

	/**
	 * Obte i carrega les dades dels clients
	 * 
	 * @throws SQLException
	 * @throws InterruptedException 
	 */
	private void getDades() throws SQLException, InterruptedException {
		Thread clients = new Thread() {
			public void run() {
				String consulta = " select id, name, sexe, telefon, correu from client ";
				PreparedStatement st;
				try {
					st = Main.getConnection().prepareStatement(consulta);
					ResultSet rs = st.executeQuery();

					while (rs.next()) {
						listClients.add(new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
								rs.getString(5)));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		};

		clients.start();

		Thread serveis = new Thread() {
			public void run() {
				String consulta = " select id,nom from servei ";
				PreparedStatement st;
				try {
					st = Main.getConnection().prepareStatement(consulta);

					ResultSet rs = st.executeQuery();

					while (rs.next()) {
						listServeis.add(new Servei(rs.getInt(1), rs.getString(2)));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
		
		serveis.start();
		
		Thread treballador = new Thread() {
			public void run() {
				String consulta = " select dni, name, nick, telefon, correu from treballador ";
				PreparedStatement st;
				try {
					st = Main.getConnection().prepareStatement(consulta);

					ResultSet rs = st.executeQuery();

					while (rs.next()) {
						listTreballadors.add(
								new Treballador(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
		
		treballador.start();
		
		clients.join();
		serveis.join();
		treballador.join();

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
		escriureACasella(0, 0, ALIGN.CENTER, "HORA");

		// Nom
		for (int i = 0; i < listTreballadors.size(); i++) {
			escriureACasella(i + 1, 0, ALIGN.CENTER, listTreballadors.get(i).getNom());
		}

		// :00
		for (int i = 0; i < ROWS / 2; i++) {
			escriureACasella(0, (i + 1) * 2 - 1, ALIGN.CENTER, HORA_INICI + i + ":00");
		}

		// :30
		for (int i = 0; i < ROWS / 2; i++) {
			escriureACasella(0, (i + 1) * 2, ALIGN.CENTER, HORA_INICI + i + ":30");
		}

		// Dades de la agenda
		new Thread() {
			public void run() {
				for (Treballador t : listTreballadors) {
					try {
						// Seleccionem totes les dades en l'agenda d'un treballador en concret (bucle)
						String consulta = " select a.*,s.nom from treballador t inner join agenda a on t.dni = a.treballador inner join servei s on s.id=a.servei where a.data_servei = ? and t.name = ? ";
						PreparedStatement st = Main.getConnection().prepareStatement(consulta);
						// System.out.println(dpData.getValue());
						java.sql.Date date = java.sql.Date.valueOf(dpData.getValue());
						st.setDate(1, date);
						st.setString(2, t.getNom());
						ResultSet rs = st.executeQuery();

						while (rs.next()) {
							// Posem les dades a les caselles corresponents
							// for(int i=1;i<8;i++)
							// System.out.println(i+"- "+rs.getString(i));
							// System.out.println(8+"- "+rs.getString(8));
							// String nom
							Agenda a = new Agenda(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
									rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8));
							a.setServei(rs.getString(9));// No funcionava en el constructor
							posarDataAAgenda(a);
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
	 *            Agenda a introdu�r
	 */
	private void posarDataAAgenda(Agenda a) {
		int col = -1;
		int row = -1;

		for (int i = 0; i < listTreballadors.size(); i++) {
			if (listTreballadors.get(i).getDni().equals(a.getTreballador())) {
				col = i;
			}
		}
		String[] time = a.getHoraInici().split(":");
		int hora = Integer.parseInt(time[0]);
		int min = Integer.parseInt(time[1]);

		// La fila ser� la hora - la inicial (9) * 2 (hora i mitja hora)
		row = (hora - HORA_INICI) * 2;
		// Si els minuts son mes de 0 la row incrementa (:30)
		row = min != 0 ? row + 1 : row;

		int temps = getDiferencia(a.getHoraInici(), a.getHoraFi());
		for (int i = 0; i < temps; i++) {
			pintarCasella(col + 1, row + 1 + i);
		}

		if (a.getClientGuardat() > -1) {
			int cGuardat = a.getClientGuardat()-1;
			escriureACasella(col + 1, row + 1, ALIGN.TOP, listClients.get(cGuardat).getNom());
		} else {
			escriureACasella(col + 1, row + 1, ALIGN.TOP, a.getClient());
		}
		escriureACasella(col + 1, row + 1, ALIGN.BOTTOM, a.getServei());

		listAgenda.add(a);
	}

	/**
	 * Retorna la diferencia entre 2 hores
	 * 
	 * @param horaInici
	 * @param horaFi
	 * @return
	 */
	private int getDiferencia(String horaInici, String horaFi) {
		String[] timeInici = horaInici.split(":");
		String[] timeFi = horaFi.split(":");

		int horaInicial = Integer.parseInt(timeInici[0]);
		int minInicial = Integer.parseInt(timeInici[1]);

		int horaFinal = Integer.parseInt(timeFi[0]);
		int minFinal = Integer.parseInt(timeFi[1]);

		int hores = Math.abs(horaFinal - horaInicial);
		int minuts = Math.abs(minFinal - minInicial);

		// System.out.println(hores+":"+minuts);
		return hores + (minuts == 0 ? 0 : 1);
	}

	/**
	 * Pinta mitja casella
	 * 
	 * @param x
	 * @param y
	 * @param part
	 */
	public void pintarMitja(int x, int y, PART part) {
		double col = LEFT_OFFSET + (x * CELLX);
		double row = TOP_OFFSET + (y * CELLY);
		gc.setFill(Color.rgb(255, 0, 0));

		if (part == PART.TOP)
			gc.fillRect(col + LINE_WIDTH, row + LINE_WIDTH, CELLX - LINE_WIDTH, CELLY / 2 - LINE_WIDTH / 2);
		else
			gc.fillRect(col + LINE_WIDTH, row + CELLY / 2 + LINE_WIDTH / 2, CELLX - LINE_WIDTH,
					CELLY / 2 - LINE_WIDTH / 2);
	}

	/**
	 * Pinta casella
	 * 
	 * @param x
	 * @param y
	 */
	public void pintarCasella(int x, int y) {
		double col = LEFT_OFFSET + (x * CELLX);
		double row = TOP_OFFSET + (y * CELLY);
		Paint old = gc.getFill();
		gc.setFill(Color.rgb(255, 0, 0));
		gc.fillRect(col + LINE_WIDTH, row + LINE_WIDTH, CELLX - LINE_WIDTH, CELLY - LINE_WIDTH);
		gc.setFill(old);
	}

	/**
	 * Escriu un text centrat a una casella
	 * 
	 * @param x
	 * @param y
	 * @param text
	 */
	public void escriureACasella(int x, int y, String text) {
		// Longitud (px) del text d'amplada
		float width = Toolkit.getToolkit().getFontLoader().computeStringWidth(text, gc.getFont());
		// Longitud (px) de la font d'al�ada
		float height = Toolkit.getToolkit().getFontLoader().getFontMetrics(gc.getFont()).getLineHeight();
		// Punt mig de la cel�la (longitud de la cel�la * num de cel�la + longitud
		// de
		// cel�la / 2.
		double XCENTER = LEFT_OFFSET + (x * CELLX + CELLX / 2);
		double YCENTER = TOP_OFFSET + (y * CELLY + CELLY / 1.05);
		gc.fillText(text, XCENTER - width / 2, YCENTER - height / 2);
	}

	/**
	 * Escriu un text centrat alineat a una casella
	 * 
	 * @param x
	 * @param y
	 * @param text
	 */
	public void escriureACasella(int x, int y, ALIGN align, String text) {
		// Longitud (px) del text d'amplada
		float width = Toolkit.getToolkit().getFontLoader().computeStringWidth(text, gc.getFont());
		// Longitud (px) de la font d'al�ada
		float height = Toolkit.getToolkit().getFontLoader().getFontMetrics(gc.getFont()).getLineHeight();
		// Punt mig de la cel�la (longitud de la cel�la * num de cel�la + longitud
		// de
		// cel�la / 2.
		double XCENTER = LEFT_OFFSET + (x * CELLX + CELLX / 2);
		double yOffset = 0;
		switch (align) {
		case TOP:
			yOffset = 1.3;
			break;
		case CENTER:
			yOffset = 1.05;
			break;
		case BOTTOM:
			yOffset = 0.8;
		}
		double YCENTER = TOP_OFFSET + (y * CELLY + CELLY / yOffset);
		gc.fillText(text, XCENTER - width / 2, YCENTER - height / 2);
	}

}