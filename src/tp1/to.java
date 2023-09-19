package tp1;

import javax.json.stream.JsonGenerator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class to
{
    int id;
	
	public to(int id) {
		this.id = id;
	}
	
    public int getId() {
        return id;
    }

    public void JSONconverter(JsonGenerator jsonGenerator) throws Exception, IFT287Exception{
        try {
            jsonGenerator.writeStartObject()
                    .write("id", id);
            jsonGenerator.writeEnd();
        }
        catch (Exception e) {
            java.lang.System.out.println(" " + e.toString());
        }
    }

	public void XMLConverter(Document document, Element toElement) {
		try {
			toElement.setAttribute("id", id+"");

        } catch (Exception e){
            java.lang.System.out.println(" " + e.toString());
        }
		
	}
}
