package tp1;

import java.util.ArrayList;
import java.util.List;

public class Flow
{
    List<Connectible> listeConnectible=new ArrayList<Connectible>();
    List<Connection> listeConnections=new ArrayList<Connection>();
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
		listeConnections.add(connection);
	}
    public List<Connection> getListeConnections() {
        return listeConnections;
    }
    public List<Connectible> getListeConnectible() {
        return listeConnectible;
    }
	

}