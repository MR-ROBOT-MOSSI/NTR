package refactor_V1;

public class Packet {

	static final int TAILLE_PACKET = 100;
	private int tempscreation;
	private int tempsdebutenvoi;
	private int tempsfinenvoi;
	private int nbbistrestant;
	private User user;
	
	public Packet(User user, int temps) {
		// TODO Auto-generated constructor stub
		this.nbbistrestant = TAILLE_PACKET;
		this.tempscreation = temps;
		this.user = user;
	}

	public int getTempscreation() {
		return this.tempscreation;
	}

	public void setTempscreation(int tempscreation) {
		this.tempscreation = tempscreation;
	}

	public int getTempsdebutenvoi() {
		return this.tempsdebutenvoi;
	}

	public void setTempsdebutenvoi(int tempsdebutenvoi) {
		this.tempsdebutenvoi = tempsdebutenvoi;
	}

	public int getTempsfinenvoi() {
		return this.tempsfinenvoi;
	}

	public void setTempsfinenvoi(int tempsfinenvoi) {
		this.tempsfinenvoi = tempsfinenvoi;
	}

	public int getNbbistrestant() {
		return this.nbbistrestant;
	}

	public void setNbbistrestant(int nbbistrestant) {
		this.nbbistrestant = nbbistrestant;
	}

}
