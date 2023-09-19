package tp1;

import javax.json.stream.JsonGenerator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Organ
{
	
	String name;
    int id;
    int systemID;
    
    public Organ(String name, int id, int systemID) {
    	this.name = name;
		this.id = id;
		this.systemID = systemID;
	}

    public void JSONconverter(JsonGenerator jsonGenerator) throws Exception, IFT287Exception{
        try {
            jsonGenerator.writeStartObject()
                    .write("name", name)
                    .write("id", id)
                    .write("systemID", systemID);
            jsonGenerator.writeEnd();
        }
        catch (Exception e) {
            java.lang.System.out.println(" " + e.toString());
        }
    }

	public void XMLConverter(Document document, Element organElement) {
		try {
			organElement.setAttribute("name", name);
			organElement.setAttribute("id", id+"");
			organElement.setAttribute("systemID", systemID+"");

        } catch (Exception e){
            java.lang.System.out.println(" " + e.toString());
        }
		
	}
	
}
