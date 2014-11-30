package test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import modele.DemandeDeLivraison;
import modele.Noeud;
import modele.PlageHoraire;

import org.junit.Test;

public class PlageHoraireTest {

	@Test
	public void testGetNoeuds() {
		Noeud n1 = new Noeud(1, 2, 3);
		Noeud n2 = new Noeud(2, 2, 3);
		Noeud n3 = new Noeud(3, 2, 3);
		DemandeDeLivraison d1 = new DemandeDeLivraison(1, n1, 1, null);
		DemandeDeLivraison d2 = new DemandeDeLivraison(2, n2, 2, null);
		DemandeDeLivraison d3 = new DemandeDeLivraison(3, n3, 3, null);
		Set<DemandeDeLivraison> demandes = new TreeSet<DemandeDeLivraison>();
		demandes.add(d1);
		demandes.add(d2);
		demandes.add(d3);
		ArrayList<Noeud> noeuds = new ArrayList<Noeud>();
		noeuds.add(n1);
		noeuds.add(n2);
		noeuds.add(n3);
		PlageHoraire plage;
		try {
			plage = new PlageHoraire("18:00:00", "20:00:00", demandes);
			assertEquals("Erreur - La liste de noeuds n'est pas correcte", plage.getNoeuds(), noeuds);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testRecupererNoeud() {
		Noeud n1 = new Noeud(1, 2, 3);
		Noeud n2 = new Noeud(2, 2, 3);
		Noeud n3 = new Noeud(3, 2, 3);
		DemandeDeLivraison d1 = new DemandeDeLivraison(1, n1, 1, null);
		DemandeDeLivraison d2 = new DemandeDeLivraison(2, n2, 2, null);
		DemandeDeLivraison d3 = new DemandeDeLivraison(3, n3, 3, null);
		Set<DemandeDeLivraison> demandes = new TreeSet<DemandeDeLivraison>();
		demandes.add(d1);
		demandes.add(d2);
		demandes.add(d3);
		List<Noeud> noeuds = new ArrayList<Noeud>();
		noeuds.add(n1);
		noeuds.add(n2);
		noeuds.add(n3);
		PlageHoraire plage;
		try {
			plage = new PlageHoraire("18:00:00", "20:00:00", demandes);
			assertEquals(n1.getId(), plage.recupererNoeud(1).getId());
			assertEquals(null, plage.recupererNoeud(4));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
