package tp1;

import java.util.ArrayList;
import java.util.List;

import javax.json.stream.JsonGenerator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Connection {
	int id;
	List<to> listeTo = new ArrayList<to>();

	public Connection(int id) {
		this.id = id;
	}

	public void addTo(to to) {
		listeTo.add(to);

	}

	public List<to> getListeTo() {
		return listeTo;
	}

	public int getId() {
		return id;
	}

	public void JSONconverter(JsonGenerator jsonGenerator) throws Exception, IFT287Exception{
        try {
            jsonGenerator.writeStartObject()
                    .write("id", id)
                    .writeStartArray("To");
            for(to to : listeTo){
                to.JSONconverter(jsonGenerator);
            }
                    jsonGenerator.writeEnd();   //To
            jsonGenerator.writeEnd();   //Connection
        }
        catch (Exception e) {
            java.lang.System.out.println(" " + e.toString());
        }
    }

	public void XMLConverter(Document document, Element connectionElement) {
		 try {
			 connectionElement.setAttribute("id", id+"");

	            for (to to: listeTo) {
	                Element toElement = document.createElement("to");
	                to.XMLConverter(document, toElement);
	                connectionElement.appendChild(toElement);
	            }

	        } catch (Exception e){
	            java.lang.System.out.println(" " + e.toString());
	        }
		
	}

}
