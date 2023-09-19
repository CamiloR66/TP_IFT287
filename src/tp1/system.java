package tp1;

import java.util.ArrayList;
import java.util.List;

import javax.json.stream.JsonGenerator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

	public void XMLConverter(Document document, Element bodySystemElement) {
		try {
			bodySystemElement.setAttribute("name", name);
			bodySystemElement.setAttribute("id", id+"");
			bodySystemElement.setAttribute("type", type+"");

            for (Flow flow: listeFlow) {
                Element flowElement = document.createElement("Flow");
                flow.XMLConverter(document, flowElement);
                bodySystemElement.appendChild(flowElement);
            }

        } catch (Exception e){
            java.lang.System.out.println(" " + e.toString());
        }
		
	}   

}

/*Gestion System*/
