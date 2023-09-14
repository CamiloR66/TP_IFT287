package tp1;

import java.util.ArrayList;
import java.util.List;

public class MainBody
{
	int bodyId;
	String bodyName;
    List<system> listeSystems = new ArrayList<system>();
    List<Organ> listeOrgan = new ArrayList<Organ>();
    
    public MainBody(String bodyName, int bodyId) {
		this.bodyId = bodyId;
		this.bodyName = bodyName;
	}

	public void addSystem(system system){
    	listeSystems.add(system);
    }
    
    public void addOrgan(Organ Organ){
    	listeOrgan.add(Organ);
    }
}
