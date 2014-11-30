package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import modele.DemandeDeLivraison;
import modele.Dijkstra;
import modele.Itineraire;
import modele.Noeud;
import modele.PlageHoraire;
import modele.Plan;
import modele.Tournee;
import modele.Troncon;

import org.junit.Test;

import controleur.Controleur;

/**
 * Classe de test de la classe <code>Tournee</code>
 * @author asarazin1
 *
 */
public class TourneeTest {
	
	/**
	 * Test de la méthode <code>calculerDijkstra</code> sur un cas normal
	 */
	@Test
	public void testDijkstraExempleWiki() {
		Controleur c = new Controleur();
		c.gererFichier(new File("XML/testDijkstraExempleWiki.xml"), "plan");
		
		Plan plan = c.getPlan();
		
		Tournee tournee = new Tournee();
		tournee.setPlanTournee(plan);
		
		double ponderationResultat = Dijkstra.calculerDijkstra(plan.recupererNoeud(1), plan.recupererNoeud(5), plan.getToutNoeuds());
		LinkedList<Troncon> cheminResultat = Dijkstra.chemin;
		double ponderationExpected = 20;
		
		assertEquals("Erreur dans taille du chemin",3, cheminResultat.size());
		assertEquals("Erreur dans chemin",3, cheminResultat.getFirst().getNoeudFin().getId());
		assertEquals("Erreur dans chemin",5, cheminResultat.getLast().getNoeudFin().getId());
		assertEquals("Erreur dans pondération totale du chemin",ponderationExpected, ponderationResultat, 0.0);
	}
	
	/**
	 * Test de la méthode <code>calculerDijkstra</code> sur un graphe mal formé
	 */
	@Test
	public void testDijkstraNoeudInaccessible(){
		Controleur c = new Controleur();
		c.gererFichier(new File("XML/testDijkstraNoeudInaccessible.xml"), "plan");
		
		Plan plan = c.getPlan();
		
		Tournee tournee = new Tournee();
		tournee.setPlanTournee(plan);
		
		double ponderationResultat = Dijkstra.calculerDijkstra(plan.recupererNoeud(1), plan.recupererNoeud(3), plan.getToutNoeuds());
		LinkedList<Troncon> cheminResultat = Dijkstra.chemin;
		
		LinkedList<Troncon> cheminExpected = new LinkedList<Troncon>();
		
		assertEquals(cheminExpected, cheminResultat);
		assertEquals(Double.MAX_VALUE, ponderationResultat, 0.0);
	}

	/**
	 * Méthode de test de <code>calculerTournee()</code>
	 */
	@Test
	public void testCalculerTournee(){
		Noeud noeud1 = new Noeud(1, 0, 0);
		Noeud noeud2 = new Noeud(2, 1, 2);
		Noeud noeud3 = new Noeud(3, 4, 3);
		
		Set<Noeud> noeuds = new TreeSet<Noeud>();
		noeuds.add(noeud1);
		noeuds.add(noeud2);
		noeuds.add(noeud3);
		
		Troncon troncon12 = new Troncon(1, 9, "", noeud2);
		noeud1.ajouterTronconSortant(troncon12);
		Troncon troncon23 = new Troncon(1, 10, "", noeud3);
		noeud2.ajouterTronconSortant(troncon23);
		Troncon troncon31 = new Troncon(1, 9, "", noeud1);
		noeud3.ajouterTronconSortant(troncon31);
		
		Set<Troncon> troncons = new TreeSet<Troncon>();
		troncons.add(troncon12);
		troncons.add(troncon23);
		troncons.add(troncon31);
		
		Plan plan = new Plan(troncons,noeuds);

		Tournee tournee = new Tournee();
		tournee.setPlanTournee(plan);
		
		PlageHoraire plage1 = new PlageHoraire("8h", "9h");
		PlageHoraire plage2 = new PlageHoraire("9h", "10h");
		
		DemandeDeLivraison entrepot = new DemandeDeLivraison(noeud1);
		DemandeDeLivraison demande1 = new DemandeDeLivraison(0, noeud2, 1, plage1);
		DemandeDeLivraison demande2 = new DemandeDeLivraison(1, noeud3, 3, plage2);
		
		Set<DemandeDeLivraison> demandes1 = new TreeSet<DemandeDeLivraison>();
		demandes1.add(demande1);
		
		Set<DemandeDeLivraison> demandes2 = new TreeSet<DemandeDeLivraison>();
		demandes2.add(demande2);
		
		plage1.setDemandesDeLivraison(demandes1);
		plage2.setDemandesDeLivraison(demandes2);
		ArrayList<PlageHoraire> plages = new ArrayList<PlageHoraire>();
		plages.add(plage1);
		plages.add(plage2);
		
		tournee.setPlagesHoraires(plages);
		tournee.setEntrepot(entrepot);
		tournee.calculerTournee();
		
		ArrayList<Troncon> tr12 = new ArrayList<Troncon>();
		tr12.add(troncon12);
		Itineraire it12 = new Itineraire(entrepot, demande1, tr12);
		
		ArrayList<Troncon> tr23 = new ArrayList<Troncon>();
		tr23.add(troncon23);
		Itineraire it23 = new Itineraire(demande1, demande2, tr23);
		
		ArrayList<Troncon> tr31 = new ArrayList<Troncon>();
		tr31.add(troncon31);
		Itineraire it31 = new Itineraire(demande2, entrepot, tr31);
		
		ArrayList<Itineraire> itinerairesExpected = new ArrayList<Itineraire>();
		itinerairesExpected.add(it12);
		itinerairesExpected.add(it23);
		itinerairesExpected.add(it31);
		
		ArrayList<Itineraire> itinerairesRes = tournee.getItineraires();
		
		assertEquals("L'itinéraire ne fait pas la bonne taille",itinerairesExpected.size(), itinerairesRes.size());
		
		for(int i=0; i<itinerairesExpected.size(); i++){
			Itineraire resultat = itinerairesRes.get(i);
			Itineraire expected = itinerairesExpected.get(i);
			assertEquals("Mauvaise arrivée",expected.getArrivee(),resultat.getArrivee());
			assertEquals("Mauvais départ", expected.getArrivee(),resultat.getArrivee());
			assertEquals("Mauvais temps total", expected.getTemps(), resultat.getTemps(), 0);
		}
		
	}
	
