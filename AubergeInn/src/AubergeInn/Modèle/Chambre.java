package AubergeInn;

import java.util.ArrayList;

public class Chambre {
	
    private String nomChambre;
    private int IdChambre;
    private float prixBase;
    private String typeLit; 
    private ArrayList<Commodite> commodites;
    
    
    public Chambre(int idChambre,String nomChambre, float prixBase, String typeLit) {
        IdChambre = idChambre;
        this.prixBase = prixBase;
        this.typeLit = typeLit;
        commodites = new ArrayList<Commodite>();
        this.nomChambre = nomChambre;
    }

    public Chambre(String nomChambre,float prixBase, String typelit) {
        this.prixBase = prixBase;
        this.typeLit = typelit;
        commodites = new ArrayList<Commodite>();
        this.nomChambre = nomChambre;
    }
    
    //Get-Set for the class 
    
    public int getIdChambre() {
        return IdChambre;
    }

    public void setIdChambre(int idChambre) {
        IdChambre = idChambre;
    }

    public float getPrixBase() {
        return prixBase;
    }

    public void setPrixBase(float prixBase) {
        this.prixBase = prixBase;
    }

    public String getTypeLit() {
        return typeLit;
    }

    public void setTypeLit(String typeLit) {
        this.typeLit = typeLit;
    }

    public String getNomChambre() {
        return nomChambre;
    }

    public void setNomChambre(String nomChambre) {
        this.nomChambre = nomChambre;
    }

    public ArrayList<Commodite> getCommodites() {
        return commodites;
    }

    public void setCommodites(ArrayList<Commodite> commodites) {
        this.commodites = commodites;
    }
    
    public void addCommodite(Commodite commodite) {
    	this.commodites.add(commodite);
    	
    }
    
    public void removeCommodite(Commodite commodite) {
    	
    	if(commodite )
    	
    	
    }
    
   

}
