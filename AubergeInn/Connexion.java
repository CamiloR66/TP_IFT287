package AubergeInn;

import java.sql.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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

	private EntityManagerFactory emf;
	private EntityManager em;
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
	@SuppressWarnings("deprecation")
	public Connexion(String serveur, String bd, String user, String pass) throws IFT287Exception, SQLException {
		Driver d;
		try {
			// d = (Driver) Class.forName("org.postgresql.Driver").newInstance();
			// DriverManager.registerDriver(d);

			if (serveur.equals("local")) {
				emf = Persistence.createEntityManagerFactory(bd);

			} else if (serveur.equals("dinf")) {

				Map<String, String> properties = new HashMap<String, String>();
				properties.put("javax.persistence.jdbc.user", user);
				properties.put("javax.persistence.jdbc.password", pass);
				emf = Persistence.createEntityManagerFactory("objectdb://bd-info2.dinf.usherbrooke.ca:5432/"+user+"/" + bd, properties);

			} else {
				throw new IFT287Exception("Serveur inconnu");
			}
		
		em = emf.createEntityManager();
		
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			throw new IFT287Exception(" Driver non instancié");
		}
	}

	/**
	 * Fermeture d'une connexion
	 */
	public void fermer() throws SQLException {
		em.close();
		System.out.println("Connexion fermée " + conn);
	}

	/**
	 * Commit
	 */
	public void commit() throws SQLException {
		em.getTransaction().commit();
	}

	public void startTransaction() throws SQLException {
		em.getTransaction().begin();
	}

	public void setIsolationReadCommited() throws SQLException {
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	}

	/**
	 * Rollback
	 */
	public void rollback() throws SQLException {
		em.getTransaction().rollback();
	}

	/**
	 * Retourne la Connection JDBC
	 */
	public EntityManager getConnection() {
		return em;
	}

	/**
	 * Retourne la liste des serveurs supportés par ce gestionnaire de connexions
	 */
	public static String serveursSupportes() {
		return "local : PostgreSQL installé localement\n"
				+ "dinf  : PostgreSQL installé sur les serveurs du département\n";
	}
}