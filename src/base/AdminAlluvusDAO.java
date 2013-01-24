package base;

import java.util.Date;


public class AdminAlluvusDAO {

	private int admin_alluvus_id;
	private int ylemus_yksus_ID;
	private int alluv_yksus_ID;
	private String alates;
	private String kuni;
	private String kommentaar;
	private String avaja;
	private Date avatud;
	private String muutuja;
	private Date muudetud;
	private String sulgeja;
	private Date suletud;
	private String riigi_admin_yksuse_nimi;
	
	


	public int getAdmin_alluvus_id() {
		return admin_alluvus_id;
	}

	public void setAdmin_alluvus_id(int admin_alluvus_id) {
		this.admin_alluvus_id = admin_alluvus_id;
	}

	
	public int getYlemus_yksus_ID() {
		return ylemus_yksus_ID;
	}

	public void setYlemus_yksus_ID(int ylemus_yksus_ID) {
		this.ylemus_yksus_ID = ylemus_yksus_ID;
	}
	
	public int getAlluv_yksus_ID() {
		return alluv_yksus_ID;
	}

	public void setAlluv_yksus_ID(int alluv_yksus_ID) {
		this.alluv_yksus_ID = alluv_yksus_ID;
	}
	
	public String getAlates() {
		return alates;
	}

	public void setAlates(String alates) {
		this.alates = alates;
	}

	public String getKuni() {
		return kuni;
	}

	public void setKuni(String kuni) {
		this.kuni = kuni;
	}
	
	public String getKommentaar() {
		return kommentaar;
	}

	public void setKommentaar(String kommentaar) {
		this.kommentaar = kommentaar;
	}

	public String getAvaja() {
		return avaja;
	}

	public void setAvaja(String avaja) {
		this.avaja = avaja;
	}

	public Date getAvatud() {
		return avatud;
	}

	public void setAvatud(Date avatud) {
		this.avatud = avatud;
	}

	public String getMuutuja() {
		return muutuja;
	}

	public void setMuutuja(String muutuja) {
		this.muutuja = muutuja;
	}

	public Date getMuudetud() {
		return muudetud;
	}

	public void setMuudetud(Date muudetud) {
		this.muudetud = muudetud;
	}

	public String getSulgeja() {
		return sulgeja;
	}

	public void setSulgeja(String sulgeja) {
		this.sulgeja = sulgeja;
	}

	public Date getSuletud() {
		return suletud;
	}

	public void setSuletud(Date suletud) {
		this.suletud = suletud;
	}

	public String getRiigi_admin_yksuse_nimi() {
		return riigi_admin_yksuse_nimi;
	}

	public void setRiigi_admin_yksuse_nimi(String riigi_admin_yksuse_nimi) {
		this.riigi_admin_yksuse_nimi = riigi_admin_yksuse_nimi;
	}
	
	
	@Override
	public String toString() {
		return "ID: " + alluv_yksus_ID + "   " + "komentaar: " + kommentaar + "   ";
	}


}


