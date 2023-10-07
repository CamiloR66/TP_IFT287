package AubergeInn.modeles;

public class Chambre {

	private int id;
    private String nom;
    private String lit;
    private int prix;
    
    public Chambre(){
    }
    
    public Chambre(int idCh, String nom, String lit, int prix) {
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
	public int getPrix() {
		return prix;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}
	
}
