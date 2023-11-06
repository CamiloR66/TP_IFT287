package AubergeInn.Gestionnaire;

import java.sql.SQLException;
import java.util.List;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.modeles.Client;
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
	
	public void add(int id, String nom, String prenom, int age) throws IFT287Exception, Exception{
		
		try
        {
			cx.startTransaction();
			
			
			Client client = new Client(id, nom, prenom, age);
			
            // Vérifie si le client existe déja
            if (tableClient.getClient(id)!=null) {
            	throw new IFT287Exception("Client existe déjà: " + id);
            }
            // Ajout du client.
            tableClient.add(client);

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
        	cx.startTransaction();
            // Vérifie si le client existe et son nombre de réservations en cours
            Client client = tableClient.getClient(id);
            if (client == null) {
            	throw new IFT287Exception("Client inexistant: " + id);
            }
            
            int nb = tableClient.delete(client);
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
	public void information(int idClient)throws IFT287Exception, Exception
    {

        cx.startTransaction();

        // Verifie si le client existe
        Client client = tableClient.getClient(idClient);
        if (client == null)
            throw new IFT287Exception("Client inexistant: " + idClient);

        List<Client> clientList = tableClient.afficherClient(idClient);

        for(Client clients : clientList) {
            System.out.print("IdClient: " + clients.getIdClient() + " ");
            System.out.print("Prenom: " + clients.getNom() + " ");
            System.out.print("Nom: " + clients.getPrenom() + " ");
            System.out.println("Age: " + clients.getAge() + " ");
        }

        cx.commit();
    }
}
