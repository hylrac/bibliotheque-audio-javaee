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
public class MaisonDeDisquePersistenceTest
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
	public void shouldFindAllGamesUsingJpqlQuery() throws Exception
	{
		System.out.println("=============================");
		System.out.println( "Selecting (using JPQL)..." );
		List<MaisonDeDisque> maisons = em.createQuery( "from maisons_de_disque m", MaisonDeDisque.class ).getResultList();

		System.out.println( "Found " + maisons.size() + " maisons" );
		Assert.assertEquals( 2, maisons.size() );
	}
	
	@Test
	public void findAll() throws Exception {
		System.out.println("=============================");
		List<MaisonDeDisque> maisons = dao.findAll();

		Assert.assertEquals( 2, maisons.size() );
	}
	
	@Test
	public void findById() throws Exception {
		System.out.println("=============================");
		List<MaisonDeDisque> maisons = dao.findAll();
		int id = maisons.get(0).getId();
		MaisonDeDisque maison = dao.findById(id);
		Assert.assertEquals(id, maison.getId());
	}
	
	
	
	
	@Test
	public void addMaison() throws Exception {
		System.out.println("=============================");
		MaisonDeDisque maison = new MaisonDeDisque();
		maison.setNom("Ultra Records");
		dao.addMaisonDeDisque(maison);
		List<MaisonDeDisque> maisons = dao.findAll();
		Assert.assertEquals(3, maisons.size());
	}
	
	@Test
	public void updateMaison() throws Exception {
		System.out.println("=============================");
		List<MaisonDeDisque> maisons = dao.findAll();
		int id = maisons.get(0).getId();
		MaisonDeDisque maison = dao.findById(id);
		maison.setNom("Grosse Maison Disque");
		dao.updateMaisonDeDisque(maison);
		MaisonDeDisque maison2 = dao.findById(id);
		Assert.assertEquals("Grosse Maison Disque", maison2.getNom());
	}
	
	@Test
	public void deleteMaison() throws Exception {
		System.out.println("=============================");
		List<MaisonDeDisque> maisons = dao.findAll();
		int id = maisons.get(0).getId();
		MaisonDeDisque maison = dao.findById(id);
		Auteur auteur = new Auteur();
		auteur.setNom("Tata");
		auteur.setMaison(maison);
		auteurDAO.ajouterAuteur(auteur);
		dao.deleteMaisonDeDisque(maison);
		List<MaisonDeDisque> maisonsFinales = dao.findAll();
		Assert.assertEquals(maisons.size(), maisonsFinales.size());
		
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
		
		MaisonDeDisque maison = new MaisonDeDisque();
		maison.setNom( "TotoRecords" );
		em.persist( maison );
		MaisonDeDisque maison2 = new MaisonDeDisque();
		maison2.setNom( "SuperRecords" );
		em.persist( maison2 );
		
		
	}

	private void startTransaction() throws Exception
	{
		utx.begin();
		em.joinTransaction();
	}
}
