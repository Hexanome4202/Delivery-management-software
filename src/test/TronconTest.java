package test;

import static org.junit.Assert.*;
import modele.Troncon;

import org.junit.Test;

public class TronconTest {

	@Test
	public void testGetTemps() {
		Troncon troncon = new Troncon(3, 5, "");
		assertEquals("Erreur - Le temps du troncon devrait être 5/3", 5/3, troncon.getTemps(), 0.01);
	}

}
