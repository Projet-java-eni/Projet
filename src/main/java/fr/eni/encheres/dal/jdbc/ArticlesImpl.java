package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.*;

public class ArticlesImpl implements ArticlesDAO {
	UtilisateursDAO utilisateursDAO = null;
	CategoriesDAO categoriesDAO = null;



	private static final String sqlSelectById = "SELECT no_article, nom_article,description," +
			"date_debut_encheres,heure_debut_encheres, date_fin_encheres,heure_fin_encheres,prix_initial,"
			+ "prix_vente,annule_par_vendeur, recu_par_acheteur, no_categorie, no_utilisateur " +
			" FROM dbo.ARTICLES_VENDUS WHERE no_article=?";

	private static final String sqlSelectAll = "SELECT no_article, nom_article,description," +
			"date_debut_encheres,heure_debut_encheres,date_fin_encheres,heure_fin_encheres,prix_initial,"
			+ "prix_vente,annule_par_vendeur, recu_par_acheteur,no_categorie,no_utilisateur  FROM dbo.ARTICLES_VENDUS";

	private static final String sqlSelectByCategorie = "SELECT no_article, nom_article,description," +
			"date_debut_encheres,heure_debut_encheres,date_fin_encheres,heure_fin_encheres,prix_initial, " +
			" prix_vente,annule_par_vendeur, recu_par_acheteur,no_categorie,no_utilisateur FROM dbo.ARTICLES_VENDUS WHERE no_categorie=?";

	private static final String sqlInsert = "INSERT INTO dbo.ARTICLES_VENDUS " +
			"(nom_article,description,date_debut_encheres,heure_debut_encheres, date_fin_encheres,heure_fin_encheres, prix_initial, "
			+ "prix_vente,annule_par_vendeur, recu_par_acheteur,no_categorie,no_utilisateur) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

	private static final String sqlUpdate = "UPDATE dbo.ARTICLES_VENDUS SET  " +
			"nom_article=?,description=?,date_debut_encheres=?,heure_debut_encheres=?," +
			"date_fin_encheres=?,heure_fin_encheres=?,prix_initial=?, "
			+ "prix_vente=?,annule_par_vendeur=?,recu_par_acheteur=?,no_categorie=?,no_utilisateur=? WHERE no_article=?";

	private static final String sqlDelete = "DELETE FROM dbo.ARTICLES_VENDUS WHERE no_article=?";

	public ArticlesImpl() {
		try {
			this.utilisateursDAO = (UtilisateursDAO) DAOFactory.getUtilisateursDAO();
			this.categoriesDAO = (CategoriesDAO) DAOFactory.getCategoriesDAO();
		} catch (DALException e) {
			e.printStackTrace();
		}
	}


	@Override
	public Article getById(int id) throws DALException {
		Article article = new Article();
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		Connection con = null;

		try {
			con = GetConnection.getConnexion();
			statement = con.prepareStatement(sqlSelectById);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			article.setNomArticle(resultSet.getString("nom_article"));
			article.setDescription(resultSet.getString("description"));
			article.setDateDebutEnchere(resultSet.getDate("date_debut_encheres").toLocalDate());
			article.setTimeDebutEnchere(resultSet.getTime("heure_debut_encheres").toLocalTime());
			article.setDateFinEnchere(resultSet.getDate("date_fin_encheres").toLocalDate());
			article.setTimeFinEnchere(resultSet.getTime("heure_fin_encheres").toLocalTime());
			article.setMiseAPrix(resultSet.getInt("prix_initial"));
			article.setPrixVente(resultSet.getInt("prix_vente"));
			article.setAnnuleParVendeur(resultSet.getBoolean("annule_par_vendeur"));
			article.setRecuParAcheteur(resultSet.getBoolean("recu_par_acheteur"));
			article.setCategorie(categoriesDAO.getById(resultSet.getInt("no_categorie")));
			article.setUtilisateur(utilisateursDAO.getById(resultSet.getInt("no_utilisateur")));
			// todo: il faut aussi peuple le lieu Retrait() et les Encheres liées à l'article
		} catch (SQLException ex) {
			throw new DALException("getById failed  = " + id, ex);
		}

		finally {
			GetConnection.close(resultSet);
			GetConnection.close(statement);
			GetConnection.close(con);
		}

		return article;
	}

	@Override
	public List<Article> getAll() throws DALException  {
		List<Article> listArticle = new ArrayList<>();
		ResultSet resultSet = null;
		Statement statement = null;
		Connection con = null;

		try {
			con = GetConnection.getConnexion();
			statement = con.createStatement();
			resultSet = statement.executeQuery(sqlSelectAll);

			while (resultSet.next()) {
				listArticle.add(new Article(
						resultSet.getInt("no_article"),
						resultSet.getString("nom_article"),
						resultSet.getString("description"),
						resultSet.getDate("date_debut_encheres").toLocalDate(),
						resultSet.getTime("heure_debut_encheres").toLocalTime(),
						resultSet.getDate("date_fin_encheres").toLocalDate(),
						resultSet.getTime("heure_fin_encheres").toLocalTime(),
						resultSet.getInt("prix_initial"),
						resultSet.getInt("prix_vente"),
						resultSet.getBoolean("annule_par_vendeur"),
						resultSet.getBoolean("recu_par_acheteur"),
						categoriesDAO.getById(resultSet.getInt("no_categorie")),
						new Retrait(),  // todo ici il faut faire les jointures?
						utilisateursDAO.getById(resultSet.getInt("no_utilisateur")),
						new ArrayList<>()  // ici il faut faire une jointure pour recuperer les encheres?
					));

			}
		} catch (SQLException ex) {
			throw new DALException("selectAll failed - " + ex.getLocalizedMessage(), ex);
		}

		finally {
			GetConnection.close(resultSet);
			GetConnection.close(statement);
			GetConnection.close(con);
		}
		return listArticle;
	}

