package AubergeInn.tables;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import AubergeInn.Connexion;
import AubergeInn.modeles.Chambre;

public class TableChambre {

	private final PreparedStatement statementExists;
	private final PreparedStatement statementInsert;
	private final PreparedStatement statementDelete;
	private final PreparedStatement statementDescCommodite;
	private final PreparedStatement statementAvailableRooms;
	private final Connexion cx;


	public TableChambre(Connexion cx) throws SQLException {
		this.cx = cx;
		statementExists = cx.getConnection()
				.prepareStatement("select idchambre, nomchambre, typelit, prixbase from chambre where idchambre = ?");
		statementInsert = cx.getConnection()
				.prepareStatement("insert into chambre (idchambre, nomchambre, typelit, prixbase) "+ "values (?,?,?,?)");
		statementDelete = cx.getConnection()
				.prepareStatement("delete from chambre where idchambre = ?");
		statementDescCommodite = cx.getConnection()
				.prepareStatement("select description, surplusprix" + " from chambrecommodite coch join commodite co on coch.idcommodite = co.idcommodite where idchambre = ? ");
		statementAvailableRooms = cx.getConnection()
				.prepareStatement("select c.idchambre, c.nomchambre, c.typelit, (c.prixbase + coalesce(sum(co.surplusprix), 0)) as totalprix "
						+ "from chambre c "
						+ "left join chambrecommodite cc on c.idchambre = cc.idchambre "
						+ "left join commodite co on cc.idcommodite = co.idcommodite "
						+ "where not exists ("
						+ "select 1 "
						+ "from reservation r "
						+ "where r.idchambre = c.idchambre "
						+ "and ((r.debut <= ? and r.fin > ?) or "
						+ "(r.debut < ? and r.fin >= ?))) "
						+ "group by c.idchambre, c.nomchambre, c.typelit, c.prixbase;\n");
	}

	public Chambre getChambre(int id) throws SQLException {
		statementExists.setInt(1, id);
		ResultSet result = statementExists.executeQuery();
		if (result.next()) {
			Chambre chambre = new Chambre();
			chambre.setId(id);
			chambre.setNom(result.getString(2));
			chambre.setLit(result.getString(3));
			chambre.setPrix(result.getInt(4));
			result.close();
			return chambre;
		} else
			return null;
	}
	public void add(int id, String nom, String lit, float prix) throws SQLException {
		statementInsert.setInt(1, id);
		statementInsert.setString(2, nom);
		statementInsert.setString(3, lit);
		statementInsert.setFloat(4, prix);
		statementInsert.executeUpdate();

	}
	public int delete(int id) throws SQLException {
		statementDelete.setInt(1, id);
		return statementDelete.executeUpdate();
	}
	public void showAvailable(Date debut, Date fin) throws SQLException {

		statementAvailableRooms.setDate(1, debut);
		statementAvailableRooms.setDate(2, debut);
		statementAvailableRooms.setDate(3, fin);
		statementAvailableRooms.setDate(4, fin);

		ResultSet rset = statementAvailableRooms.executeQuery();



		while (rset.next()){
			System.out.print("ID chambre: " + rset.getInt(1) + " ");
			System.out.print("Nom chambre: " + rset.getString(2) + " ");
			System.out.print("Type lit: " + rset.getString(3) + " ");
			System.out.print("Prix: " + rset.getFloat(4) + "\n");

		}
		rset.close();
	}
	public void information(int idCh) throws SQLException {
		statementExists.setInt(1, idCh);
		ResultSet rset = statementExists.executeQuery();

		if (rset.next()) {
			System.out.print("IdChambre: " + rset.getString(1));
			System.out.print("Nom: " + rset.getString(2));
			System.out.print("Type: " + rset.getString(3));
			System.out.print("Prix avant commodite(s): " + rset.getString(4) + "$");
			rset.close();
		}

		statementDescCommodite.setInt(1, idCh);
		ResultSet rset1 = statementDescCommodite.executeQuery();
		if(rset1.next()) {
			System.out.print("\nCommodites offertes:\n");
			System.out.print("\t"+"\t"+rset1.getString(1) + "(" + rset1.getInt(2) + "$)\n");
			while (rset1.next()) {
				System.out.print("\t"+"\t"+rset1.getString(1) + "(" + rset1.getInt(2) + "$) ");
			}
		}
		rset1.close();
		System.out.println();
	}


	public Connexion getConnexion() {
		// TODO Auto-generated method stub
		return cx;
	}
}