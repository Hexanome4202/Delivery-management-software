package test;

import static org.junit.Assert.*;
import modele.Noeud;
import modele.Troncon;

import org.junit.Test;

public class TronconTest {

	@Test
	public void testGetTemps() {
		Troncon troncon = new Troncon(3, 5, "");
		assertEquals("Erreur - Le temps du troncon devrait être 5/3", 5.0/3, troncon.getTemps(), 0.01);
	}

	@Test
	public void testCompareTo() {
		Noeud noeud = new Noeud(2, 3, 4);
		Troncon a = new Troncon(2, 3, "", noeud);
		Troncon b = new Troncon(2, 3, "", noeud);
		Troncon c = new Troncon(2, 4, "", noeud);
		Troncon d = new Troncon(1, 3, "", noeud);
		Troncon e = new Troncon(2, 3, "", null);
		
		assertEquals("Erreur - les tronçons a et b devraient être identiques", 0, a.compareTo(b));
		assertFalse("Erreur - les tronçons a et c ne devraient pas être identiques", a.compareTo(c) != 0);
		assertFalse("Erreur - les tronçons a et d ne devraient pas être identiques", a.compareTo(d) != 0);
		assertFalse("Erreur - les tronçons a et e ne devraient pas être identiques", a.compareTo(e) != 0);
	}
}
