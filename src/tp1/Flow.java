package tp1;

import java.util.ArrayList;
import java.util.List;

import javax.json.stream.JsonGenerator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Flow
{
    List<Connectible> listeConnectible=new ArrayList<Connectible>();
    List<Connection> listeConnection=new ArrayList<Connection>();
    String name;
    int id;
    
    public Flow( int id, String name) {
    	this.name = name;
    	this.id = id;
    }
    
	public void addConnectible(Connectible connectible) {
		listeConnectible.add(connectible);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void addConnection(Connection connection) {
		listeConnection.add(connection);
	}
    public List<Connection> getListeConnections() {
        return listeConnection;
    }
    public List<Connectible> getListeConnectible() {
        return listeConnectible;
    }
    
    public void JSONconverter(JsonGenerator jsonGenerator) throws Exception, IFT287Exception{
        try {
            jsonGenerator.writeStartObject()
                    .write("id", id)
                    .write("name", name)
                    .writeStartArray("Connectible");


            for(Connectible connectible : listeConnectible){
                connectible.JSONconverter(jsonGenerator);
            }
                    jsonGenerator.writeEnd();   //Connectible

                    jsonGenerator.writeStartArray("Connection");
            for(Connection connection : listeConnection){
                connection.JSONconverter(jsonGenerator);
            }
                    jsonGenerator.writeEnd();   //Connection
            jsonGenerator.writeEnd();   //Flow
        }
        catch (Exception e) {
            java.lang.System.out.println(" " + e.toString());
        }
    }

	public void XMLConverter(Document document, Element flowElement) {
		try {
			flowElement.setAttribute("id", id+"");
			flowElement.setAttribute("name", name);

            Element connectibles = document.createElement("Connectible");
            for (Connectible connectible: listeConnectible) {
                Element connectibleElement = document.createElement(connectible.getConnectibleType().name());
                connectible.XMLConverter(document, connectibleElement);
                connectibles.appendChild(connectibleElement);
            }
            flowElement.appendChild(connectibles);

            Element connections = document.createElement("Connections");
            for(Connection connection: listeConnection) {
                Element connectionElement = document.createElement("Connection");
                connection.XMLConverter(document, connectionElement);
                connections.appendChild(connectionElement);
            }
            flowElement.appendChild(connections);

        } catch (Exception e){
            java.lang.System.out.println(" " + e.toString());
        }
		
	}
	

}