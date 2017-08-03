package fr.sopra.pox3.test;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.sopra.pox3.ejb.AuteurDAO;
import fr.sopra.pox3.ejb.MaisonDeDisqueDAO;
import fr.sopra.pox3.entities.Auteur;
import fr.sopra.pox3.entities.MaisonDeDisque;

@RunWith( Arquillian.class )
public class AuteurPersistenceTest
{
	@Deployment
	public static Archive<?> createDeployment()
	{
		// TODO Ajouter la data source pour les tests et l'utiliser à la place de ExempleDS dans le fichier persistence.xml
		return ShrinkWrap.create( WebArchive.class, "test.war" )
				.addPackage( MaisonDeDisque.class.getPackage() )
				.addPackage(MaisonDeDisqueDAO.class.getPackage() )
				.addPackage( Auteur.class.getPackage() )
				.addPackage(AuteurDAO.class.getPackage() )
				.addAsResource( "test-persistence.xml", "META-INF/persistence.xml" )
				.addAsWebInfResource( EmptyAsset.INSTANCE, "beans.xml" );
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;
	
	@EJB
	MaisonDeDisqueDAO dao;
	
	@EJB
	AuteurDAO auteurDAO;

	@Before
	public void preparePersistenceTest() throws Exception
	{
		startTransaction();
		clearData();
		insertData();
		utx.commit();

		em.clear();

		startTransaction();
	}

	@After
	public void commitTransaction() throws Exception
	{	
		
		utx.commit();
	}
	
	@Test
	public void findAllAuteur() throws Exception {
		System.out.println("=============================");
		List<Auteur>auteurs = auteurDAO.findAll();
		Assert.assertEquals(1, auteurs.size());
		List<MaisonDeDisque> maisons = dao.findAll();
		Assert.assertEquals(1, maisons.size());
	}
	
	@Test
	public void findById() throws Exception {
		System.out.println("=============================");
		Auteur auteur = new Auteur();
		auteur.setNom("Titi");
		em.persist(auteur);
		Assert.assertEquals(auteur, auteurDAO.findById(auteur.getId()));
	}
	
	@Test
	public void addAuteur() throws Exception {
		System.out.println("=============================");
		Auteur auteur = new Auteur();
		auteur.setNom("Toto");
		MaisonDeDisque maison = new MaisonDeDisque();
		maison.setNom("Modern Records");
		auteur.setMaison(maison);
		auteurDAO.ajouterAuteur(auteur);
		List<Auteur> auteurs = auteurDAO.findAll();
		Assert.assertEquals(2, auteurs.size());
	}
	
	@Test
	public void updateAuteur() throws Exception {
		System.out.println("=============================");
		List<Auteur> auteurs = auteurDAO.findAll();
		int id = auteurs.get(0).getId();
		Auteur auteur = auteurDAO.findById(id);
		auteur.setNom("Tata");
		auteurDAO.updateAuteur(auteur);
		Assert.assertEquals("Tata", auteurDAO.findById(id).getNom());
	}
	
	@Test
	public void deleteAuteur() throws Exception {
		System.out.println("=============================");
		List<Auteur> auteurs = auteurDAO.findAll();
		int id = auteurs.get(0).getId();
		Auteur auteur = auteurDAO.findById(id);
		auteurDAO.deleteAuteur(auteur);
		List<Auteur> auteursFin = auteurDAO.findAll();
		Assert.assertEquals(0, auteursFin.size());
	}

	

	private void clearData() throws Exception
	{
		System.out.println( "Deleting old records" );
		
		
		em.createQuery( "delete from auteurs m" ).executeUpdate();
		em.createQuery( "delete from maisons_de_disque m" ).executeUpdate();
	}

	private void insertData() throws Exception
	{
		System.out.println( "Inserting record" );
		
		Auteur auteur = new Auteur();
		auteur.setNom("Toto");
		MaisonDeDisque maison = new MaisonDeDisque();
		maison.setNom("Nouvelle Maison Disque");
		auteur.setMaison(maison);
		em.merge(auteur);
	}

	private void startTransaction() throws Exception
	{
		utx.begin();
		em.joinTransaction();
	}
}
