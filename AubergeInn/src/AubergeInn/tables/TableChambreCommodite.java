package AubergeInn.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import AubergeInn.Connexion;
import AubergeInn.modeles.ChambreCommodite;

public class TableChambreCommodite {
	private PreparedStatement statementExiste;
	private PreparedStatement statementInsert;
	private PreparedStatement statementDelete;
	private Connexion cx;

	/**
	 * Creation d'une instance.
	 */
	public TableChambreCommodite(Connexion cx) throws SQLException
	{
		this.cx = cx;
		statementExiste = cx.getConnection()
				.prepareStatement("select idchambre, idcommodite from chambrecommodite where idchambre = ? AND idcommodite = ?");
		statementInsert = cx.getConnection()
				.prepareStatement("insert into chambrecommodite (idchambre, idcommodite) "+ "values (?,?)");
		statementDelete = cx.getConnection()
				.prepareStatement("delete from chambrecommodite where idchambre = ? AND idcommodite = ?");
	}

	/**
	 * Retourner la connexion associée.
	 */
	public Connexion getConnexion()
	{
		return cx;
	}


	public boolean exists(int idchambre, int idcommodite) throws SQLException
	{
		statementExiste.setInt(1, idchambre);
		statementExiste.setInt(2, idcommodite);
		ResultSet rset = statementExiste.executeQuery();
		boolean chambreCommoditeExiste = rset.next();
		rset.close();
		return chambreCommoditeExiste;
	}

	
	public ChambreCommodite getChambreCommodite(int idchambre, int idcommodite) throws SQLException
	{
		statementExiste.setInt(1, idchambre);
		statementExiste.setInt(2, idcommodite);
		ResultSet rset = statementExiste.executeQuery();
		if (rset.next())
		{
			ChambreCommodite chambreCommodite = new ChambreCommodite();
			chambreCommodite.setIdChambre (rset.getInt(1));
			chambreCommodite.setIdCommodite(idcommodite);
			return chambreCommodite;
		}
		else
		{
			return null;
		}
	}
	
	public void add(int idchambre, int idcommodite) throws SQLException
	{
		
		statementInsert.setInt(1, idchambre);
		statementInsert.setInt(2, idcommodite);
		statementInsert.executeUpdate();
	}

	/**
	 * Enlever une commodite-chambre
	 */
	public int delete(int idchambre, int idcommodite) throws SQLException
	{
		statementDelete.setInt(1, idchambre);
		statementDelete.setInt(2, idcommodite);
		return statementDelete.executeUpdate();
	}

}
