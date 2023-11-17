package AubergeInn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.client.MongoClients;
import com.mongodb.Mongo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.objectdb.o.CLN.p;

/**
 * Gestionnaire d'une connexion avec une BD relationnelle via JDBC.<br>
 * <br>
 * 
 * Cette classe ouvre une connexion avec une BD via JDBC.<br>
 * La méthode serveursSupportes() indique les serveurs supportés.<br>
 * 
 * <pre>
 * Pré-condition
 *   Le driver JDBC approprié doit être accessible.
 * 
 * Post-condition
 *   La connexion est ouverte en mode autocommit false et sérialisable, 
 *   (s'il est supporté par le serveur).
 * </pre>
 * 
 * <br>
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * @author Vincent Ducharme - Université de Sherbrooke
 * @version Version 3.0 - 21 mai 2016
 *
 * @author Kerson Boisrond - Université de Sherbrooke
 * @version Version 3.1 - 17 Septembre 2023
 *
 */
public class Connexion {
	private final MongoClient mongoClient;
	private final MongoDatabase mongoDatabase;

	private EntityManager em;
	private EntityManagerFactory emf;

	/**
	 * Ouverture d'une connexion en mode autocommit false et sérialisable (si
	 * supporté)
	 * 
	 * @param serveur Le type de serveur SQL à utiliser (Valeur : local, dinf).
	 * @param bd      Le nom de la base de données sur le serveur.
	 * @param user    Le nom d'utilisateur à utiliser pour se connecter à la base de
	 *                données.
	 * @param pass    Le mot de passe associé à l'utilisateur.
	 */
	public Connexion(String serveur, String bd, String user, String pass) throws IFT287Exception, SQLException {

		if(serveur.equals("local")) 
		{
			mongoClient = MongoClients.create();	
		}
		else if (serveur.equals("dinf")) 
		{
			mongoClient = MongoClients.create("mongodb://"+user+":"+pass+"@bd-info2.dinf.usherbrooke.ca:27017/"+bd+"?ssl=true");
		}
		else 
		{
			throw new IFT287Exception("Serveur inconnu");
		}
		mongoDatabase = mongoClient.getDatabase(bd);
		
		System.out.println("Connexion ouverte");
		System.out.println("BD: " + bd +", user "+ user) ;
	
	}

	/**
	 * Fermeture d'une connexion
	 */
	public void fermer() {
		mongoClient.close();
		System.out.println("Connexion fermée");
	}

	public MongoDatabase getMongoDatabase() {
		return mongoDatabase;
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}	

	

	/*public void setIsolationReadCommited() throws SQLException {
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	}*/

	/**
	 * Rollback
	//  */
	// public void rollback() throws SQLException {
	// 	em.getTransaction().rollback();
	// }

	/**
	 * Retourne la Connection ObjectDB
	 */
	// public EntityManager getConnection() {
	// 	return em;
	// }

	// /*public void setAutoCommit(boolean m) throws SQLException {
	// 	conn.setAutoCommit(false);
	// }*/

	// /**
	//  * Retourne la liste des serveurs supportés par ce gestionnaire de connexions
	//  */
	// public static String serveursSupportes() {
	// 	return "local : PostgreSQL installé localement\n"
	// 			+ "dinf  : PostgreSQL installé sur les serveurs du département\n";
	// }
	// public void startTransaction() {
	// 	em.getTransaction().begin(); 
	// }
}