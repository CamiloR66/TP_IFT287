package AubergeInn.tables;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import AubergeInn.Connexion;
import AubergeInn.modeles.Reservation;

public class TableReservation {
	
	private TypedQuery<Reservation> statementClientExists;
	private TypedQuery<Reservation> statementExists;
	/*private PreparedStatement statementExisteChambre;
	private PreparedStatement statementInsert;
	private PreparedStatement statementDelete;
	private PreparedStatement statementPrixChambre;
	private PreparedStatement statementIdCommodite;
	private PreparedStatement statementPrixCommodite;
	private PreparedStatement statementPrixChambreTotal;
	private PreparedStatement statementToutesChambreClient;
	private PreparedStatement statementPrixCommoditeTotal;
	private PreparedStatement statementVerificationDate1;
	private PreparedStatement statementVerificationDate2;
	private PreparedStatement statementVerificationDate3;
	private PreparedStatement statementVerificationDate4;*/
	private Connexion cx;

	/**
	 * Creation d'une instance.
	 */
	public TableReservation(Connexion cx) throws SQLException
	{
		this.cx = cx;
		statementClientExists = cx.getConnection().createQuery("select r from TupleReservation r where r.m_idCl = :idCl", Reservation.class);
		statementExists = cx.getConnection().createQuery("select idCl from TupleReservation idCl where idCl.m_idCl = :idCl", Reservation.class);
		/*statementExisteChambre = cx.getConnection()
				.prepareStatement("select idclient, idchambre, debut, fin "+ "from reservation where idchambre = ?" + "order by dateReservation");
		
		statementToutesChambreClient = cx.getConnection()
				.prepareStatement("select r.idclient, r.idchambre, r.debut, r.fin from reservation r where r.idclient = ? ");
		statementInsert = cx.getConnection()
				.prepareStatement("insert into reservation (idclient, idchambre, debut, fin) " + "values (?,?,?,?)");
		statementDelete = cx.getConnection()
				.prepareStatement("delete from reservation where idclient = ?" + "delete from reservation where idchambre = ?" + "from reservation where debut = ?");
		statementPrixCommoditeTotal = cx.getConnection()
				.prepareStatement("select SUM(co.surplusprix) as prixCoTotal from chambre ch natural join chambrecommodite natural join commodite co where idchambre = ? group by ch.idchambre");
		statementPrixChambre = cx.getConnection()
				.prepareStatement("select prixbase " + " from chambre where idchambre = ? ");
		statementIdCommodite = cx.getConnection()
				.prepareStatement("select idCo " + " from chambrecommodite where idchambre = ? ");
		statementPrixCommodite = cx.getConnection()
				.prepareStatement("select surplusprix " + " from commodite where idCo = ? ");
		statementVerificationDate1 = cx.getConnection()
				.prepareStatement("select idchambre, debut, fin from reservation where idchambre = ? AND debut > ? AND fin < ?  ");
		statementVerificationDate2 = cx.getConnection()
				.prepareStatement("select idchambre, debut, fin from reservation where idchambre = ? AND debut > ? AND debut < ?  ");
		statementVerificationDate3 = cx.getConnection()
				.prepareStatement("select idchambre, debut, fin from reservation where idchambre = ? AND fin > ? AND fin < ?  ");
		statementVerificationDate4 = cx.getConnection()
				.prepareStatement("select idchambre, debut, fin from reservation where idchambre = ? AND debut < ? AND fin > ?  ");
	*/
	}

	/**
	 * Retourner la connexion associée.
	 */
	public Connexion getConnexion()
	{
		return cx;
	}

	/**
	 * Verifie si une reservation existe.
	 */
	public boolean existe(int idclient, int idchambre, java.sql.Date dateDebut) throws SQLException
	{
		statementExists.setParameter("idCl", idclient);
		statementExists.setParameter("idCh", idchambre);
		statementExists.setParameter("dateDebut", dateDebut);
        return !statementExists.getResultList().isEmpty();
	}

	/**
	 * Lecture d'une reservation.
	 */
	public Reservation getReservation(int idClient, int idChambre, Date dateDebut, Date dateFin) throws SQLException
	{
		statementExists.setParameter("idCl", idClient);
		statementExists.setParameter("idCh", idChambre);
		statementExists.setParameter("dateDebut", dateDebut);
        List<Reservation> reservations = statementExists.getResultList();
        if (!reservations.isEmpty())
        {
            return reservations.get(0);
        }
        return null;
	}

	/**
	 * Réservation d'une chambre par un client.
	 */
	public void book(Reservation reservation) throws SQLException
	{
		{
	        cx.getConnection().persist(reservation);
	    }
	}

	/**
	 * Suppression d'une reservation.
	 * Commande pas utilisée (TP2).
	 */
	public int annulerRes(Reservation reservation) throws SQLException
	{
		if(reservation != null)
        {
            cx.getConnection().remove(reservation);
            return 1;
        }
        return 0;
	}

	/**
	 * Lecture de la première reservation d'un client.
	 */
	public List<Reservation> getReservationClient(int id) throws SQLException
	{
		statementClientExists.setParameter("idCl", id);
	        List<Reservation> reservationList = statementClientExists.getResultList();
	        return reservationList;
	}

	public List<Reservation> information(int idCl) throws SQLException{
		statementClientExists.setParameter("idCl", idCl);
        List<Reservation> reservationList = statementClientExists.getResultList();
        return reservationList;
    }



}
