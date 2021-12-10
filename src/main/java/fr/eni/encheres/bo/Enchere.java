// @author Lucie
package fr.eni.encheres.bo;

import java.time.LocalDate;

import fr.eni.encheres.bo.misc.ArticleVendu;

public class Enchere {
	private Utilisateur utilisateur;
	private Article articleVendu;
	/*private int noUtilisateur;
	private int noArticle;*/
	private int montantEnchere;
	private LocalDate dateEnchere;
	
	public Enchere() {	
	}
	
	public Enchere(Utilisateur utilisateur, Article articleVendu, LocalDate dateEnchere, int montantEnchere) {
		super();
		this.setUtilisateur(utilisateur);
		this.setArticleVendu(articleVendu);
		this.montantEnchere = montantEnchere;
		this.dateEnchere = dateEnchere;
	}

	/*public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}*/
	
	/*public int getNoArticle() {
		return noArticle;
	}


	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}*/

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Article getArticleVendu() {
		return articleVendu;
	}

	public void setArticleVendu(Article articleVendu) {
		this.articleVendu = articleVendu;
	}
	
	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	public LocalDate getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(LocalDate dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	@Override
	public String toString() {
		return "Enchere [noUtilisateur=" + this.utilisateur.getNoUtilisateur() + ", noArticle=" + this.articleVendu.getNoArticle() + ", montantEnchere="
				+ this.montantEnchere + ", dateEnchere=" + this.dateEnchere + "]";
	}

	

}
