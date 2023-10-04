package AubergeInn.Modele;

public class Client {
     
    private int idClient;

    private String nom;
     
    private String prenom;

    private int age;

    //GET/SET
    public int getAge() {
        return age;
    }
    public int getIdClient() {
        return idClient;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Client(int idClient, String nom, String prenom, int age) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    public void printClient(){
        System.out.println("ID: " + idClient + " Nom: " + nom + " Prenom: " + prenom + " Age: " + age);
    }

}
