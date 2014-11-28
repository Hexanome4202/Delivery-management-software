package test;

import static org.junit.Assert.assertFalse;

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
		DemandeDeLivraison d1 = new DemandeDeLivraison(1, n1);
		DemandeDeLivraison d2 = new DemandeDeLivraison(2, n2);
		DemandeDeLivraison d3 = new DemandeDeLivraison(3, n3);
		Set<DemandeDeLivraison> demandes = new TreeSet<DemandeDeLivraison>();
		demandes.add(d1);
		demandes.add(d2);
		demandes.add(d3);
		List<Noeud> noeuds = new ArrayList<Noeud>();
		noeuds.add(n1);
		noeuds.add(n2);
		noeuds.add(n3);
		PlageHoraire plage = new PlageHoraire("18:00:00", "20:00:00", demandes);
		assertFalse("Erreur - La liste de noeuds n'est pas correcte", !plage.getNoeuds().containsAll(noeuds));
	}

}
