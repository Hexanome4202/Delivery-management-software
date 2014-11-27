package test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import modele.Noeud;
import modele.Plan;
import modele.Troncon;

import org.junit.Test;

/**
 * Classe de test de la classe <code>PlanTest</code>
 * @author remimartin
 *
 */
public class PlanTest {
	/**
	 * Teste la méthode <code>recupererNoeud</code>
	 */
	@Test
	public void testRecupererNoeud() {
		Set<Noeud> noeuds = new HashSet<Noeud>();
		noeuds.add(new Noeud(2, 3, 4));
		Set<Troncon> troncons = new HashSet<Troncon>();
		Plan plan = new Plan(troncons, noeuds);
		
		Noeud noeud1 = plan.recupererNoeud(3);
		Noeud noeud2 = plan.recupererNoeud(2);
		
		assertEquals(null, noeud1);
		assertNotNull(noeud2);
		assertEquals(2, noeud2.getId());
	}

}
