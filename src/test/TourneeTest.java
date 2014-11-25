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

public class TourneeTest {

	@Test
	public void testConstruireAPartirDeDOMXML() {
		
	}
	
	@Test
	public void testDijkstra() {
		Noeud noeud1 = new Noeud(0, 0, 1);
		Noeud noeud2 = new Noeud(0, 0, 2);
		Noeud noeud3 = new Noeud(0, 0, 3);
		Noeud noeud4 = new Noeud(0, 0, 4);
		Noeud noeud5 = new Noeud(0, 0, 5);
		Noeud noeud6 = new Noeud(0, 0, 6);
		
		Set<Noeud> noeuds = new TreeSet<Noeud>();
		noeuds.add(noeud1);
		noeuds.add(noeud2);
		noeuds.add(noeud3);
		noeuds.add(noeud4);
		noeuds.add(noeud5);
		noeuds.add(noeud6);
		
		Troncon troncon13 = new Troncon(1, 9, "", noeud3);
		noeud1.ajouterTronconSortant(troncon13);
		
		Troncon troncon12 = new Troncon(1, 7, "", noeud2);
		noeud1.ajouterTronconSortant(troncon12);
		
		Troncon troncon16 = new Troncon(1, 14, "", noeud6);
		noeud1.ajouterTronconSortant(troncon16);
		
		Troncon troncon23 = new Troncon(1, 10, "", noeud3);
		noeud2.ajouterTronconSortant(troncon23);
		
		Troncon troncon24 = new Troncon(1, 15, "", noeud4);
		noeud2.ajouterTronconSortant(troncon24);
		
		Troncon troncon36 = new Troncon(1, 2, "", noeud6);
		noeud3.ajouterTronconSortant(troncon36);
		
		Troncon troncon34 = new Troncon(1, 11, "", noeud4);
		noeud3.ajouterTronconSortant(troncon34);
		
		Troncon troncon45 = new Troncon(1, 6, "", noeud5);
		noeud4.ajouterTronconSortant(troncon45);
		
		Troncon troncon65 = new Troncon(1, 9, "", noeud5);
		noeud6.ajouterTronconSortant(troncon65);
		
		Set<Troncon> troncons = new TreeSet<Troncon>();
		troncons.add(troncon12);
		troncons.add(troncon13);
		troncons.add(troncon16);
		troncons.add(troncon23);
		troncons.add(troncon24);
		troncons.add(troncon36);
		troncons.add(troncon34);
		troncons.add(troncon45);
		troncons.add(troncon65);
		
		Plan plan = new Plan(troncons,noeuds);
		
		Tournee tournee = new Tournee();
		tournee.setPlanTournee(plan);
		
		LinkedList<Troncon> resultat = tournee.testCaculDijkstra(noeud1, noeud5);
		
		fail("Not yet implemented");
	}

}
