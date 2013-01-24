package base;

import java.util.Date;


public class RiigiAdminYksuseLiik {
	public int riigi_admin_yksuse_lik_id;
	public int alluv_id;
	public String kood;
	public String nimetus;
	public String kommentaar;
	public Date alates;
	public Date kuni;
	public String avaja;
	public Date avatud;
	public String muutja;
	public Date muudetud;
	public String sulgeja;
	public Date suletud;
	

	public int getRiigi_admin_yksuse_lik_id() {
		return riigi_admin_yksuse_lik_id;
	}

	public void setRiigi_admin_yksuse_lik_id(int riigi_admin_yksuse_lik_id) {
		this.riigi_admin_yksuse_lik_id = riigi_admin_yksuse_lik_id;
	}

	public int getAlluv_id() {
		return alluv_id;
	}

	public void setAlluv_id(int alluv_id) {
		this.alluv_id = alluv_id;
	}
	
	
	
	public String getKood() {
		return kood;
	}

	public void setKood(String kood) {
		this.kood = kood;
	}
	
	

	public String getNimetus() {
		return nimetus;
	}

	public void setNimetus(String nimetus) {
		this.nimetus = nimetus;
	}

	public String getKommentaar() {
		return kommentaar;
	}

	public void setKommentaar(String kommentaar) {
		this.kommentaar = kommentaar;
	}

	public Date getAlates() {
		return alates;
	}

	public void setAlates(Date alates) {
		this.alates = alates;
	}

	public Date getKuni() {
		return kuni;
	}

	public void setKuni(Date kuni) {
		this.kuni = kuni;
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

	public String getMuutja() {
		return muutja;
	}

	public void setMuutja(String muutja) {
		this.muutja = muutja;
	}

	public Date getMuudetud() {
		return muudetud;
	}

	public void setMuudetud(Date muudetud) {
		this.alates = muudetud;
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

	@Override
	public String toString() {
		return "ID: " + riigi_admin_yksuse_lik_id + "   " + "Code: " + kood + "   ";


	
	}
}
