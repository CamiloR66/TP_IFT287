package AubergeInn.Gestionnaire;

import static com.mongodb.client.model.Filters.eq;

import java.sql.SQLException;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.modeles.Client;
import AubergeInn.modeles.Commodite;

public class GestionCommodite {

	
    private Connexion cx;
    private MongoCollection<Document> collectionCommodite;

    public GestionCommodite(Connexion cx) throws IFT287Exception
    {
        try {
            this.cx = cx;
            collectionCommodite = cx.getMongoDatabase().getCollection("Commodites");
        } catch (Exception e) {
            throw new IFT287Exception("Impossible d'ouvrir la collection de clients");
        }

        collectionCommodite = cx.getMongoDatabase().getCollection("Commodites");
    }

    public Connexion getConnexion() {
        return cx;
    }
	
    public boolean existe(int idCommodite) throws Exception
    {
        Commodite commodite = null;
        try {
            commodite = getCommodite(idCommodite);
            return commodite != null;
        } catch (Exception e) {
            return false;
        }
    }

    public Commodite getCommodite(int idCommodite) throws Exception
    {
        Commodite commodite  = null;
        Document doc = collectionCommodite.find(eq("idCommodite", idCommodite)).first();
        if (doc != null) {
            commodite = new Commodite(doc);
            return commodite;
        }
        return null;
    }


	public boolean add(Commodite commodite) throws Exception {

        if (existe(commodite.getId())) {
            return false;
        }
        else
        {
            try {
                collectionCommodite.insertOne(commodite.toDoc());
                return true;
            } catch (Exception e) {
                return false;
            }
        }   
		
	}
	public boolean delete(Commodite commodite) throws Exception
    {
        if(!existe(commodite.getId()))
        {
            return false;
        }
        else
        {
            try {
                collectionCommodite.deleteOne(eq("idClient", commodite.getId()));
                return true;
            } catch (Exception e) {
                return false;  
            }
        }
        
        
    }

    public boolean update(Commodite commodite) throws Exception
    {
        if(!existe(commodite.getId()))
        {
            return false;
        }
        else
        {
            try {
                collectionCommodite.updateOne(eq("idClient", commodite.getId()), commodite.toDoc());
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
}
