package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Retrait;

/**
 * 
 * @author Sego
 *
 */

public interface RetraitsDAO {

	public Retrait lieuRetrait(int noArticle) throws DALException;

	public void insertNouvelleAdresse(Retrait adresse) throws DALException;

	public void deleteAdresseRetrait(Retrait adresse) throws DALException;

	void updateAdresseRetrait(Retrait adresse) throws DALException;

	public List<Retrait> selectAll() throws DALException;
}
