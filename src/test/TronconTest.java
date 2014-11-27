package test;

import static org.junit.Assert.*;
import modele.Noeud;
import modele.Troncon;

import org.junit.Test;

public class TronconTest {

	@Test
	public void testGetTemps() {
		double vitesse = 3;
		double longueur = 5;
		Troncon troncon = new Troncon(vitesse, longueur, "");
		assertEquals(longueur/vitesse, troncon.getTemps(), 0.01);
	}

	@Test
	public void testCompareTo() {
		Noeud noeud = new Noeud(2, 3, 4);
		Troncon a = new Troncon(2, 3, "", noeud);
		Troncon b = new Troncon(2, 3, "", noeud);
		Troncon c = new Troncon(2, 4, "", noeud);
		Troncon d = new Troncon(1, 3, "", noeud);
		Troncon e = new Troncon(2, 3, "", null);
		
		assertEquals(0, a.compareTo(b));
		assertFalse(a.compareTo(c) != 0);
		assertFalse(a.compareTo(d) != 0);
		assertFalse(a.compareTo(e) != 0);
	}
}
