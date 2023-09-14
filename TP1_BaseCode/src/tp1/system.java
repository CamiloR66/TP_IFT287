package tp1;

import java.util.ArrayList;
import java.util.List;

public class system
{
    List<Flow> listeFlow=new ArrayList<Flow>();
    String name;
    int id;
    int type; 
	
	public system(String name, int id, int type) {
		this.name = name;
		this.id = id;
		this.type = type;
	}
	
	public void addFlow(Flow flow) {
		listeFlow.add(flow);
	}
	 
    

}

/*Gestion System*/
