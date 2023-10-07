package AubergeInn.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import AubergeInn.Connexion;

public class TableCommodite {

	private final PreparedStatement statementExiste;
    private final PreparedStatement statementInsert;
    private final PreparedStatement statementDelete;

    private final Connexion cx;

    /**
     * Creation d'une instance. Des énoncés SQL pour chaque requête sont
     * précompilés.
     */
    public TableCommodite(Connexion cx) throws SQLException
    {
        this.cx = cx;
        statementExiste = cx.getConnection()
                .prepareStatement("select idcommodite, description, surplusprix from commodite where idcommodite = ?");
        statementInsert = cx.getConnection()
                .prepareStatement("insert into commodite (idcommodite, description, surplusprix) " + "values (?,?,?)");
        statementDelete = cx.getConnection()
                .prepareStatement("delete from commodite where idcommodite = ?");
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Vérifie si une commodite existe.
     */
    public boolean existe(int idcommodite) throws SQLException
    {
        statementExiste.setInt(1, idcommodite);
        ResultSet rset = statementExiste.executeQuery();
        boolean commoditeExiste = rset.next();
        rset.close();
        return commoditeExiste;
    }

    /**
     * Ajout d'une nouvelle commodite dans la base de données.
     */
    public void add(int idcommodite, String description, float surplusprix) throws SQLException
    {
        statementInsert.setInt(1, idcommodite);
        statementInsert.setString(2, description);
        statementInsert.setFloat(3, surplusprix);
        statementInsert.executeUpdate();
    }

    /**
     * Cette commande enlève une commodité de la base de données.
     * Cette commande n'est pas utilisé dans ce devoir (TP2).
     */
    public int delete(int idcommodite) throws SQLException
    {
        statementDelete.setInt(1, idcommodite);
        return statementDelete.executeUpdate();
    }


}
