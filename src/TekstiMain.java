
import java.util.Scanner;
import model.*;

public class TekstiMain {
	static ValuuttaAccessObject valuuttaDAO = new ValuuttaAccessObject();
	static Scanner scanner = new Scanner(System.in);
	private static Valuutta valuutta;
	private static Valuutta[] valuutat;

	public static void listaaValuutat() {
		valuutat = valuuttaDAO.readValuutat();

		for (Valuutta v : valuutat) {
			System.out.println(v.getTunnus() + ", " + v.getVaihtokurssi() + ", " + v.getNimi());
		}
	}

	public static void lisääValuutta() {

		System.out.println("Anna valuutan tunnus:");
		String tunnus = scanner.nextLine();

		System.out.println("Anna valuutan vaihtokurssi:");
		double vaihtokurssi = Double.parseDouble(scanner.nextLine());

		System.out.println("Anna valuutan nimi:");
		String nimi = scanner.nextLine();

		valuutta = new Valuutta(tunnus, vaihtokurssi, nimi);

		if (valuuttaDAO.createValuutta(valuutta)) {
			System.out.println("Valuutta lisätty tietokantaan.");
		} else {
			System.out.println("Valuutan lisääminen ei onnistunut.");
		}

	}

	public static void päivitäValuutta() {
		System.out.println("Anna muutettavan valuutan tunnus:");
		String tunnus = scanner.nextLine().toUpperCase();

		valuutta = valuuttaDAO.readValuutta(tunnus);

		System.out.println("Haluatko muuttaa valuutan " + valuutta.getNimi() + 
				"nimen, vaihtokurssin, vai molemmat? (n/v/m)");
		char vastaus = scanner.nextLine().toLowerCase().charAt(0);

		switch (vastaus) {
		case 'n':
			System.out.println("Syötä uusi nimi:");
			valuutta.setNimi(scanner.nextLine());
			break;
		case 'v':
			System.out.println("Syötä uusi vaihtokurssi:");
			try {
				valuutta.setVaihtokurssi(Double.parseDouble(scanner.nextLine()));
			} catch (NumberFormatException nfe) {
				System.err.println("Syötä vain numeroita.");
			}
			break;
		case 'm':
			System.out.println("Syötä uusi nimi:");
			valuutta.setNimi(scanner.nextLine());

			System.out.println("Syötä uusi vaihtokurssi:");
			try {
				valuutta.setVaihtokurssi(Double.parseDouble(scanner.nextLine()));
			} catch (NumberFormatException nfe) {
				System.err.println("Syötä vain numeroita.");
			}
			break;
		default:
			System.out.println("Jokin meni pieleen. Yritä uudestaan.");
		}

		if (valuuttaDAO.updateValuutta(valuutta)) {
			System.out.println("Data päivitetty.");
		} else {
			System.out.println("Datan päivitys epäonnistui.");
		}

	}

	public static void poistaValuutta() {
		System.out.println("Poistettavan valuutan tunnus:");
		String tunnus = scanner.nextLine().toUpperCase();

		if (valuuttaDAO.deleteValuutta(tunnus)) {
			System.out.println("Data poistettu.");
		} else {
			System.out.println("Datan poistaminen ei onnistunut.");
		}
	}

	public static void main(String[] args) {
		char valinta;
		final char CREATE = 'C', READ = 'R', UPDATE = 'U', DELETE = 'D', QUIT = 'Q';

		do {

			System.out.println("C: Lisää uusi valuutta tietokantaan.\n"
					+ "R: Listaa tietokannassa olevien valuuttojen tiedot.\n"
					+ "U: Päivitä valuutan vaihtokurssi tietokantaan.\n"
					+ "D: Poista valuutta tietokannasta.\n"
					+ "Q: Lopetus.");

			valinta = (scanner.nextLine().toUpperCase()).charAt(0);
			switch (valinta) {
			case CREATE:
				lisääValuutta();
				break;
			case READ:
				listaaValuutat();
				break;
			case UPDATE:
				päivitäValuutta();
				break;
			case DELETE:
				poistaValuutta();
				break;
			}
		} while (valinta != QUIT);
	}
}
