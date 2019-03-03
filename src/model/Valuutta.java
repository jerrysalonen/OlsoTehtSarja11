package model;

/**
 * @author Jerry Salonen
 */

public class Valuutta {
	
	private String tunnus;
	private double vaihtokurssi;
	private String nimi;
	
	public Valuutta(String tunnus, double vaihtokurssi, String nimi) {
		this.tunnus = tunnus;
		this.vaihtokurssi = vaihtokurssi;
		this.nimi = nimi;
	}

	public String getTunnus() {
		return tunnus;
	}
	
	public double getVaihtokurssi() {
		return vaihtokurssi;
	}
	
	public String getNimi() {
		return nimi;
	}

	public void setTunnus(String tunnus) {
		this.tunnus = tunnus;
	}

	public void setVaihtokurssi(double vaihtokurssi) {
		this.vaihtokurssi = vaihtokurssi;
	}
	
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}
}

