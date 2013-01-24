package base;

import java.util.Date;
import java.util.List;

public class VoimalikAlluvus {

	private int riigi_admin_yksuse_lik;
	private int voimalik_alluv_lik_ID;
	private String kommentaar;
	private String avaja;
	private Date avatud;
	private String muutuja;
	private Date muudetud;
	private String sulgeja;
	private Date suletud;
	
	private List<VoimalikAlluvus> alluvused;

	public int getRiigi_admin_yksuse_lik() {
		return riigi_admin_yksuse_lik;
	}

	public void setRiigi_admin_yksuse_lik(int riigi_admin_yksuse_lik) {
		this.riigi_admin_yksuse_lik = riigi_admin_yksuse_lik;
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

	public String getmuutuja() {
		return muutuja;
	}

	public void setmuutuja(String muutuja) {
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

	public List<VoimalikAlluvus> getAlluvused() {
		return alluvused;
	}
	public void setAlluvused(List<VoimalikAlluvus> alluvused) {
		this.alluvused = alluvused;
	}
	
	
	@Override
	public String toString() {
		return "ID: " + riigi_admin_yksuse_lik + "   " + "komentaar: " + kommentaar + "   ";
	}

	public int getVoimalik_alluv_lik_ID() {
		return voimalik_alluv_lik_ID;
	}

	public void setVoimalik_alluv_lik_ID(int voimalik_alluv_lik_ID) {
		this.voimalik_alluv_lik_ID = voimalik_alluv_lik_ID;
	}
}


