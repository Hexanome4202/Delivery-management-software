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
	}
}
