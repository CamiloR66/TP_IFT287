package tp1;

import java.util.ArrayList;
import java.util.List;

import javax.json.stream.JsonGenerator;

public class MainBody
{
	int bodyID;
	String bodyName;
    List<system> listeSystems = new ArrayList<system>();
    List<Organ> listeOrgan = new ArrayList<Organ>();
    
    public MainBody(String bodyName, int bodyID) {
		this.bodyID = bodyID;
		this.bodyName = bodyName;
	}

	public void addSystem(system system){
    	listeSystems.add(system);
    }
    
    public void addOrgan(Organ Organ){
    	listeOrgan.add(Organ);
    }
    
    public List<system> getListeSystems() {
        return listeSystems;
    }
    
    public List<Organ> getListeOrgan() {
        return listeOrgan;
    }

	public void JSONconverter(JsonGenerator jsonGenerator) {
		try {
            jsonGenerator.writeStartObject()
                    .writeStartObject("MainBody")
                    .write("bodyName", bodyName)
                    .write("bodyID", bodyID)
                    .writeStartArray("System");

            for (int i = 1; i < listeSystems.size(); i++){
                system system = listeSystems.get(i);
                system.JSONconverter(jsonGenerator);
            }

                    jsonGenerator.writeEnd();   //fin de list<bodySystem>

                    jsonGenerator.writeStartArray("Organ");

            for(Organ organ : listeOrgan) {
                organ.JSONconverter(jsonGenerator);
            }
                    jsonGenerator.writeEnd();   //fin de list<organ>

            jsonGenerator.writeEnd();   //fin de MainBody
            jsonGenerator.writeEnd();   //fin de ()
        }
        catch (Exception e) {
            java.lang.System.out.println(" " + e.toString());
        }
		
	}
}
