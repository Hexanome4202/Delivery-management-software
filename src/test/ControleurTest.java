package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import modele.Noeud;
import modele.Troncon;

import org.junit.Test;

import controleur.Controleur;

public class ControleurTest {

	@Test
	public void testGererFichier() {
		// TODO: debug toutTroncons (Plan)
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

}
