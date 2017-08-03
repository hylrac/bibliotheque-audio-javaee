package fr.sopra.pox3.api;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import fr.sopra.pox3.ejb.AuteurDAO;
import fr.sopra.pox3.ejb.AuteurDTO;
import fr.sopra.pox3.ejb.MaisonDeDisqueDAO;
import fr.sopra.pox3.entities.Auteur;
import fr.sopra.pox3.entities.MaisonDeDisque;

@Path("/auteurs")
@Produces("application/json")
@Consumes("application/json")
public class AuteurResource {
	@EJB
	private AuteurDAO auteurDAO;

	@EJB
	private MaisonDeDisqueDAO maisonDAO;

	@GET
	@Path("/{id}")
	public AuteurDTO findById(@PathParam("id") int id) {
		Auteur auteur = auteurDAO.findById(id);
		if (auteur == null)
			return null;

		AuteurDTO auteurDTO = new AuteurDTO();
		auteurDTO.setId(id);
		auteurDTO.setNom(auteur.getNom());

		return auteurDTO;

	}

	@GET
	public List<AuteurDTO> findAll() throws Exception {
		List<Auteur> auteurs = auteurDAO.findAll();

		if (auteurs.isEmpty())
			return null;
		List<AuteurDTO> auteursDTO = new ArrayList<>();
		for (Auteur auteur : auteurs) {
			AuteurDTO auteurDTO = new AuteurDTO();
			auteurDTO.setId(auteur.getId());
			auteurDTO.setNom(auteur.getNom());
			auteursDTO.add(auteurDTO);
		}
		return auteursDTO;

	}

	@POST
	public void addAuteur(AuteurDTO auteurDTO) {
		Auteur auteur = new Auteur();
		auteur.setNom(auteurDTO.getNom());
		auteur.setMaison(auteurDTO.getMaisonDeDisque());
		auteurDAO.ajouterAuteur(auteur);
	}

	@DELETE
	@Path("/{id}")
	public void deleteAuteur(@PathParam("id") int id) throws Exception {
		MaisonDeDisque maison = maisonDAO.findById(id);
		if (maison != null) {
			maisonDAO.deleteMaisonDeDisque(maison);
		}
		Auteur auteur = auteurDAO.findById(id);
		if (auteur != null) {
			auteurDAO.deleteAuteur(auteur);
		}
	}
/*
	@DELETE
	public void deleteAuteurs(List<Integer> id) {
		for (int num : id) {
			MaisonDeDisque maison = maisonDAO.findById(num);
			if (maison != null) {
				maisonDAO.deleteMaisonDeDisque(maison);
			}
			Auteur auteur = auteurDAO.findById(num);
			if (auteur != null) {
				auteurDAO.deleteAuteur(auteur);
			}
		}
	}*/

	@PUT
	public void updateAuteur(AuteurDTO auteurDTO) {
		Auteur auteur = new Auteur();
		auteur.setId(auteurDTO.getId());
		auteur.setNom(auteurDTO.getNom());
		auteur.setMaison(auteurDTO.getMaisonDeDisque());
		auteurDAO.updateAuteur(auteur);
	}

}
