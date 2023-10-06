package AubergeInn.Gestionnaire;

import java.sql.SQLException;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.modeles.Client;
import AubergeInn.modeles.Reservation;
import AubergeInn.tables.TableClient;
import AubergeInn.tables.TableReservation;

public class GestionClient {

	private Connexion cx;
    private TableClient tableClient;
    private TableReservation tableReservation;
	
	public GestionClient(TableClient tableClient, TableReservation tableReservation) throws IFT287Exception
    {
        this.cx = tableClient.getConnexion();
        if (tableClient.getConnexion() != tableReservation.getConnexion())
            throw new IFT287Exception("Les instances de TableCLients et de TableReservations n'utilisent pas la même connexion au serveur");
        this.tableClient = tableClient;
        this.tableReservation = tableReservation;
    }
	
	public void add(int id, String nom, String prenom, int age) throws SQLException, IFT287Exception, Exception{
		
		try
        {
            // Vérifie si le client existe déja
            if (tableClient.getClient(id)!=null) {
            	throw new IFT287Exception("Client existe déjà: " + id);
            }
            // Ajout du client.
            tableClient.add(id, nom, prenom, age);

            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
	}
	public void delete(int id) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si le client existe et son nombre de réservations en cours
            Client client = tableClient.getClient(id);
            if (client == null)
                throw new IFT287Exception("Client inexistant: " + id);
            int nb = tableClient.delete(id);
            if (nb == 0)
                throw new IFT287Exception("Client " + id + " inexistant");

            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
}
