package AubergeInn.Gestionnaire;

import java.sql.SQLException;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.TableChambreCommodite;
import AubergeInn.tables.TableCommodite;

public class GestionCommodite {

	private TableCommodite tableCommodite;

	private Connexion cx;

	public GestionCommodite(TableCommodite tableCommodite, TableChambreCommodite tableChambreCommodite) throws IFT287Exception
	{
		this.cx = tableCommodite.getConnexion();
		if (tableCommodite.getConnexion() != tableChambreCommodite.getConnexion())
			throw new IFT287Exception("Les instances de commodite et de commoditeChambre n'utilisent pas la même connexion au serveur");
		this.tableCommodite = tableCommodite;
	}

	public void add(int id, String description, float surplus)
			throws SQLException, IFT287Exception, Exception
	{
		try
		{
			// Vérifie si la commodite existe déja
			if (tableCommodite.existe(id))
				throw new IFT287Exception("Commodite existe déjà: " + id);

			// Ajout de la commodite dans la table des commodites
			tableCommodite.add(id, description, surplus);

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
