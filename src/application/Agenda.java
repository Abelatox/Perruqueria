package application;

public class Agenda {
	String horaTrucada;
	String client;
	String servei;
	String dataServei;
	String horaInici;
	String horaFi;
	String treballador;
	int clientGuardat;
	
	public Agenda(String horaTrucada, String client, String servei, String dataServei, String horaInici, String horaFi, String treballador, int clientGuardat) {
		this.horaTrucada = horaTrucada;
		this.client = client;
		this.servei = servei;
		this.dataServei = dataServei;
		this.horaInici = horaInici;
		this.horaFi = horaFi;
		this.treballador = treballador;
		this.clientGuardat = clientGuardat;
	}
}
