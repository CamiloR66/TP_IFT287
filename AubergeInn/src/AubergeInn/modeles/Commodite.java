package AubergeInn.modeles;

import org.bson.Document;

public class Commodite {
	
    private int id;
    private String description;
    private float surplus;
    
    public Commodite() {
    }

    public Commodite(int id, String description, float surplus){
        this.setId(id);
        this.setDescription(description);
        this.setSurplus(surplus);
    }

	public Commodite(Document doc) {
		id = doc.getInteger("idCommodite");
		description = doc.getString("description");
		surplus = doc.getDouble("surplus").floatValue();
	}
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getSurplus() {
		return surplus;
	}
	public void setSurplus(float surplus) {
		this.surplus = surplus;
	}
    
}