	@Override
	public Article getByCategorie(Categorie categorie) throws DALException {
		Article article = new Article();
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		Connection con = null;

		try {
			con = GetConnection.getConnexion();
			statement = con.prepareStatement(sqlSelectByCategorie);
			statement.setObject(1, categorie);
			resultSet = statement.executeQuery();

			article.setNomArticle(resultSet.getString("nom_article"));
			article.setDescription(resultSet.getString("description"));
			article.setDateDebutEnchere(resultSet.getDate("date_debut_encheres").toLocalDate());
			article.setTimeDebutEnchere(resultSet.getTime("heure_debut_encheres").toLocalTime());
			article.setDateFinEnchere(resultSet.getDate("date_fin_encheres").toLocalDate());
			article.setTimeFinEnchere(resultSet.getTime("heure_fin_encheres").toLocalTime());
			article.setMiseAPrix(resultSet.getInt("prix_initial"));
			article.setPrixVente(resultSet.getInt("prix_vente"));
			article.setCategorie(categoriesDAO.getById(resultSet.getInt("no_categorie")));
			article.setUtilisateur(utilisateursDAO.getById(resultSet.getInt("no_utilisateur")));

		} catch (SQLException ex) {
			throw new DALException("getByCategorie failed  = " + categorie + " " + ex.getLocalizedMessage(), ex);
		}

		finally {
			GetConnection.close(resultSet);
			GetConnection.close(statement);
			GetConnection.close(con);
		}

		return article;
	}

	@Override
	public void add(Article article) throws DALException {
		Connection con = null;
		ResultSet rs = null;

		try (PreparedStatement statement = GetConnection.getConnexion().prepareStatement(
				sqlInsert, new String[] { "no_article" })){
			int i = 1;

			statement.setString(i++, article.getNomArticle());
			statement.setString(i++, article.getDescription());
			statement.setDate(i++, Date.valueOf(article.getDateDebutEnchere()));
			statement.setTime(i++, Time.valueOf(article.getTimeDebutEnchere()));
			statement.setDate(i++, Date.valueOf(article.getDateFinEnchere()));
			statement.setTime(i++, Time.valueOf(article.getTimeFinEnchere()));
			statement.setInt(i++, article.getMiseAPrix());
			statement.setInt(i++, article.getPrixVente());
			statement.setBoolean(i++, article.isAnnuleParVendeur());
			statement.setBoolean(i++, article.isRecuParAcheteur());
			statement.setInt(i++, article.getCategorie().getId());
			statement.setInt(i++, article.getUtilisateur().getNoUtilisateur());

			int nbRows = statement.executeUpdate();
			if (nbRows == 1) {
				rs = statement.getGeneratedKeys();
				if (rs.next()) {
					article.setNoArticle(rs.getInt(1));
				}
			}
			statement.execute();

		} catch (SQLException | NullPointerException ex) {
			throw new DALException(ex.getLocalizedMessage(), ex);
		}

		finally {
			GetConnection.close(rs);
			GetConnection.close(con);
		}
	}

	@Override
	public void update(Article article) throws DALException {
		Connection con = null;
		PreparedStatement statement = null;

		try {
			con = GetConnection.getConnexion();
			statement = con.prepareStatement(sqlUpdate);

			int i = 1;
			statement.setInt(i++, article.getNoArticle());
			statement.setString(i++, article.getNomArticle());
			statement.setString(i++, article.getDescription());
			statement.setDate(i++, Date.valueOf(article.getDateDebutEnchere()));
			statement.setTime(i++, Time.valueOf(article.getTimeDebutEnchere()));
			statement.setDate(i++, Date.valueOf(article.getDateFinEnchere()));
			statement.setTime(i++, Time.valueOf(article.getTimeFinEnchere()));
			statement.setInt(i++, article.getMiseAPrix());
			statement.setInt(i++, article.getPrixVente());
			statement.setBoolean(i++, article.isAnnuleParVendeur());
			statement.setBoolean(i++, article.isRecuParAcheteur());
			statement.setInt(i++, article.getCategorie().getId());
			statement.setInt(i++, article.getUtilisateur().getNoUtilisateur());

			statement.executeUpdate();

		} catch (SQLException ex) {
			throw new DALException("update article failed - " + article, ex);
		}

		finally {
			GetConnection.close(statement);
			GetConnection.close(con);
		}
	}

	@Override
	public void remove(Article supArticle) throws DALException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = GetConnection.getConnexion();
			stmt = con.prepareStatement(sqlDelete);

			stmt.setInt(1, supArticle.getNoArticle());
			stmt.executeUpdate();

		} catch (SQLException ex) {
			throw new DALException("Erreur dans la suppression de l'article ", ex);

		} finally {
			GetConnection.close(rs);
			GetConnection.close(stmt);
			GetConnection.close(con);
		}

	}
}