package AubergeInn.Gestionnaire;

import java.sql.SQLException;
import java.util.List;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;

import org.bson.Document;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.modeles.Client;


public class GestionClient {

	private Connexion cx;
    private MongoCollection<Document> collectionClient;
	
	public GestionClient(Connexion cx) throws IFT287Exception
    {
        try {
            this.cx = cx;
            collectionClient = cx.getMongoDatabase().getCollection("Clients");
        } catch (Exception e) {
            throw new IFT287Exception("Impossible d'ouvrir la collection de clients");
        }

        collectionClient = cx.getMongoDatabase().getCollection("Clients");
    }

    public Connexion getConnexion() {
        return cx;
    }
	
    public boolean existe(int idClient) throws Exception
    {
        Client client = null;
        try {
            client = getClient(idClient);
            return client != null;
        } catch (Exception e) {
            return false;
        }
    }

    public Client getClient(int idClient) throws Exception
    {
        Client client = null;
        Document doc = collectionClient.find(eq("idClient", idClient)).first();
        if (doc != null) {
            client = new Client(doc);
            return client;
        }
        return null;
    }


	public boolean add(Client client) throws Exception {

        if (existe(client.getIdClient())) {
            return false;
        }
        else
        {
            try {
                collectionClient.insertOne(client.toDocument());
                return true;
            } catch (Exception e) {
                return false;
            }
        }   
		
	}
	public boolean delete(Client client) throws Exception
    {
        if(!existe(client.getIdClient()))
        {
            return false;
        }
        else
        {
            try {
                collectionClient.deleteOne(eq("idClient", client.getIdClient()));
                return true;
            } catch (Exception e) {
                return false;  
            }
        }
        
        
    }

    public boolean update(Client client) throws Exception
    {
        if(!existe(client.getIdClient()))
        {
            return false;
        }
        else
        {
            try {
                collectionClient.updateOne(eq("idClient", client.getIdClient()), client.toDocument());
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

}
