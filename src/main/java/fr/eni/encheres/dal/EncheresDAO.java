// @author Lucie
package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Enchere;

public interface EncheresDAO {
	
	public void insertEnchere(Enchere e)throws DALException;
	
	public Enchere selectEnchereByNoUtilisateur(int noUtilisateur)throws DALException;
	
	public Enchere selectEnchereByNoArticle(int noArticle)throws DALException;
	
	public Enchere selectEnchereByNoUtilisateurEtNoArticle(int noUtilisateur, int noArticle)throws DALException;
	
	public List<Enchere> selectAllEncheres()throws DALException;
	
	public void updateEnchere(Enchere e)throws DALException;
	
	public void deleteEnchereByNoUtilisateurEtNoArticle(int noUtilisateur, int noArticle)throws DALException;
}
