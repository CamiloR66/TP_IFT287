package AubergeInn.modeles;

import java.util.List;

import javax.persistence.OneToMany;

import org.bson.Document;

public class Client {

	private int id;
	private String Nom;
    private String Prenom;
    private int Age;
    @OneToMany(mappedBy = "m_client")
    private List<Reservation> m_reservation;

    
    public Client(Document doc)
    {
		Prenom = doc.getString("prenom");
		Nom = doc.getString("nom");
		Age = doc.getInteger("age");
		id = doc.getInteger("idClient");
    }

    public Client(int idClient, String nom, String prenom, int age) {
        this.setIdClient(idClient);
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setAge(age);
    }
    
    public int getIdClient() {
		return id;
	}
	public void setIdClient(int id) {
		this.id = id;
	}
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public String getPrenom() {
		return Prenom;
	}
	public void setPrenom(String prenom) {
		Prenom = prenom;
	}
	public int getAge() {
		return Age;
	}
	public void setAge(int age) {
		Age = age;
	}
	 public void add(Reservation reservation)
	 {
	        m_reservation.add(reservation);
	    }
	
}
