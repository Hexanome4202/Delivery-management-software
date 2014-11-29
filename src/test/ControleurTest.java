package test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import modele.DemandeDeLivraison;
import modele.Noeud;
import modele.PlageHoraire;
import modele.Troncon;

import org.junit.Test;

import controleur.Controleur;

public class ControleurTest {

	@Test
	public void testGererFichierPlan() {
		// TODO: debug toutTroncons (Plan)
		// TODO: Gérer tous les cas identifiés dans les CUs
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
		//TODO : fail car toutTroncons n'est pas mis à jour après chargement du plan ! 
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
	
	@Test
	public void testGererFichierLivraisons() {
		// TODO: gérer tous les cas identifiés dans les CUs
		// TODO: test heures de la plage horaire
		// TODO: Créer le fichier de plan
		Controleur c = new Controleur();
		c.gererFichier(new File("XML/testPlan2.xml"), "plan");
		c.gererFichier(new File("XML/testLivraisons.xml"), "horaires");
		
		assertNotNull("tournee null", c.getTournee());
		assertNotNull("entrepot null", c.getTournee().getEntrepot());
		assertNotNull("noeud de l'entrepot null", c.getTournee().getEntrepot().getNoeud());
		assertEquals(2, c.getTournee().getEntrepot().getNoeud().getId());
		
		List<PlageHoraire> plages = c.getTournee().getPlagesHoraires();
		assertNotNull(plages);
		assertEquals(1, plages.size());
		
		PlageHoraire plage = plages.get(0);
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
				assertEquals(1, n.getId());
			} else {
				fail("Cette demande ne devrait pas exister...");
			}
		}
	}

}
