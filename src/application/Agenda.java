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
	String nomServei;
	
	public Agenda(String horaTrucada, String client, String servei, String dataServei, String horaInici, String horaFi, String treballador, int clientGuardat, String nomServei) {
		this.horaTrucada = horaTrucada;
		this.client = client;
		this.servei = servei;
		this.dataServei = dataServei;
		this.horaInici = horaInici;
		this.horaFi = horaFi;
		this.treballador = treballador;
		this.clientGuardat = clientGuardat;
		this.nomServei = nomServei;
	}
	
	public String getHoraTrucada() {
		return horaTrucada;
	}

	public void setHoraTrucada(String horaTrucada) {
		this.horaTrucada = horaTrucada;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getServei() {
		return servei;
	}

	public void setServei(String servei) {
		//System.out.println(servei);
		this.servei = servei;
	}

	public String getDataServei() {
		return dataServei;
	}

	public void setDataServei(String dataServei) {
		this.dataServei = dataServei;
	}

	public String getHoraInici() {
		return horaInici;
	}

	public void setHoraInici(String horaInici) {
		this.horaInici = horaInici;
	}

	public String getHoraFi() {
		return horaFi;
	}

	public void setHoraFi(String horaFi) {
		this.horaFi = horaFi;
	}

	public String getTreballador() {
		return treballador;
	}

	public void setTreballador(String treballador) {
		this.treballador = treballador;
	}

	public int getClientGuardat() {
		return clientGuardat;
	}

	public void setClientGuardat(int clientGuardat) {
		this.clientGuardat = clientGuardat;
	}

	public String getNomServei() {
		return nomServei;
	}

	public void setNomServei(String nomServei) {
		this.nomServei = nomServei;
	}

	
}
