package fr.sopra.pox3.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.sopra.pox3.entities.MaisonDeDisque;

@Stateless
public class MaisonDeDisqueDAO {
	
	@PersistenceContext (name = "Bibliotheque")
	private EntityManager em ;
	
	public MaisonDeDisque findById ( int id) {
		/*TypedQuery<MaisonDeDisque> q = em.createQuery("from maisons_de_disque m join fetch m.auteurs where m.id = :id", MaisonDeDisque.class);
		q.setParameter("id", id)
		
		return q.getSingleResult();*/
		return em.find( MaisonDeDisque.class, id );
	}
	
	public List<MaisonDeDisque> findAll () {
		TypedQuery<MaisonDeDisque> q = em.createQuery("from maisons_de_disque m ", MaisonDeDisque.class);
		
		return q.getResultList();
	}
	
	public void addMaisonDeDisque (MaisonDeDisque maison) {
		em.persist(maison);
	}
	
	public void deleteMaisonDeDisque (MaisonDeDisque maison) {
		em.remove(em.contains(maison) ? maison : em.merge(maison));
	}
	
	public void updateMaisonDeDisque (MaisonDeDisque maison) {
		em.merge(maison);
		/*MaisonDeDisque maisonAUpdate = this.findById(maison.getId());
		MaisonDeDisque maisonUpdated = em.merge(maisonAUpdate);
		maisonUpdated.setNom(maison.getNom());*/
	}
	
}
