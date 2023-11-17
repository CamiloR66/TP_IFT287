package AubergeInn.Gestionnaire;

import java.sql.SQLException;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.modeles.Commodite;
import AubergeInn.tables.TableChambre;
import AubergeInn.tables.TableCommodite;

public class GestionCommodite {

	private TableCommodite tableCommodite;
    private TableChambre tableChambre;

    private Connexion cx;

    public GestionCommodite(Connexion cx) throws IFT287Exception
    {
        this.cx = tableCommodite.getConnexion();
        if (tableCommodite.getConnexion() != tableChambre.getConnexion())
            throw new IFT287Exception("Les instances de commodite et de commoditeChambre n'utilisent pas la même connexion au serveur");
        this.tableCommodite = tableCommodite;
        this.tableChambre = tableChambre;
    }
    
    public void add(int id, String description, float surplus)
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
        	cx.startTransaction();
        	
        	Commodite commodite = new Commodite(id, description, surplus);
            // Vérifie si la commodite existe déja
            if (tableCommodite.exists(id)) {
                throw new IFT287Exception("Commodite existe déjà: " + id);
            }

            // Ajout de la commodite dans la table des commodites
            tableCommodite.add(commodite);

            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
}
