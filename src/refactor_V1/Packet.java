package refactor_V1;

public class Packet {

	static final int TAILLE_PACKET = 62;
	private int tempscreation;
	private int tempsdebutenvoi;
	private int tempsfinenvoi;
	private int nbbistrestant;
	private User user;
	
	public Packet(User user, int temps) {
		// TODO Auto-generated constructor stub
		this.setNbbistrestant(TAILLE_PACKET);
		this.setTempscreation(temps);
		this.setUser(user);
	}

	public int getPacketNbBitsRestant() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setPacketNbBitsRestant(int i) {
		// TODO Auto-generated method stub
		
	}

	public void setTransmissionFini(int temps) {
		// TODO Auto-generated method stub
		
	}

	public int getTempscreation() {
		return tempscreation;
	}

	public void setTempscreation(int tempscreation) {
		this.tempscreation = tempscreation;
	}

	public int getTempsdebutenvoi() {
		return tempsdebutenvoi;
	}

	public void setTempsdebutenvoi(int tempsdebutenvoi) {
		this.tempsdebutenvoi = tempsdebutenvoi;
	}

	public int getTempsfinenvoi() {
		return tempsfinenvoi;
	}

	public void setTempsfinenvoi(int tempsfinenvoi) {
		this.tempsfinenvoi = tempsfinenvoi;
	}

	public int getNbbistrestant() {
		return nbbistrestant;
	}

	public void setNbbistrestant(int nbbistrestant) {
		this.nbbistrestant = nbbistrestant;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
