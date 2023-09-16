package tp1;

import javax.json.stream.JsonGenerator;

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
}
