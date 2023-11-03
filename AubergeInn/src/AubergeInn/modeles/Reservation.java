package AubergeInn.modeles;

import java.sql.Date;

public class Reservation {

	private int idClient;
    private int idChambre;
    private Date dateDebut;
    private Date dateFin;
    
    public Reservation()
    {
    }

    public Reservation(int idClient, int idChambre, Date dateDebut, Date dateFin) {
        this.setIdClient(idClient);
        this.setIdChambre(idChambre);
        this.setDateDebut(dateDebut);
        this.setDateFin(dateFin);
    }
    
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	public int getIdChambre() {
		return idChambre;
	}
	public void setIdChambre(int idChamre) {
		this.idChambre = idChamre;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
    
}
