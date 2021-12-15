package fr.eni.encheres.servlets;

import fr.eni.encheres.beans.Erreurs;
import fr.eni.encheres.bll.ArticleManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "VentesEnCoursServlet", value = "/VentesEnCours")
public class VentesEnCoursServlet extends HttpServlet {
	static ArticleManager articleManager = ArticleManager.GetInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Erreurs erreurs = (Erreurs) request.getAttribute("errors");
		request.setAttribute("articles", articleManager.getCatalogue(erreurs));

		request.getRequestDispatcher("/WEB-INF/jsps/vente/ListerVentes.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
