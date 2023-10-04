package AubergeInn.Modele;

public class Commodite {

    private int idCommodite;
    private String description;
    private float surplusPrix;

    public Commodite(int idCommodite, String description, float surplusPrix) {
        this.idCommodite = idCommodite;
        this.description = description;
        this.surplusPrix = surplusPrix;
    }

    public int getIdCommodite() {
        return idCommodite;
    }   
    
    public String getDescription() {
        return description;
    }

    public float getSurplusPrix() {
        return surplusPrix;
    }

    public void setIdCommodite(int idCommodite) {
        this.idCommodite = idCommodite;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSurplusPrix(float surplusPrix) {
        this.surplusPrix = surplusPrix;
    }
        
    public void print(){
        System.out.println("ID: " + idCommodite + " Description: " + description + " Surplus: " + surplusPrix);
    }

}
