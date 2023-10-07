package AubergeInn.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import AubergeInn.Connexion;
import AubergeInn.modeles.Client;

public class TableClient {
     
    private PreparedStatement statementExiste;
    private PreparedStatement statementInsert;
    private PreparedStatement statementDelete;
    private Connexion cx;

    /**
     * Creation d'une instance. Précompilation d'énoncés SQL.
     */
    public TableClient(Connexion cx) throws SQLException
    {
        this.cx = cx;
        statementExiste = cx.getConnection()
                .prepareStatement("select idclient, nom, prenom, age from client where idclient = ?");
        statementInsert = cx.getConnection()
                .prepareStatement("insert into client (idclient, nom, prenom, age) " + "values (?,?,?,?)");
        statementDelete = cx.getConnection()
                .prepareStatement("delete from client where idclient = ?");
        
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
        statementExiste.setInt(1, idCl);
        ResultSet rset = statementExiste.executeQuery();
        boolean clientExiste = rset.next();
        rset.close();
        return clientExiste;
    }

    /**
     * Lecture d'un client.
     */
    public Client getClient(int id) throws SQLException
    {
        statementExiste.setInt(1, id);
        ResultSet rset = statementExiste.executeQuery();
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
        statementInsert.setInt(1, idCl);
        statementInsert.setString(2, nom);
        statementInsert.setString(3, prenom);
        statementInsert.setInt(4, age);
        statementInsert.executeUpdate();
    }

    /**
     * Suppression d'un client.
     */
    public int delete(int idCl) throws SQLException
    {
        statementDelete.setInt(1, idCl);
        return statementDelete.executeUpdate();
    }

    /**
     * Affichage des informations d'un client.
     */
    public void afficherClient(int idCl) throws SQLException
    {
        statementExiste.setInt(1, idCl);
        ResultSet rset = statementExiste.executeQuery();
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
