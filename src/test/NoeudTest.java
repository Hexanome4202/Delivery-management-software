package test;

import static org.junit.Assert.*;
import modele.Noeud;

import org.junit.Test;

public class NoeudTest {

	@Test
	public void testCompareTo() {
		Noeud a = new Noeud(1, 2, 3);
		Noeud b = new Noeud(2, 2, 3);
		Noeud c = new Noeud(1, 3, 4);
		assertEquals("Erreur - les noeuds a et a devraient être différents", 0,
				a.compareTo(a));
		assertFalse("Erreur - les noeuds a et b ne devraient être identiques",
				a.compareTo(b) == 0);
		assertFalse(
				"Erreur - les tronçons a et c ne devraient pas être différents",
				a.compareTo(c) != 0);
	}

}
