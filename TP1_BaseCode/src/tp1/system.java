package tp1;

import java.util.ArrayList;
import java.util.List;

import javax.json.stream.JsonGenerator;

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
	
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }
    
    public List<Flow> getListeFlow() {
        return listeFlow;
    }

    public void JSONconverter(JsonGenerator jsonGenerator) throws Exception, IFT287Exception{
        try {
            jsonGenerator.writeStartObject()
                    .write("name", name)
                    .write("id", id)
                    .write("type", type)
                    .writeStartArray("Flow");
            for(Flow flow : listeFlow){
                flow.JSONconverter(jsonGenerator);
            }

                    jsonGenerator.writeEnd();
            jsonGenerator.writeEnd();
        }
        catch (Exception e) {
            java.lang.System.out.println(" " + e.toString());
        }
    }   

}

/*Gestion System*/
