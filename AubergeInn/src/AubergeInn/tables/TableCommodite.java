package AubergeInn.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.TypedQuery;

import AubergeInn.Connexion;
import AubergeInn.modeles.Commodite;

public class TableCommodite {

	private TypedQuery<Commodite> statementExists;
    /*private final PreparedStatement statementDelete;
    private final PreparedStatement statementInsert;*/

    private final Connexion cx;

    /**
     * Creation d'une instance. Des énoncés SQL pour chaque requête sont
     * précompilés.
     */
    public TableCommodite(Connexion cx) throws SQLException
    {
        this.cx = cx;
        statementExists = cx.getConnection().createQuery("select c from TupleCommodite c where c.m_idCo = :idCo", Commodite.class);
        /*statementInsert = cx.getConnection()
                .prepareStatement("insert into commodite (idcommodite, description, surplusprix) " + "values (?,?,?)");
        statementDelete = cx.getConnection()
                .prepareStatement("delete from commodite where idcommodite = ?");*/
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
    public boolean exists(int idCommodite) throws SQLException
    {
    	statementExists.setParameter("idCo", idCommodite);
        return !statementExists.getResultList().isEmpty();
    }

    /**
     * Ajout d'une nouvelle commodite dans la base de données.
     */
    public void add(Commodite commodite) throws SQLException
    {
    	cx.getConnection().persist(commodite);
    }

    /**
     * Cette commande enlève une commodité de la base de données.
     * Cette commande n'est pas utilisé dans ce devoir (TP2).
     */
    public int delete(Commodite commodite) throws SQLException
    {
    	if(commodite != null)
        {
            cx.getConnection().remove(commodite);
            return 1;
        }
        return 0;
    }
    public Commodite getCommodite(int idCo)
    {
    	statementExists.setParameter("idCo", idCo);
        List<Commodite> listCommodites = statementExists.getResultList();
        if (!listCommodites.isEmpty())
        {
            return listCommodites.get(0);
        }
        else
        {
            return null;
        }
    }


}
