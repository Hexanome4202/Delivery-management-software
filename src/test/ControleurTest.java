package test;

import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import modele.DemandeDeLivraison;
import modele.Noeud;
import modele.PlageHoraire;
import modele.Troncon;

import org.junit.Test;

import controleur.Controleur;
import erreurs.Codes;

public class ControleurTest {

	@Test
	public void testGererFichierPlan() {
		Controleur c = new Controleur();
		c.gererFichier(new File("XML/testPlan.xml"), "plan");
		
		assertEquals(2, c.getPlan().getToutNoeuds().size());
		List<Noeud> noeuds = new ArrayList<Noeud>(c.getPlan().getToutNoeuds());
		Noeud n;
		for(int i = 0; i < 2; ++i) {
			n = noeuds.get(i);
			if(n.getId() == 0) {
				assertEquals(3, n.getX());
				assertEquals(4, n.getY());
			} else if(n.getId() == 1) {
				assertEquals(1, n.getX());
				assertEquals(2, n.getY());
			} else {
				fail("Erreur dans le chargement des noeuds");
			}
		}
		
		List<Troncon> troncons = new ArrayList<Troncon>(c.getPlan().getToutTroncons());
		assertEquals(2, troncons.size());
		Troncon t;
		for(int i = 0; i < noeuds.size(); ++i) {
			n = noeuds.get(i);
			assertEquals(1, n.getTronconSortants().size());
			troncons = new ArrayList<Troncon>(n.getTronconSortants());
			t = troncons.get(0);
			assertNotNull(t.getNoeudFin());
			if(n.getId() == 0) {
				assertEquals("v0", t.getNomRue());
				assertEquals(3.9, t.getVitesse(), 0.01);
				assertEquals(602.1, t.getLongueur(), 0.01);
			} else if(n.getId() == 1) {
				assertEquals("v1", t.getNomRue());
				assertEquals(3.9, t.getVitesse(), 0.01);
				assertEquals(602.1, t.getLongueur(), 0.01);
			}
		}
	}
	
	@Test
	public void testGererFichierPlanNonXML() {
		// Test Noeud destination inexistant pour un tronçon 
		Controleur c = new Controleur();
		c.setModeTest(true);
		assertEquals(Codes.PARSE_ERROR, c.gererFichier(new File("XML/testPlan2.xml"), "plan"));
		assertEquals(0, c.getPlan().getToutNoeuds().size());
		assertEquals(0, c.getPlan().getToutTroncons().size());
	}
	
	@Test
	public void testGererFichierPlanNoeudDestInexistant() {
		// Test poru des informations erronés pour la description d'un torncon
		Controleur c = new Controleur();
		c.setModeTest(true);
		assertEquals(Codes.ERREUR_301, c.gererFichier(new File("XML/errors/plan301.xml"), "plan"));
		assertEquals(0, c.getPlan().getToutNoeuds().size());
		assertEquals(0, c.getPlan().getToutTroncons().size());
	}
	
	@Test
	public void testGererFichierPlanMauvaiseSpecTroncon() {
		// Vitesse négative, taille négative, nom vide, id négatif
		Controleur c = new Controleur();
		c.setModeTest(true);
		assertEquals(Codes.ERREUR_302, c.gererFichier(new File("XML/errors/plan302.xml"), "plan"));
		assertEquals(0, c.getPlan().getToutNoeuds().size());
		assertEquals(0, c.getPlan().getToutTroncons().size());
	}
	
	@Test
	public void testGererFichierPlanMauvaiseSpecNoeud() {
		// Id de noeud négatif
		Controleur c = new Controleur();
		c.setModeTest(true);
		assertEquals(Codes.ERREUR_303, c.gererFichier(new File("XML/errors/plan303.xml"), "plan"));
		assertEquals(0, c.getPlan().getToutNoeuds().size());
		assertEquals(0, c.getPlan().getToutTroncons().size());
	}
	
	@Test
	public void testGererFichierLivraisons() {
		Controleur c = new Controleur();
		c.gererFichier(new File("XML/plan2.xml"), "plan");
		c.gererFichier(new File("XML/testLivraisons.xml"), "horaires");
		
		assertNotNull("tournee null", c.getTournee());
		assertNotNull("entrepot null", c.getTournee().getEntrepot());
		assertNotNull("noeud de l'entrepot null", c.getTournee().getEntrepot().getNoeud());
		assertEquals(2, c.getTournee().getEntrepot().getNoeud().getId());
		
		List<PlageHoraire> plages = c.getTournee().getPlagesHoraires();
		assertNotNull(plages);
		assertEquals(1, plages.size());
		
		PlageHoraire plage = plages.get(0);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(plage.getHeureDebut());
		assertEquals(8, calendar.get(GregorianCalendar.HOUR_OF_DAY));
		assertEquals(0, calendar.get(GregorianCalendar.MINUTE));
		assertEquals(0, calendar.get(GregorianCalendar.SECOND));
		calendar.setTime(plage.getHeureFin());
		assertEquals(12, calendar.get(GregorianCalendar.HOUR_OF_DAY));
		assertEquals(0, calendar.get(GregorianCalendar.MINUTE));
		assertEquals(0, calendar.get(GregorianCalendar.SECOND));
		assertNotNull(plage);
		assertEquals(3, plage.getDemandeLivraison().size());
		Noeud n;
		for(DemandeDeLivraison demande : plage.getDemandeLivraison()) {
			n = demande.getNoeud();
			assertNotNull(n);
			if(demande.getId() == 1) {
				assertEquals(611, demande.getIdClient());
				assertEquals(1, n.getId());
			} else if(demande.getId() == 2) {
				assertEquals(621, demande.getIdClient());
				assertEquals(3, n.getId());
			} else if(demande.getId() == 3) {
				assertEquals(611, demande.getIdClient());
				assertEquals(2, n.getId());
			} else {
				fail("Cette demande ne devrait pas exister...");
			}
		}
	}
	
