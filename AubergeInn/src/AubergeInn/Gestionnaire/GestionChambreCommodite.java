/*package AubergeInn.Gestionnaire;

import java.sql.SQLException;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.modeles.ChambreCommodite;
import AubergeInn.tables.TableChambre;
import AubergeInn.tables.TableChambreCommodite;
import AubergeInn.tables.TableCommodite;

public class GestionChambreCommodite {

	private TableChambreCommodite tableChambreCommodite;
    private TableChambre tableChambre;
    private TableCommodite tableCommodite;
    private Connexion cx;

    /**
     * Creation d'une instance. La connection de l'instance de chambre et de
     * client doit être la même que cx, afin d'assurer l'intégrité des
     * transactions.
     **/
/*    public GestionChambreCommodite(TableChambreCommodite tableChambreCommodite, TableChambre tableChambre, TableCommodite tableCommodite) throws IFT287Exception
    {
        if (tableChambreCommodite.getConnexion() != tableChambre.getConnexion() || tableChambreCommodite.getConnexion() != tableCommodite.getConnexion())
            throw new IFT287Exception(
                    "Les instances de commoditeChambre, de chambre et de commodites n'utilisent pas la même connexion au serveur");
        this.cx = tableChambreCommodite.getConnexion();
        this.tableChambreCommodite = tableChambreCommodite;
        this.tableChambre = tableChambre;
        this.tableCommodite = tableCommodite;
    }
    public void add(int idCh, int idCo)
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si la commodite existe déja
            if (tableChambreCommodite.exists(idCh, idCo))
                throw new IFT287Exception("Commodite-Chambre existe déjà: " + idCh + " " + idCo);

            // Ajout de la commodite-chambre
            tableChambreCommodite.add(idCh, idCo);

            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
    public void delete(int idCo, int idCh) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Validation
            ChambreCommodite chambreCommodite = tableChambreCommodite.getChambreCommodite(idCo, idCh);
            if (chambreCommodite == null)
                throw new IFT287Exception("CommoditeChambre inexistante: " + idCo + " " + idCh);

            // Effachage d'une commodite-chambre.
            int nb = tableChambreCommodite.delete(idCo, idCh);
            if (nb == 0)
                throw new IFT287Exception("Commodite-Chambre " + idCh + " " + idCo + " inexistante");

            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
}*/
