package model;

/**
 * @author hakka
 */

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ValuuttaTest {

	private Valuutta valuutta;
	private final static double DELTA = 0.001;

	@Before
	public void setUp() {
		valuutta = new Valuutta("EUR", 1.0, "Euroopan euro");
	}

	@Test
	public void testGetTunnus() {
		assertEquals("Valuutan tunnus väärin", "EUR", valuutta.getTunnus());
	}

	@Test
	public void testGetNimi() {
		assertEquals("Valuutan nimi väärin", "Euroopan euro", valuutta.getNimi());
	}

	@Test
	public void testGetVaihtokurssi() {
		assertEquals("Valuutan vaihtoarvo väärin", 1.0, valuutta.getVaihtokurssi(), DELTA);
	}

	@Test
	public void testSetTunnus() {
		valuutta.setTunnus("EURO");
		assertEquals("Valuutan tunnuksen asetus ei onnistunut", "EURO", valuutta.getTunnus());
	}

	@Test
	public void testSetNimi() {
		valuutta.setNimi("Euroopan valuutta");
		assertEquals("Valuutan nimen asetus ei onnistunut", "Euroopan valuutta", valuutta.getNimi());
	}

	@Test
	public void testSetVaihtokurssi() {
		valuutta.setVaihtokurssi(1.01);
		assertEquals("Valuutan vaihtokurssin päivitys väärin", 1.01, valuutta.getVaihtokurssi(), DELTA);
	}
}
