package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import modele.Itineraire;
import modele.Troncon;

import org.junit.Test;

/**
 * Classe de test de la classe <code>Itineraire</code>
 * 
 * @author remimartin
 *
 */
public class ItineraireTest {

	/**
	 * Teste le fonctionnement de la méthode <code>getTemps()</code>
	 */
	@Test
	public void testGetTemps() {
		Troncon t1 = new Troncon(1, 2, "");
		Troncon t2 = new Troncon(5, 10, "");
		ArrayList<Troncon> troncons = new ArrayList<Troncon>();
		troncons.add(t1);
		troncons.add(t2);
		Itineraire it = new Itineraire(null, null, troncons);
		assertEquals(4.0, it.getTemps(), 0.01);
	}

}
