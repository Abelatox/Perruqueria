package application.objectes;

public class Treballador {
	private String dni, nom, nick, mail;
	private int tlf;

	public Treballador(String dni, String nom, String nick, int tlf, String mail) {
		this.dni = dni;
		this.nom = nom;
		this.nick = nick;
		this.mail = mail;
		this.tlf = tlf;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getTlf() {
		return tlf;
	}

	public void setTlf(int tlf) {
		this.tlf = tlf;
	}
}
