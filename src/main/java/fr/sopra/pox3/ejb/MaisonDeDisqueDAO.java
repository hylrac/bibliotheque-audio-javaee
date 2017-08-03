package fr.sopra.pox3.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.sopra.pox3.entities.Auteur;
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
	
	public void deleteMaisonDeDisque (MaisonDeDisque maison) throws Exception {
		em.merge(maison);
		TypedQuery<Auteur> q = em.createQuery("from auteurs ", Auteur.class);
		List<Auteur> auteurs = new ArrayList<>();
		for (Auteur auteur : q.getResultList()) {
			if (auteur.getMaison().getId() == maison.getId()) {
				auteurs.add(auteur);
			}
		}
		
		if (auteurs.isEmpty()) {
			em.remove(em.contains(maison) ? maison : em.merge(maison));
		} /*else {
			throw  new Exception("Impossible d'effacer une maison contenant des auteurs");
		}*/
	}
	
	public void updateMaisonDeDisque (MaisonDeDisque maison) {
		em.merge(maison);
	}
	
}
