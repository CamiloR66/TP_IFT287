package AubergeInn.modeles;

public class Client {

	private int id;
	private String Nom;
    private String Prenom;
    private int Age;
    
    public Client()
    {
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
	
}
