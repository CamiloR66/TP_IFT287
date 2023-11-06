package AubergeInn.modeles;

import java.util.List;

import javax.persistence.OneToMany;

public class Chambre {

	private int id;
	private String nom;
	private String lit;
	private float prix;
	@OneToMany(mappedBy = "m_chambre")
	private List<Commodite> m_commodites;
	@OneToMany(mappedBy = "m_chambre")
	private List<Reservation> m_reservations;

	public Chambre(){
	}

	public Chambre(int idCh, String nom, String lit, float prix) {
		this.id = idCh;
		this.nom = nom;
		this.lit = lit;
		this.prix = prix;
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
	public String getlit() {
		return lit;
	}
	public void setLit(String lit) {
		this.lit = lit;
	}
	public float getPrix() {
		return prix;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}
	public int getChambre(int id) {
		return 1;
	}
	public List<Commodite> getCommodite() {
		return m_commodites;
	}
	public void include(Commodite commodite)
	{
		m_commodites.add(commodite);
	}
	public void remove(Commodite commodite)
	{
		m_commodites.remove(commodite);
	}
	public List<Reservation> getM_reservations() {
		return m_reservations;
	}
	public void add(Reservation reservation)
	{
		m_reservations.add(reservation); 
		}
}
