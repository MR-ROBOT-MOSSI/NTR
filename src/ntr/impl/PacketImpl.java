package ntr.impl;

public class PacketImpl  {
	
	private int taille ;
	private int tempsCreation ;
	private int tempsDebutEnvoi ;
	private int dureeEnvoi ;
	private UserImpl destinaire ;
	
	public int getTaille() {
		return taille;
	}
	public void setTaille(int taille) {
		this.taille = taille;
	}
	
	public int getTempsCreation() {
		return tempsCreation;
	}
	public void setTempsCreation(int tempsCreation) {
		this.tempsCreation = tempsCreation;
	}
	
	public int getTempsDebutEnvoi() {
		return tempsDebutEnvoi;
	}
	public void setTempsDebutEnvoi(int tempsDebutEnvoi) {
		this.tempsDebutEnvoi = tempsDebutEnvoi;
	}
	
	public int getDureeEnvoi() {
		return dureeEnvoi;
	}
	public void setDureeEnvoi(int dureeEnvoi) {
		this.dureeEnvoi = dureeEnvoi;
	}
	
	public UserImpl getDestinaire() {
		return destinaire;
	}

	public void setDestinaire(UserImpl destinaire) {
		this.destinaire = destinaire;
	}
}