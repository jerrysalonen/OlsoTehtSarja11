package model;

/**
 * @author hakka
 */

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Test;

public class ValuuttaAccessObjectTest {

	private ValuuttaAccessObject valuuttaDAO = new ValuuttaAccessObject();

	private final String tunnus = "TST"; // Testauksessa käytettävän valuutan tunnus
	private final double vaihtokurssi = 2017.100;
	private final String nimi = "Juhlavuoden testausmarkka";
	private Valuutta valuutta = new Valuutta(tunnus, vaihtokurssi, nimi);
	private final double DELTA = 0.001;

	// Koska tässä käytetään oikeaa tietokantaa, kaikki testeissä tehdyt muutokset
	// pitää tietysti perua, siksi AINA lopuksi deleteValuutta()
	@After
	public void lopputoimet() {
		valuuttaDAO.deleteValuutta(tunnus);
	}

	// Kaikki testit on ohjelmoitu samaan metodiin, jotta ne tehdään juuri halutussa järjestyksessä.
	// Jos joku testi ei mene läpi, tämän testimetodin suoritus päättyy heti.
	@Test
	public void testDAO() {

		// Lisää valuutta
		assertTrue("createValuutta(): Uuden valuutan lisääminen ei onnistu.",
				valuuttaDAO.createValuutta(valuutta));
		assertFalse("createValuutta(): Saman valuutan pystyy lisäämään kahteen kertaan.",
				valuuttaDAO.createValuutta(valuutta));

		// Haun tulee nyt onnistua ja kenttien tulee tietenkin olla asetettu oikein
		assertTrue("readValuutta(): Juuri lisätyn valuutan hakeminen ei onnistunut",
				(valuutta = valuuttaDAO.readValuutta(tunnus)) != null);
		assertEquals("readValuutta(): Valuutan tunnus väärin.",
				tunnus, valuutta.getTunnus());
		assertEquals("readValuutta(): Valuutan vaihtoarvo värin.",
				vaihtokurssi, valuutta.getVaihtokurssi(), DELTA);
		assertEquals("readValuutta(): Valuutan nimi väärin.",
				nimi, valuutta.getNimi());

		// Vaihtokurssin muutoksen tulee onnistua
		valuutta.setVaihtokurssi(2017.1206);
		assertTrue("updateValuutta(): Juuri lisätyn valuutan vaihtokurssin päivitys ei onnistunut.",
				valuuttaDAO.updateValuutta(valuutta));
		valuutta = valuuttaDAO.readValuutta(tunnus);
		assertEquals("updateValuutta(): Vaihtokurssin arvo väärin.",
				2017.1206, valuutta.getVaihtokurssi(), DELTA);

		// Valuutan nimen muutoksen tulee onnistua
		valuutta.setNimi("Suomen testivaluutta");
		assertTrue("updateValuutta(): Juuri lisätyn valuutan nimen päivitys ei onnistunut.",
				valuuttaDAO.updateValuutta(valuutta));
		valuutta = valuuttaDAO.readValuutta(tunnus);
		assertEquals("updateValuutta(): Valuutan nimi väärin.",
				"Suomen testivaluutta", valuutta.getNimi());

		// Testissä lisätyn valuutan poiston tulee onnistua
		assertTrue("deleteValuutta(): Valuutan poisto ei onnistunut.",
				valuuttaDAO.deleteValuutta(tunnus));
		assertTrue("deleteValuutat(): Valuutan poisto ei onnistunut - valuutta voitiin hakea tietokannasta.",
				valuuttaDAO.readValuutta(tunnus) == null);

		// Olemattoman valuutan poiston tulee "epäonnistua"
		assertFalse("deleteValuutta(): Väittää poistaneensa olemattoman valuutan.",
				valuuttaDAO.deleteValuutta("PUPPU"));
	}
}
