package AubergeInn.tables;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.TypedQuery;

import AubergeInn.Connexion;
import AubergeInn.modeles.Chambre;

public class TableChambre {

	private TypedQuery<Chambre>  statementExists;
	private TypedQuery<Chambre> statementAvailableRooms;
	/*private final PreparedStatement statementInsert;
	private final PreparedStatement statementDelete;
	private final PreparedStatement statementDescCommodite;*/

	private final Connexion cx;


	public TableChambre(Connexion cx) throws SQLException {
		this.cx = cx;
		statementExists = cx.getConnection().createQuery("select ch from TupleChambre ch where ch.m_idCh = :idCh", Chambre.class);
		statementAvailableRooms = cx.getConnection().createQuery("select ch from TupleChambre ch", Chambre.class);
		/*statementExists = cx.getConnection()
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
						+ "group by c.idchambre, c.nomchambre, c.typelit, c.prixbase;\n");*/
	}

	public Chambre getChambre(int id) throws SQLException {
		statementExists.setParameter("idCh", id);
        List<Chambre> chambres = statementExists.getResultList();
        if(!chambres.isEmpty())
        {
            return chambres.get(0);
        }
        else
        {
            return null;
        }
	}
	public void add(Chambre chambre) throws SQLException {
		cx.getConnection().persist(chambre);
	}
	
	public int delete(Chambre chambre) throws SQLException {
		if (chambre == null)
		{
			return 0;
		}
		cx.getConnection().remove(chambre);
		return 1;
	}
	public List<Chambre> information(int idCh) throws SQLException {
		statementExists.setParameter("idCh", idCh);
        List<Chambre> chambres = statementExists.getResultList();
        return chambres;
	}
	public List<Chambre> getAvailableRooms()
	{

		/* Afficher les chambres libres */
		List<Chambre> chambres = statementAvailableRooms.getResultList();
		return chambres;
	}


	public Connexion getConnexion() {
		// TODO Auto-generated method stub
		return cx;
	}
}