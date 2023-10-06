package AubergeInn.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import AubergeInn.Connexion;
import AubergeInn.modeles.Client;

public class TableClient {
     
	private PreparedStatement stmtInfo;
    private PreparedStatement stmtExiste;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtDelete;
    private Connexion cx;

    /**
     * Creation d'une instance. Précompilation d'énoncés SQL.
     */
    public TableClient(Connexion cx) throws SQLException
    {
        this.cx = cx;
        stmtExiste = cx.getConnection()
                .prepareStatement("select idclient, nom, prenom, age from client where idclient = ?");
        stmtInsert = cx.getConnection()
                .prepareStatement("insert into client (idclient, nom, prenom, age) " + "values (?,?,?,?)");
        stmtDelete = cx.getConnection()
                .prepareStatement("delete from client where idclient = ?");
        stmtInfo = cx.getConnection()
                .prepareStatement("select idclient, nom, prenom, age from client where idclient =?");
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
    public boolean existe(int idCl) throws SQLException
    {
        stmtExiste.setInt(1, idCl);
        ResultSet rset = stmtExiste.executeQuery();
        boolean clientExiste = rset.next();
        rset.close();
        return clientExiste;
    }

    /**
     * Lecture d'un client.
     */
    public Client getClient(int id) throws SQLException
    {
        stmtExiste.setInt(1, id);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            Client Client = new Client();
            Client.setIdClient(id);
            Client.setNom(rset.getString(2));
            Client.setPrenom(rset.getString(3));
            Client.setAge(rset.getInt(4));
            rset.close();
            return Client;
        }
        else
        {
            return null;
        }
    }

    /**
     * Ajout d'un nouveau client.
     */
    public void add(int idCl, String nom, String prenom, int age) throws SQLException
    {
        /* Ajout du client. */
        stmtInsert.setInt(1, idCl);
        stmtInsert.setString(2, nom);
        stmtInsert.setString(3, prenom);
        stmtInsert.setInt(4, age);
        stmtInsert.executeUpdate();
    }

    /**
     * Suppression d'un client.
     */
    public int delete(int idCl) throws SQLException
    {
        stmtDelete.setInt(1, idCl);
        return stmtDelete.executeUpdate();
    }

    /**
     * Affichage des informations d'un client.
     */
    public void afficherClient(int idCl) throws SQLException
    {
        stmtExiste.setInt(1, idCl);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            System.out.print("IdClient: " + rset.getString(1) + " ");
            System.out.print("Nom: " + rset.getString(2) + " ");
            System.out.print("Prenom: " + rset.getString(3) + " ");
            System.out.println("Age: " + rset.getString(4) + " ");
            rset.close();
        }
    }

}
