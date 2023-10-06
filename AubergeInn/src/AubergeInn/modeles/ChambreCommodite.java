package AubergeInn.modeles;

public class ChambreCommodite {

	private int idChambre;
    private int idCommodite;
    
    public ChambreCommodite() {
    }

    public ChambreCommodite(int idChambre, int idCommodite) {
        this.idChambre = idChambre;
        this.idCommodite = idCommodite;
    }
    
	public int getIdChambre() {
		return idChambre;
	}
	public void setIdChambre(int idChambre) {
		this.idChambre = idChambre;
	}
	public int getIdCommodite() {
		return idCommodite;
	}
	public void setIdCommodite(int idCommodite) {
		this.idCommodite = idCommodite;
	}
    
}
