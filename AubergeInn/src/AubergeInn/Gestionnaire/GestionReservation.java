package AubergeInn.Gestionnaire;

import java.sql.Date;
import java.sql.SQLException;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.modeles.Chambre;
import AubergeInn.modeles.Client;
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
        try {
            // Vérifier que la chambre est louee
            Chambre chambre = tableChambre.getChambre(idChambre);
            if (chambre == null)
                throw new IFT287Exception("Chambre inexistante: " + idChambre);

            // Vérifier que le client existe
            Client client = tableClient.getClient(idClient);
            if (client == null)
                throw new IFT287Exception("Client inexistant: " + idClient);

            // Vérifier que la date est dans le passe
            Date dateNow = new Date(System.currentTimeMillis());
            if (dateFin.before(dateNow)){
                throw new IFT287Exception("La date de fin de la réservation est déjà passé");
            }

            // Vérifier que la date est de fin n'est pas avant la date de debut et vice-versa
            if (dateDebut.after(dateFin))
                throw new IFT287Exception("Date debut apres la date de fin");
            if (dateFin.before(dateDebut))
                throw new IFT287Exception("Date fin avant la date de debut");

            // Vérifier que la chambre n'est pas déjà réservé
            Reservation reservation = tableReservation.getReservation(idClient, idChambre, dateDebut);
            if (reservation != null)
                throw new IFT287Exception("Cette réservation existe déjà");
            
            
            Reservation validation = tableReservation.getVerificationDate(idChambre, dateDebut, dateFin);
            if (validation != null)
                throw new IFT287Exception("Cette chambre est déjà reservee pour ces dates");
           
            tableReservation.book(idClient, idChambre, dateDebut, dateFin);

            // Commit
            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
        
    }
	public void information(int idCl) throws SQLException, IFT287Exception, Exception {
        try
        {
            // Verifier que le client a au moins une reservation
            Reservation reservation = tableReservation.getReservationClient(idCl);
            if (reservation == null)
                throw new IFT287Exception("Le client " + idCl + " n'a pas de réservation");

            tableReservation.information(idCl);

        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }
}
