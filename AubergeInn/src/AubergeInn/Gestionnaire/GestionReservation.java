package AubergeInn.Gestionnaire;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.modeles.Chambre;
import AubergeInn.modeles.Client;
import AubergeInn.modeles.Commodite;
import AubergeInn.modeles.Reservation;
import AubergeInn.tables.TableChambre;
import AubergeInn.tables.TableClient;
import AubergeInn.tables.TableReservation;

public class GestionReservation {

	private TableChambre tableChambre;
	private TableReservation tableReservation;
	private TableClient tableClient;
	private Connexion cx;


	public GestionReservation(TableReservation tableReservation, TableChambre tableChambre, TableClient tableClient) throws IFT287Exception {
		if (tableChambre.getConnexion() != tableChambre.getConnexion() || tableReservation.getConnexion() != tableChambre.getConnexion())
			throw new IFT287Exception(
					"Les instances de chambre, de client et de reservation n'utilisent pas la meme connexion au serveur");
		this.cx = tableChambre.getConnexion();
		this.tableChambre = tableChambre;
		this.tableReservation = tableReservation;
		this.tableClient = tableClient;
	}
	public void book(int idClient, int idChambre, Date dateDebut, Date dateFin)
			throws SQLException, IFT287Exception, Exception
	{
        try
        {
            cx.startTransaction();

            // Verif que la chambre est louée
            Chambre chambre = tableChambre.getChambre(idChambre);
            if (chambre == null)
                throw new IFT287Exception("Chambre inexistante: " + idChambre);

            // Vérifier que le client existe
            Client client = tableClient.getClient(idClient);
            if (client == null)
                throw new IFT287Exception("Client inexistant: " + idClient);

            // Vérifier que la réservation existe
            if (tableReservation.existe(idClient, idChambre, dateDebut))
                throw new IFT287Exception("Réservation idCl: " + idClient + ", idCh: " + idChambre + ", dateDebut: " + dateDebut + " existe deja");

            // Vérifier que la date est de fin n'est pas avant la date de debut et vice-versa
            if (dateDebut.after(dateFin))
                throw new IFT287Exception("Date debut apres la date de fin");
            if (dateFin.before(dateDebut))
                throw new IFT287Exception("Date fin avant la date de debut");

            // Vérifier que la date est dans le passe
            Date dateNow = new Date(System.currentTimeMillis());
            if (dateFin.before(dateNow)){
                throw new IFT287Exception("La date de fin de la réservation est déjà passé");
            }

            // Creation de la reservation
            Reservation reservation = new Reservation(idClient, idChambre, dateDebut, dateFin);
            tableReservation.book(reservation);

            Reservation reservation1 = tableReservation.getReservation(idClient, idChambre, dateDebut, dateFin);
            chambre.add(reservation1);

            client.add(reservation1);

            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
	public void information(int idCl) throws SQLException, IFT287Exception, Exception {
		{
			cx.startTransaction();

			List<Reservation> reservationList = tableReservation.information(idCl);
			if (reservationList.size() == 0) {
				cx.commit();

				throw new IFT287Exception("Le client #" + idCl + " n'a pas de réservation dans le système.");
			}

			for(Reservation reservation : reservationList)
			{


				System.out.print("IdClient: " + reservation.getIdClient() + " ");
				System.out.print("IdChambre: " + reservation.getIdChambre() + " ");
				System.out.print("DateDebut: " + reservation.getDateDebut() + " ");
				System.out.print("DateFin: " + reservation.getDateFin() + " ");

				Chambre chambre = tableChambre.getChambre(reservation.getIdChambre());
				float prixChambre = chambre.getPrix();
				int prixCommodite = 0;
				float prixChambreETCommodites = 0;
				List<Commodite> tupleCommoditeList = chambre.getCommodite();
				for (Commodite commodite : tupleCommoditeList) {
					prixCommodite += commodite.getSurplus();
				}
				prixChambreETCommodites = prixChambre + prixCommodite;
				System.out.println("Prix de la chambre et de ses commodités incluses: " + prixChambreETCommodites + "$");

			}
			cx.commit();
		}
	}
}
