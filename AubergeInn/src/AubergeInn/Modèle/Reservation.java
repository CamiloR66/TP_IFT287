package AubergeInn.Modèle;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Reservation {

    private Date debut;
    private Date fin;

    private Client client;
    private Chambre chambre;    

    public Reservation(Date debut, Date fin, Client client, Chambre chambre) {
        this.debut = debut;
        this.fin = fin;
        this.client = client;
        this.chambre = chambre;
    }

    public Date getDebut() {
        return debut;
    }

    public Date getFin() {
        return fin;
    }

    public Client getClient() {
        return client;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }
    public Float getPrixTotal(){
        return (TimeUnit.MILLISECONDS.toDays(fin.getTime()-debut.getTime()) * chambre.getPrixBase()) + chambre.getPrixTotalChambre();
    }

    public void print(){
        System.out.println("Debut: " + debut + " Fin: " + fin + " Client: " + client.getIdClient() + " Chambre: " + chambre.getIdChambre() + " Prix: " + getPrixTotal());
    }


}
