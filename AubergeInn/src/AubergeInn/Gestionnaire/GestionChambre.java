package AubergeInn.Gestionnaire;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.modeles.Chambre;
import AubergeInn.modeles.Commodite;
import AubergeInn.modeles.Reservation;
import AubergeInn.tables.TableChambre;
import AubergeInn.tables.TableCommodite;
import AubergeInn.tables.TableReservation;

public class GestionChambre {

	private TableChambre tableChambre;
	private TableReservation tableReservation;
	private TableCommodite tableCommodite;
	private Connexion cx;

	public GestionChambre(TableChambre tableChambre, TableReservation tableReservation, TableCommodite tableCommodite) throws IFT287Exception {
		this.cx = tableChambre.getConnexion();
		if (tableChambre.getConnexion() != tableReservation.getConnexion())
			throw new IFT287Exception("Les instances de chambre et de reservation n'utilisent pas la même connexion au serveur");
		this.tableChambre = tableChambre;
		this.tableReservation = tableReservation;
		this.tableCommodite = tableCommodite;
	}

	public void add(int id, String nom, String lit, float prix)
			throws SQLException, IFT287Exception, Exception {
		try {
			cx.startTransaction();
			Chambre chambre = new Chambre(id, nom, lit, prix);

			// Vérifie si la chambre existe déja
			if (tableChambre.getChambre(id)!=null)
				throw new IFT287Exception("Chambre existe déjà: " + id);

			// Ajout du chambre dans la table des chambres
			tableChambre.add(chambre);

			// Commit
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}
	public void delete(int IdChambre) throws SQLException, IFT287Exception, Exception {
		try {
			cx.startTransaction();

			// Vérifie si la chambre existe et son nombre de réservations en cours
			Chambre chambre = tableChambre.getChambre(IdChambre);
			if (chambre == null)
				throw new IFT287Exception("Chambre inexistante: " + IdChambre);

			// Suppression de la chambre
			int nb = tableChambre.delete(chambre);
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
		{
	        cx.startTransaction();

	        // Verifie si la chambre existe
	        Chambre tupleChambre = tableChambre.getChambre(IdChambre);
	        if (tupleChambre == null)
	            throw new IFT287Exception("Chambre inexistante: " + IdChambre);

	        List<Chambre> chambresList = tableChambre.information(IdChambre);

	        for(Chambre chambre : chambresList)
	        {
	            System.out.print("IdChambre: " + chambre.getId() + " ");
	            System.out.print("Nom de la chambre: " + chambre.getNom() + " ");
	            System.out.print("Type de lit: " + chambre.getlit() + " ");
	            System.out.println("Prix de la chambre: " + chambre.getPrix() + "$ ");
	            System.out.print("Commodite(s) incluse(s): ");
	            if(chambre.getCommodite().size() == 0){
	                System.out.print("Aucune");
	            }

	            for(int i = 0; i<chambre.getCommodite().size(); i++)
	            {
	                System.out.print(chambre.getCommodite().get(i).getId() + " ");
	                System.out.print("(" + chambre.getCommodite().get(i).getSurplus() + "$)      ");
	            }
	            System.out.println(" ");
	        }
	        cx.commit();
	    }
	}
	public void showAvailable(Date debut, Date fin) throws IFT287Exception, Exception {
		cx.startTransaction();

		List<Chambre> tupleChambreList = tableChambre.getAvailableRooms();

		for (Chambre chambre : tupleChambreList) {
			List<Reservation> reservationList = chambre.getM_reservations();
			for (Reservation reservation : reservationList) {
				if(reservation.getDateDebut().after(fin)){

					//S'il y a une réservation qui commence avant la fin de la nouvelle réservation, il y a conflit

				}
				else if(reservation.getDateFin().before(debut)){
					//S'il y a une réservation qui fini aprés le début de la nouvelle réservation, il y a conflit
				}
				else{
					float prixChambre = chambre.getPrix();
					float prixCommodite = 0;
					float prixChambreETCommodites = 0;
					List<Commodite> commoditeList = chambre.getCommodite();
					for (Commodite commodite : commoditeList) {
						prixCommodite += commodite.getSurplus();
					}
					prixChambreETCommodites = prixChambre + prixCommodite;
					System.out.print("idCh: " + chambre.getId() + ", ");
					System.out.println("Prix de la chambre et de ces commodités incluses: " + prixChambreETCommodites + "$");
				}
			}
			if(chambre.getM_reservations().size() == 0){
				float prixChambre = chambre.getPrix();
				float prixCommodite = 0;
				float prixChambreETCommodites = 0;
				List<Commodite> commoditeList = chambre.getCommodite();
				for (Commodite commodite : commoditeList) {
					prixCommodite += commodite.getSurplus();
				}
				prixChambreETCommodites = prixChambre + prixCommodite;
				System.out.print("idCh: " + chambre.getId() + ", ");
				System.out.println("Prix de la chambre et de ces commodités incluses: " + prixChambreETCommodites + "$");
			}
		}
		cx.commit();
	}
	public void include(int idChambre, int idCommodite) throws IFT287Exception, Exception{
		try {
			cx.startTransaction();

			Chambre chambre = tableChambre.getChambre(idChambre);
			if (chambre == null)
				throw new IFT287Exception("Chambre inexistante: " + idChambre);
			Commodite commodite = tableCommodite.getCommodite(idCommodite);
			if (commodite == null)
				throw new IFT287Exception("Commodite inexistante: " + idCommodite);

			List<Commodite> commoditeList = chambre.getCommodite();
			for(Commodite CO : commoditeList){
				if(CO.getId() == idCommodite){
					throw new IFT287Exception("Cette chambre contient déjà cette commodité");
				}
			}

			chambre.include(commodite);
			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}
	public void remove(int idChambre, int idCommodite) throws IFT287Exception, Exception {
		try {
			cx.startTransaction();

			Chambre chambre = tableChambre.getChambre(idChambre);
			if (chambre == null)
				throw new IFT287Exception("Chambre inexistante: " + idChambre);
			Commodite commodite = tableCommodite.getCommodite(idCommodite);
			if (commodite == null)
				throw new IFT287Exception("Commodite inexistante: " + idCommodite);
			// Vérifier si la commodité est dans la chambre
			boolean flag = false;
			List<Commodite> commoditeList = chambre.getCommodite();
			for(Commodite CO : commoditeList){
				if(CO.getId() == idCommodite){
					flag = true;
				}
			}
			if(flag == false){
				throw new IFT287Exception("La commodite " + idCommodite + " n'est pas incluse dans la chambre " + idChambre);
			}

			chambre.remove(commodite);
			//commodite.enleverChambre(chambre);

			cx.commit();
		} catch (Exception e) {
			cx.rollback();
			throw e;
		}
	}
}
