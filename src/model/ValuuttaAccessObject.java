package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ValuuttaAccessObject implements IValuuttaDAO {

	private Connection con;
	private Valuutta valuutta;
	Valuutta[] valuutat;
	private Scanner scanner = new Scanner(System.in);

	public ValuuttaAccessObject() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/valuutat", "root", "root");
		} catch (ClassNotFoundException e) {    
			System.err.println("JDBC-ajurin lataus epäonnistui");    
			System.exit(-1);
		} catch (SQLException sqle) {
			System.err.println("Yhteys tietokantaan epäonnistui.");
			sqle.printStackTrace();
			System.exit(-1);
		} catch (Exception e) {
			System.err.println("Virhe.");
			e.printStackTrace();
		}
	}

	@Override
	public boolean createValuutta(Valuutta valuutta) {

		try (PreparedStatement createStatement = 
				con.prepareStatement("INSERT INTO valuutta VALUES (?, ?, ?);")) {

			boolean hasValuutta = false;

			createStatement.setString(1, valuutta.getTunnus());
			createStatement.setDouble(2, valuutta.getVaihtokurssi());
			createStatement.setString(3, valuutta.getNimi());

			Valuutta[] valuutatTemp = readValuutat();
			for (Valuutta v : valuutatTemp) {
				if (v.getTunnus().equals(valuutta.getTunnus())) {
					hasValuutta = true;
				}
			}

			if (hasValuutta) {
				System.out.println("Valuutta on jo tietokannassa.");
				hasValuutta = false;
				return false;
			} else {
				createStatement.executeUpdate();
			}
			createStatement.close();
			return true;

		} catch (SQLException sqle) {
			do {
				System.err.println("Datan luominen epäonnistui.");
				sqle.printStackTrace();
			} while (sqle.getNextException() != null);
		} catch (Exception e) {
			System.out.println("Virhe");
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Valuutta readValuutta(String tunnus) {

		try (PreparedStatement readValuuttaStatement = 
				con.prepareStatement("SELECT * FROM valuutta WHERE tunnus = ?;")) {

			readValuuttaStatement.setString(1, tunnus);
			ResultSet rSet = readValuuttaStatement.executeQuery();

			if (rSet.next()) {
				String rsTunnus = rSet.getString("tunnus");
				double rsKurssi = rSet.getDouble("vaihtokurssi");
				String rsNimi = rSet.getString("nimi");
				valuutta = new Valuutta(rsTunnus, rsKurssi, rsNimi);
			} else {
				return null;
			}

			readValuuttaStatement.close();

		} catch (SQLException sqle) {
			do {
				System.err.println("Datan hakeminen epäonnistui.");
				sqle.printStackTrace();
			} while (sqle.getNextException() != null);
		}
		return valuutta;
	}

	@Override
	public Valuutta[] readValuutat() {

		try (PreparedStatement readValuuttaStatement = 
				con.prepareStatement("SELECT * FROM valuutta")) {

			int rows = 0;
			int index = 0;

			ResultSet rSet = readValuuttaStatement.executeQuery();

			while (rSet.next()) {
				rows++;
			}

			rSet = readValuuttaStatement.executeQuery();
			valuutat = new Valuutta[rows];

			while (rSet.next()) {
				String rsTunnus = rSet.getString("tunnus");
				double rsKurssi = rSet.getDouble("vaihtokurssi");
				String rsNimi = rSet.getString("nimi");
				valuutat[index] = new Valuutta(rsTunnus, rsKurssi, rsNimi);
				index++;
			}
			readValuuttaStatement.close();

		} catch (SQLException sqle) {
			do {
				System.err.println("Datan hakeminen epäonnistui.");
				sqle.printStackTrace();
			} while (sqle.getNextException() != null);
		}
		return valuutat;
	}

	@Override
	public boolean updateValuutta(Valuutta valuutta) {
		try (PreparedStatement updateStatement = 
				con.prepareStatement("UPDATE valuutta SET nimi = ?, vaihtokurssi = ? WHERE tunnus = ?;")) {


			updateStatement.setString(1, valuutta.getNimi());
			updateStatement.setDouble(2, valuutta.getVaihtokurssi());
			updateStatement.setString(3, valuutta.getTunnus());

			updateStatement.executeUpdate();

			updateStatement.close();

			return true;

		} catch (SQLException sqle) {
			do {
				System.err.println("Datan poistaminen epäonnistui.");
				sqle.printStackTrace();
			} while (sqle.getNextException() != null);
		} catch (Exception e) {
			System.err.println("Virhe.");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteValuutta(String tunnus) {
		try (PreparedStatement deleteStatement = 
				con.prepareStatement("DELETE FROM valuutta WHERE tunnus = ? LIMIT 1;")) {

			if (readValuutta(tunnus) != null) {
				deleteStatement.setString(1, tunnus);
				deleteStatement.executeUpdate();

				deleteStatement.close();

				return true;
			} else {
				System.out.println("Poistettavaa valuuttaa ei löytynyt.");
				return false;
			}

		} catch (SQLException sqle) {
			do {
				System.err.println("Datan poistaminen epäonnistui.");
				sqle.printStackTrace();
			} while (sqle.getNextException() != null);
		} catch (Exception e) {
			System.err.println("Virhe.");
			e.printStackTrace();
		}

		return false;
	}

}
