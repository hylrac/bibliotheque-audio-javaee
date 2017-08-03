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

import fr.sopra.pox3.ejb.MaisonDeDisqueDAO;
import fr.sopra.pox3.ejb.MaisonDeDisqueDTO;
import fr.sopra.pox3.entities.MaisonDeDisque;

@Path("/maisons")

@Produces("application/json")
@Consumes("application/json")
public class MaisonDeDisqueResource {
	@EJB
	private MaisonDeDisqueDAO maisonDAO;
	
	
	
	@GET
	@Path("/{id}")
	public MaisonDeDisqueDTO findById (@PathParam("id") int id) {
		MaisonDeDisque maison = maisonDAO.findById(id);
		if (maison == null) return null;
		
		MaisonDeDisqueDTO maisonDTO = new MaisonDeDisqueDTO();
		maisonDTO.setId(id);
		maisonDTO.setNom(maison.getNom());
		
		return maisonDTO;
		
	}
	
	@GET
	public List<MaisonDeDisqueDTO> findAll() throws Exception {
		List<MaisonDeDisque> maisons = maisonDAO.findAll();
		
		if (maisons.isEmpty()) return null;
		List<MaisonDeDisqueDTO> maisonsDTO = new ArrayList<>();
		for (MaisonDeDisque maison : maisons) {
			MaisonDeDisqueDTO maisonDTO = new MaisonDeDisqueDTO();
			maisonDTO.setId(maison.getId());
			maisonDTO.setNom(maison.getNom());
			maisonsDTO.add(maisonDTO);
		}
		return maisonsDTO;
		
	}
	
	@POST
	public void addMaisonDeDisque(MaisonDeDisqueDTO maisonDTO) {
		MaisonDeDisque maison = new MaisonDeDisque();
		maison.setNom(maisonDTO.getNom());
		maisonDAO.addMaisonDeDisque(maison);
		
	}
	
	@DELETE
	@Path ("/{id}")
	public void deleteMaisonDeDisque (@PathParam("id") int id) throws Exception {
		MaisonDeDisque maison = maisonDAO.findById(id);
		if (maison != null) {
			maisonDAO.deleteMaisonDeDisque(maison);
		}
	}
	/*
	@PUT
	public void updateMaisonDeDisque (MaisonDeDisqueDTO maisonDTO) {
		MaisonDeDisque maison = new MaisonDeDisque();
		maison.setId(maisonDTO.getId());
		maison.setNom(maisonDTO.getNom());
		maisonDAO.updateMaisonDeDisque(maison);
	}*/
	
	@PUT
	@Path ("/{id}")
	public void updateMaisonDeDisque (@PathParam("id") int id, MaisonDeDisqueDTO maisonDTO) {
		MaisonDeDisque maison = new MaisonDeDisque();
		maison.setId(id);
		maison.setNom(maisonDTO.getNom());
		maisonDAO.updateMaisonDeDisque(maison);
	}

}
