package AubergeInn.Gestionnaire;

import java.sql.Date;
import java.sql.SQLException;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.modeles.Chambre;
import AubergeInn.tables.TableChambre;
import AubergeInn.tables.TableCommodite;
import AubergeInn.tables.TableReservation;

public class GestionChambre {

	private TableChambre tableChambre;
    private TableReservation tableReservation;
    private TableCommodite tableCommoditeChambre;
    private Connexion cx;
    
    public GestionChambre(TableChambre chambre, TableReservation reservation, TableCommodite commoditeChambre) throws IFT287Exception {
        this.cx = chambre.getConnexion();
        if (chambre.getConnexion() != reservation.getConnexion())
            throw new IFT287Exception("Les instances de chambre et de reservation n'utilisent pas la même connexion au serveur");
        if (commoditeChambre.getConnexion() != reservation.getConnexion())
            throw new IFT287Exception("Les instances de commoditeChambres et de reservation n'utilisent pas la même connexion au serveur");
        if (chambre.getConnexion() != commoditeChambre.getConnexion())
            throw new IFT287Exception("Les instances de chambre et de commoditeChambres n'utilisent pas la même connexion au serveur");

        this.tableChambre = chambre;
        this.tableReservation = reservation;
        this.tableCommoditeChambre = commoditeChambre;
    }
    
    public void add(int id, String nom, String lit, float prix)
            throws SQLException, IFT287Exception, Exception {
        try {
            // Vérifie si la chambre existe déja
            if (tableChambre.getChambre(id)!=null)
                throw new IFT287Exception("Chambre existe déjà: " + id);

            // Ajout du chambre dans la table des chambres
            tableChambre.add(id, nom, lit, prix);

            // Commit
            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }
    public void delete(int IdChambre) throws SQLException, IFT287Exception, Exception {
        try {
            // Vérifie si la chambre existe et son nombre de réservations en cours
            Chambre chambre = tableChambre.getChambre(IdChambre);
            if (chambre == null)
                throw new IFT287Exception("Chambre inexistante: " + IdChambre);

            // Suppression de la chambre
            int nb = tableChambre.delete(IdChambre);
            if (nb == 0)
                throw new IFT287Exception("Chambre " + IdChambre + " inexistante");

            // Commit
            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }
    /*public void showAvailable(Date debut, Date fin) throws SQLException, IFT287Exception, Exception {
        try {
            tableChambre.showAvailable(Date debut, Date fgin);
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }*/
    public void information(int IdChambre) throws SQLException, IFT287Exception, Exception {
        try {
            // Verifie si la chambre existe
            Chambre chambre = tableChambre.getChambre(IdChambre);
            if (chambre == null)
                throw new IFT287Exception("Chambre inexistante: " + IdChambre);

            tableChambre.information(IdChambre);

        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }
    public void showAvailable(Date debut, Date fin) throws SQLException, IFT287Exception, Exception {
        try {
            tableChambre.showAvailable(debut, fin);
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }
}
