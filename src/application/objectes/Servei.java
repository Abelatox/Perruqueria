package application.objectes;

public class Servei {
	int id;
	String nom;
	Double preu;

	public Servei(int id, String nom,Double preu) {
		this.id = id;
		this.nom = nom;
		this.preu = preu;
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

	public Double getPreu() {
		return preu;
	}

	public void setPreu(Double preu) {
		this.preu = preu;
	}
}