	@Test
	public void testRecupererNoeudXY() {
		Noeud noeud1 = new Noeud(1, 0, 0);
		Noeud noeud2 = new Noeud(2, 1, 2);
		Noeud noeud3 = new Noeud(3, 4, 3);
		
		Set<Noeud> noeuds = new TreeSet<Noeud>();
		noeuds.add(noeud1);
		noeuds.add(noeud2);
		noeuds.add(noeud3);
		
		Troncon troncon12 = new Troncon(1, 9, "", noeud2);
		noeud1.ajouterTronconSortant(troncon12);
		
		Troncon troncon32 = new Troncon(1, 9, "", noeud2);
		noeud3.ajouterTronconSortant(troncon32);
		
		Set<Troncon> troncons = new TreeSet<Troncon>();
		troncons.add(troncon12);
		troncons.add(troncon32);
		
		Plan plan = new Plan(troncons,noeuds);
		
		Tournee tournee = new Tournee();
		tournee.setPlanTournee(plan);
		
		assertEquals(null, tournee.recupererNoeud(5, 5));
		assertEquals(1, tournee.recupererNoeud(1, 2).getX());
		assertEquals(2, tournee.recupererNoeud(1, 2).getY());
	}
	
	@Test
	public void testRecupererNoeudId() {
		Noeud noeud1 = new Noeud(1, 0, 0);
		Noeud noeud2 = new Noeud(2, 1, 2);
		Noeud noeud3 = new Noeud(3, 4, 3);
		
		Set<Noeud> noeuds = new TreeSet<Noeud>();
		noeuds.add(noeud1);
		noeuds.add(noeud2);
		noeuds.add(noeud3);
		
		Troncon troncon12 = new Troncon(1, 9, "", noeud2);
		noeud1.ajouterTronconSortant(troncon12);
		
		Troncon troncon32 = new Troncon(1, 9, "", noeud2);
		noeud3.ajouterTronconSortant(troncon32);
		
		Set<Troncon> troncons = new TreeSet<Troncon>();
		troncons.add(troncon12);
		troncons.add(troncon32);
		
		Plan plan = new Plan(troncons,noeuds);
		
		Tournee tournee = new Tournee();
		tournee.setPlanTournee(plan);
		
		assertEquals(null, tournee.recupererNoeud(4));
		assertEquals(1, tournee.recupererNoeud(2).getX());
		assertEquals(2, tournee.recupererNoeud(2).getY());
	}
	
	@Test
	public void testEffacerItineraire() {
		Noeud noeud1 = new Noeud(1, 0, 0);
		Noeud noeud2 = new Noeud(2, 1, 2);
		Noeud noeud3 = new Noeud(3, 4, 3);
		
		Set<Noeud> noeuds = new TreeSet<Noeud>();
		noeuds.add(noeud1);
		noeuds.add(noeud2);
		noeuds.add(noeud3);
		
		Troncon troncon12 = new Troncon(1, 9, "", noeud2);
		noeud1.ajouterTronconSortant(troncon12);
		
		Troncon troncon32 = new Troncon(1, 9, "", noeud2);
		noeud3.ajouterTronconSortant(troncon32);
		
		Set<Troncon> troncons = new TreeSet<Troncon>();
		troncons.add(troncon12);
		troncons.add(troncon32);
		
		Plan plan = new Plan(troncons,noeuds);
		
		Tournee tournee = new Tournee();
		tournee.setPlanTournee(plan);
		
		fail("A faire, attendre que les itinéraires soient affectés");
	}
	
	@Test
	public void testEditerFeuilleRoute() {
		Controleur c = new Controleur();
		c.gererFichier(new File("XML/plan2.xml"), "plan");
		c.gererFichier(new File("XML/testLivraisons.xml"), "horaires");
		
		c.getTournee().calculerTournee();
		System.out.println(c.editerFeuilleRoute());
	}
}
