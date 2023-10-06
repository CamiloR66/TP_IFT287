package AubergeInn.tables;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AubergeInn.Connexion;
import AubergeInn.modeles.Reservation;

public class TableReservation {

	private PreparedStatement statementExiste;
	private PreparedStatement statementExisteChambre;
	private PreparedStatement statementExisteClient;
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
	private PreparedStatement statementVerificationDate4;
	private Connexion cx;

	/**
	 * Creation d'une instance.
	 */
	public TableReservation(Connexion cx) throws SQLException
	{
		this.cx = cx;
		statementExiste = cx.getConnection()
				.prepareStatement("select idclient, idchambre, debut, fin "+ "from reservation where idclient = ? AND idchambre = ? AND debut = ? ");
		statementExisteChambre = cx.getConnection()
				.prepareStatement("select idclient, idchambre, debut, fin "+ "from reservation where idchambre = ?" + "order by dateReservation");
		statementExisteClient = cx.getConnection()
				.prepareStatement("select idclient, idchambre, debut, fin " + "from reservation where idclient = ? ");
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
		statementExiste.setInt(1, idclient);
		statementExiste.setInt(2, idchambre);
		statementExiste.setDate(3, dateDebut);
		ResultSet rset = statementExiste.executeQuery();
		boolean reservationExiste = rset.next();
		rset.close();
		return reservationExiste;
	}

	/**
	 * Lecture d'une reservation.
	 */
	public Reservation getReservation(int idclient, int idchambre, Date dateDebut) throws SQLException
	{
		statementExiste.setInt(1, idclient);
		statementExiste.setInt(2, idchambre);
		statementExiste.setDate(3, dateDebut);
		ResultSet rset = statementExiste.executeQuery();
		if (rset.next())
		{
			Reservation Reservation = new Reservation();
			Reservation.setIdClient(rset.getInt(1));
			Reservation.setIdChambre(rset.getInt(2));
			Reservation.setDateDebut(rset.getDate(3));
			Reservation.setDateFin(rset.getDate(4));
			return Reservation;
		}
		else
		{
			return null;
		}
	}

	/**
	 * La première vérification de date pour une réservation essaie de trouver un cas où une réservation qui
	 * existe déjà dans le système commence après et termine avant la nouvelle réservation.
	 * Code: (dateDebut > ? AND dateFin < ?)
	 * Ex: (15  >   13  AND     18  <   20)
	 */
	public Reservation getVerificationDate(int idchambre, Date dateDebut, Date dateFin) throws SQLException
	{
		statementVerificationDate1.setInt(1, idchambre);
		statementVerificationDate1.setDate(2,dateDebut);
		statementVerificationDate1.setDate(3, dateFin);
		statementVerificationDate2.setInt(1, idchambre);
		statementVerificationDate2.setDate(2,dateDebut);
		statementVerificationDate2.setDate(3, dateFin);
		statementVerificationDate3.setInt(1, idchambre);
		statementVerificationDate3.setDate(2,dateDebut);
		statementVerificationDate3.setDate(3, dateFin);
		statementVerificationDate4.setInt(1, idchambre);
		statementVerificationDate4.setDate(2,dateDebut);
		statementVerificationDate4.setDate(3, dateFin);
		Reservation reservation = null;
		
		ResultSet rset = statementVerificationDate1.executeQuery();
		if (!rset.next()){
			rset = statementVerificationDate2.executeQuery();
			if (!rset.next()){
				rset = statementVerificationDate3.executeQuery();
				if (!rset.next()){
					rset = statementVerificationDate4.executeQuery();
					if (!rset.next()){
						return reservation;
					}
				}
			}

		}
		reservation = new Reservation();
		reservation.setIdChambre(rset.getInt(1));
		reservation.setDateDebut(rset.getDate(2));
		reservation.setDateFin(rset.getDate(3));
		return reservation;
	}
	/**
	 * Réservation d'une chambre par un client.
	 */
	public void reserver(int idclient, int idchambre, Date dateDebut, Date dateFin) throws SQLException
	{
		statementInsert.setInt(1, idclient);
		statementInsert.setInt(2, idchambre);
		statementInsert.setDate(3, dateDebut);
		statementInsert.setDate(4, dateFin);
		statementInsert.executeUpdate();
	}

	/**
	 * Suppression d'une reservation.
	 * Commande pas utilisée (TP2).
	 */
	public int annulerRes(int idclient, int idchambre, Date dateDebut, Date dateFin) throws SQLException
	{
		statementDelete.setInt(1, idclient);
		statementDelete.setInt(2, idchambre);
		statementDelete.setDate(3, dateDebut);
		statementDelete.setDate(4, dateFin);
		return statementDelete.executeUpdate();
	}

	/**
	 * Lecture de la première reservation d'un client.
	 */
	public Reservation getReservationClient(int idclient) throws SQLException
	{
		statementExisteClient.setInt(1, idclient);
		ResultSet rset = statementExisteClient.executeQuery();
		if (rset.next())
		{
			Reservation reservation = new Reservation();
			reservation.setIdClient(rset.getInt(1));
			reservation.setIdChambre(rset.getInt(2));
			reservation.setDateDebut(rset.getDate(3));
			reservation.setDateFin(rset.getDate(4));
			return reservation;
		}
		else
		{
			return null;
		}
	}

	/**
	 * Lecture de toutes les reservations passées et présentes d'un client.
	 */
	public void book(int idClient, int idChambre, Date dateDebut, Date dateFin) throws SQLException
	{
		statementInsert.setInt(1, idClient);
		statementInsert.setInt(2, idChambre);
		statementInsert.setDate(3, dateDebut);
		statementInsert.setDate(4, dateFin);
		statementInsert.executeUpdate();
	}

	public void information(int idCl) throws SQLException{
		statementToutesChambreClient.setInt(1, idCl);
        ResultSet rset = statementToutesChambreClient.executeQuery();
        while (rset.next()) {

            int idChambre = rset.getInt(2);
            int prixCh = 0;
            int prixTotalCo = 0;

            Date dateNow = new Date(System.currentTimeMillis());
            Date dateDebut = rset.getDate(3);
            if (dateDebut.after(dateNow)){

            }
            else {

                System.out.print("Numero de chambre: " + rset.getInt(2) + " ");
                System.out.print("Date de debut: " + rset.getDate(3) + " ");
                System.out.print("Date de fin: " + rset.getDate(4) + " ");

                /**
                 * Calcul du prix total de la chambre
                 */
                statementPrixChambre.setInt(1, idChambre);
                ResultSet rset4 = statementPrixChambre.executeQuery();
                statementPrixCommoditeTotal.setInt(1, idChambre);
                ResultSet rset5 = statementPrixCommoditeTotal.executeQuery();

                while (rset4.next()) {
                    prixCh = rset4.getInt(1);
                }
                rset4.close();
                while (rset5.next()) {
                    prixTotalCo = rset5.getInt(1);
                }
                rset5.close();
                int prixTotalChambre = prixCh + prixTotalCo;
                System.out.println("PrixTotalChambre: " + prixTotalChambre);
            }
        }
        rset.close();
    }



}
