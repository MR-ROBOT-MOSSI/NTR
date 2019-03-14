package refactor_v2;

public class Packet {

	//taille d'un paquet
	private static final int TAILLE_PACKET = 100;
	//date de creation d'un paquet
	private int tempscreation;

	//dtae de fin de transmission d'un paquet
	private int tempsfinenvoi;
	//nombre de bits restant à être transmis dans un paquet
	private int nbbitsrestant;
	private User user;

	//constructeur
	Packet(User user, int temps) {
		// TODO Auto-generated constructor stub
		this.nbbitsrestant = TAILLE_PACKET;
		this.tempscreation = temps;
		this.user = user;
	}

	//remise à zéro des valeurs
	void resetGeneral(){
		this.user = null;
		System.gc();
	}
	//getter and setter
	public int getTempscreation() {
		return this.tempscreation;
	}

	public void setTempscreation(int tempscreation) {
		this.tempscreation = tempscreation;
	}


	int getTempsfinenvoi() {
		return this.tempsfinenvoi;
	}

	void setTempsfinenvoi(int tempsfinenvoi) {
		this.tempsfinenvoi = tempsfinenvoi;
	}

	int getNbbitsrestant() {
		return this.nbbitsrestant;
	}

	void setNbbitsrestant(int nbbitsrestant) {
		this.nbbitsrestant = nbbitsrestant;
	}

}
