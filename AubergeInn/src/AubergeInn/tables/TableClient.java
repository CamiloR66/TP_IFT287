package AubergeInn.tables;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.TypedQuery;

import AubergeInn.Connexion;
import AubergeInn.modeles.Client;

public class TableClient {
     
	private TypedQuery<Client> statementExiste;
	/*private PreparedStatement stmtInfo;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtDelete;*/
    private Connexion cx;

    /**
     * Creation d'une instance. Précompilation d'énoncés SQL.
     */
    public TableClient(Connexion cx) throws SQLException
    {
        this.cx = cx;
        statementExiste = cx.getConnection().createQuery("select cl from TupleClient cl where cl.m_idClient = :idCl", Client.class);
        /*stmtInsert = cx.getConnection()
                .prepareStatement("insert into client (idclient, nom, prenom, age) " + "values (?,?,?,?)");
        stmtDelete = cx.getConnection()
                .prepareStatement("delete from client where idclient = ?");
        stmtInfo = cx.getConnection()
                .prepareStatement("select idclient, nom, prenom, age from client where idclient =?");*/
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Vérifie si un client existe.
     */
    public boolean exists(int idCl) throws SQLException
    {
    	statementExiste.setParameter("idCl", idCl);
        return !statementExiste.getResultList().isEmpty();
    }

    /**
     * Lecture d'un client.
     */
    public Client getClient(int id) throws SQLException
    {
    	statementExiste.setParameter("idCl", id);
        List<Client> clients = statementExiste.getResultList();
        if(!clients.isEmpty())
        {
            return clients.get(0);
        }
        else
        {
            return null;
        }
    }

    /**
     * Ajout d'un nouveau client.
     */
    public void add(Client client)
    {
        /* Ajout du client. */
    	cx.getConnection().persist(client);
    }

    /**
     * Suppression d'un client.
     */
    public int delete(Client client) throws SQLException
    {
    	if(client != null)
        {
            /* Suppression du client. */
            cx.getConnection().remove(client);
            return 0;
        }
        return 1;
    }

    /**
     * Affichage des informations d'un client.
     * @return 
     */
    public List<Client> afficherClient(int idCl) throws SQLException
    {
    	statementExiste.setParameter("idCl", idCl);
         List<Client> clients = statementExiste.getResultList();
         return clients;
    }

}
