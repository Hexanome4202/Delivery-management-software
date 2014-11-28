package test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import modele.Noeud;
import modele.Plan;
import modele.Tournee;
import modele.Troncon;

import org.junit.Test;

/**
 * Classe de test de la classe <code>Tournee</code>
 * @author asarazin1
 *
 */
public class TourneeTest {

	/**
	 * 
	 */
	@Test
	public void testConstruireAPartirDeDOMXML() {
		fail("todo");
	}
	
	/**
	 * Test de la méthode <code>calculerDijkstra</code> sur un cas normal
	 */
	@Test
	public void testDijkstraExempleWiki() {
		Noeud noeud1 = new Noeud(1, 0, 0);
		Noeud noeud2 = new Noeud(2, 0, 0);
		Noeud noeud3 = new Noeud(3, 0, 0);
		Noeud noeud4 = new Noeud(4, 0, 0);
		Noeud noeud5 = new Noeud(5, 0, 0);
		Noeud noeud6 = new Noeud(6, 0, 0);
		
		Set<Noeud> noeuds = new TreeSet<Noeud>();
		noeuds.add(noeud1);
		noeuds.add(noeud2);
		noeuds.add(noeud3);
		noeuds.add(noeud4);
		noeuds.add(noeud5);
		noeuds.add(noeud6);
		
		Troncon troncon13 = new Troncon(1, 9, "", noeud3);
		noeud1.ajouterTronconSortant(troncon13);
		Troncon troncon31 = new Troncon(1, 9, "", noeud1);
		noeud3.ajouterTronconSortant(troncon31);
		
		Troncon troncon12 = new Troncon(1, 7, "", noeud2);
		noeud1.ajouterTronconSortant(troncon12);
		Troncon troncon21 = new Troncon(1, 7, "", noeud1);
		noeud2.ajouterTronconSortant(troncon21);
		
		Troncon troncon16 = new Troncon(1, 14, "", noeud6);
		noeud1.ajouterTronconSortant(troncon16);
		Troncon troncon61 = new Troncon(1, 14, "", noeud1);
		noeud6.ajouterTronconSortant(troncon61);
		
		Troncon troncon23 = new Troncon(1, 10, "", noeud3);
		noeud2.ajouterTronconSortant(troncon23);
		Troncon troncon32 = new Troncon(1, 10, "", noeud2);
		noeud3.ajouterTronconSortant(troncon32);
		
		Troncon troncon24 = new Troncon(1, 15, "", noeud4);
		noeud2.ajouterTronconSortant(troncon24);
		Troncon troncon42 = new Troncon(1, 15, "", noeud2);
		noeud4.ajouterTronconSortant(troncon42);
		
		Troncon troncon36 = new Troncon(1, 2, "", noeud6);
		noeud3.ajouterTronconSortant(troncon36);
		Troncon troncon63 = new Troncon(1, 2, "", noeud3);
		noeud6.ajouterTronconSortant(troncon63);
		
		Troncon troncon34 = new Troncon(1, 11, "", noeud4);
		noeud3.ajouterTronconSortant(troncon34);
		Troncon troncon43 = new Troncon(1, 11, "", noeud3);
		noeud4.ajouterTronconSortant(troncon43);
		
		Troncon troncon45 = new Troncon(1, 6, "", noeud5);
		noeud4.ajouterTronconSortant(troncon45);
		Troncon troncon54 = new Troncon(1, 6, "", noeud4);
		noeud5.ajouterTronconSortant(troncon54);
		
		Troncon troncon65 = new Troncon(1, 9, "", noeud5);
		noeud6.ajouterTronconSortant(troncon65);
		Troncon troncon56 = new Troncon(1, 9, "", noeud6);
		noeud5.ajouterTronconSortant(troncon56);
		
		Set<Troncon> troncons = new TreeSet<Troncon>();
		troncons.add(troncon12);
		troncons.add(troncon21);
		troncons.add(troncon13);
		troncons.add(troncon31);
		troncons.add(troncon16);
		troncons.add(troncon61);
		troncons.add(troncon23);
		troncons.add(troncon32);
		troncons.add(troncon24);
		troncons.add(troncon42);
		troncons.add(troncon36);
		troncons.add(troncon63);
		troncons.add(troncon34);
		troncons.add(troncon43);
		troncons.add(troncon45);
		troncons.add(troncon54);
		troncons.add(troncon65);
		troncons.add(troncon56);
		
		Plan plan = new Plan(troncons,noeuds);
		
		Tournee tournee = new Tournee();
		tournee.setPlanTournee(plan);
		
		LinkedList<Troncon> cheminResultat = new LinkedList<Troncon>();
		@SuppressWarnings("deprecation")
		double ponderationResultat = tournee.testCaculDijkstra(noeud1, noeud5, cheminResultat);
		
		LinkedList<Troncon> cheminExpected = new LinkedList<Troncon>();
		cheminExpected.add(troncon13);
		cheminExpected.add(troncon36);
		cheminExpected.add(troncon65);
		
		double ponderationExpected = 20;
		
		assertEquals(cheminExpected, cheminResultat);
		assertEquals(ponderationExpected, ponderationResultat, 0.0);
	}
	
	/**
	 * Test de la méthode <code>calculerDijkstra</code> sur un graphe mal formé
	 */
	@Test
	public void testDijkstraNoeudInaccessible(){
		Noeud noeud1 = new Noeud(1, 0, 0);
		Noeud noeud2 = new Noeud(2, 0, 0);
		Noeud noeud3 = new Noeud(3, 0, 0);
		
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
		
		LinkedList<Troncon> cheminResultat = new LinkedList<Troncon>();
		@SuppressWarnings("deprecation")
		double ponderationResultat = tournee.testCaculDijkstra(noeud1, noeud3, cheminResultat);
		
		LinkedList<Troncon> cheminExpected = new LinkedList<Troncon>();
		
		assertEquals(cheminExpected, cheminResultat);
		assertEquals(Double.MAX_VALUE, ponderationResultat, 0.0);
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
}
