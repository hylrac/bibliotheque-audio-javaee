package fr.sopra.pox3.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.sopra.pox3.ejb.AuteurDAO;
import fr.sopra.pox3.ejb.MaisonDeDisqueDAO;
import fr.sopra.pox3.entities.MaisonDeDisque;

@WebServlet("/index2.html")
public class JPA102Servlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private AuteurDAO auteurDAO;
	
	@EJB
	private MaisonDeDisqueDAO maisonDeDisqueDAO;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().print("I am here\n");
		
		/*MaisonDeDisque maison = new MaisonDeDisque();
		maison.setId(145);
		maison.setNom("Super Maison");
		Auteur auteur = new Auteur();
		auteur.setNom("Titi");
		auteur.setMaison(maison);
		
		auteurDAO.ajouterAuteur(auteur);*/
		
		MaisonDeDisque maison = maisonDeDisqueDAO.findById(145);
		System.out.println(maison.getNom() + " " + maison.getId());
		System.out.println(maison.getAuteurs().size());
		
		
	}

}