	/**
	 * Test pour un fichier de livraisons vide
	 */
	@Test
	public void testGererFichierLivraisonsVide(){
		Controleur c = new Controleur();
		c.setModeTest(true);
		c.gererFichier(new File("XML/plan2.xml"), "plan");
		c.gererFichier(new File("XML/errors/livraisonsVide.xml"), "horaires");
		assertEquals(new ArrayList<PlageHoraire>(),c.getTournee().getPlagesHoraires());
	}
	
	/**
	 * Test pour un fichier où des plages horaires se chevauchent
	 */
	@Test
	public void testGererFichierLivraisonsPlagesChevauchent(){
		Controleur c = new Controleur();
		//c.setModeTest(true);
		c.gererFichier(new File("XML/plan2.xml"), "plan");
		c.gererFichier(new File("XML/errors/livraisonsChevauche.xml"), "horaires");
		PlageHoraire plage = null;
		
		try {
			plage = new PlageHoraire("8:0:0","9:0:0");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		assertEquals("Liste de plages horaires pas de la bonne taille...",1,c.getTournee().getPlagesHoraires().size());
		assertEquals("Il ne reste pas la bonne plage horaire...",plage.getHeureDebut(),c.getTournee().getPlagesHoraires().get(0).getHeureDebut());
		assertEquals("Il ne reste pas la bonne plage horaire...",plage.getHeureFin(),c.getTournee().getPlagesHoraires().get(0).getHeureFin());
		
	}
	
	/**
	 * Test pour un entrepôt invalide
	 */
	@Test
	public void testGererFichierLivraisonsEntrepotInvalide(){
		Controleur c = new Controleur();
		c.setModeTest(true);
		c.gererFichier(new File("XML/plan2.xml"), "plan");
		
		assertEquals(Codes.ERREUR_306,c.gererFichier(new File("XML/errors/livraisonsEntrepotFaux.xml"), "horaires"));
	}

	/**
	 * Test pour une plage horaire invalide (heureDebut > heureFin)
	 */
	@Test
	public void testGererFichierLivraisonsPlageHoraireInvalide(){
		Controleur c = new Controleur();
		c.gererFichier(new File("XML/plan2.xml"), "plan");
		c.gererFichier(new File("XML/errors/livraisonsPlageInvalide.xml"), "horaires");
		
		assertEquals("Il n'y a pas qu'une seule plage...",1,c.getTournee().getPlagesHoraires().size());
		assertEquals("Il n'y a pas qu'une seule demande...",1,c.getTournee().getPlagesHoraires().get(0).getDemandeLivraison().size());
		assertEquals("La demande n'a pas le bon ID...",1,new ArrayList<DemandeDeLivraison>(c.getTournee().getPlagesHoraires().get(0).getDemandeLivraison()).get(0).getId());
	}
	
	/**
	 * Test pour une livraison invalide
	 */
	@Test
	public void testGererFichierLivraisonsLivraisonInvalide(){
		Controleur c = new Controleur();
		c.gererFichier(new File("XML/plan2.xml"), "plan");
		c.gererFichier(new File("XML/errors/livraisonsLivraisonInvalide.xml"), "horaires");
		
		DemandeDeLivraison demande = new DemandeDeLivraison(1, c.getPlan().getNoeud(3), 621, null);
		assertEquals(1,c.getTournee().getPlagesHoraires().size());
		assertEquals(1,c.getTournee().getPlagesHoraires().get(0).getDemandeLivraison().size());

		List<DemandeDeLivraison> demandes = new ArrayList<DemandeDeLivraison>(c.getTournee().getPlagesHoraires().get(0).getDemandeLivraison());
		assertTrue(demandes.get(0).compareTo(demande)==0);
	}
	
	/**
	 * Méthode qui teste les méthodes <code>undo()</code> et <code>redo()</code>
	 */
	@Test
	public void testUndo(){
		Controleur c = new Controleur();
		c.gererFichier(new File("XML/plan2.xml"), "plan");
		c.gererFichier(new File("XML/testLivraisons.xml"), "horaires");
		c.calculerTournee();
		c.ajouterLivraison(15, c.getPlan().getNoeud(4), c.getPlan().getNoeud(1));
		assertEquals(5,c.getTournee().getItineraires().size());
		c.undo();
		assertEquals(4,c.getTournee().getItineraires().size());
		c.redo();
		assertEquals(5,c.getTournee().getItineraires().size());
		c.undo();
		
		c.supprimerLivraison(c.getPlan().getNoeud(1));
		assertEquals(3,c.getTournee().getItineraires().size());
		c.undo();
		assertEquals(4,c.getTournee().getItineraires().size());
		c.redo();
		assertEquals(3,c.getTournee().getItineraires().size());
	}
}
