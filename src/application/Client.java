package application;

public class Client {
	int id;
	String nom;
	String sexe;
	String telefon;
	String correu;
	
	public Client(int id, String nom, String sexe, String telefon, String correu) {
		super();
		this.id = id;
		this.nom = nom;
		this.sexe = sexe;
		this.telefon = telefon;
		this.correu = correu;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getCorreu() {
		return correu;
	}

	public void setCorreu(String correu) {
		this.correu = correu;
	}
	
	
	
	
}
