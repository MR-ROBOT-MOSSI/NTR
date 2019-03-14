package refactor_v2;

import java.util.*;
import java.util.Random;

public class User {
	
	//variable global 
	//definir le debit pour les utilisateurs proches
	private static final int DEBIT_PROCHE = 8;
	//definir le debit pour les utilisateurs loin
	private static final int DEBIT_LOIN = 6;
	//l'identifiant de l'utilisateur
	private int id;
	//vérificateur si l'utilisateur est proche ou loin
	private boolean distance;
	//init le point d'acces auquel appartient l'utilisateur
	private PointAcc pointacc;
	//liste des paquets d'un utilisateur ayant été traiter
	private List<Packet> packet;
	//table de debit
	private int [][] tabDebits;
	//debit attribut a chaque temps
	private int debitParTemps;
	//identificateur de TimeSlot
	private boolean timeSlotAttribue;//pour savoir si on met 10 ou 1
	private boolean emetPaquets;
	private int energie;
	//buffer contant les paquets restant à être traiter
	private Deque<Packet> buf;
	private int debitglobal;

	public User(int j, PointAcc pointAcc, boolean b) {
		this.id = j;
		this.packet = new ArrayList<>();
		this.distance = b;
		this.pointacc = pointAcc;
		buf = new LinkedList<>();
		if(distance) {
			debitglobal = DEBIT_PROCHE;
		}else {
			debitglobal = DEBIT_LOIN;
		}
		this.emetPaquets = false;
		this.tabDebits = new int[5][128];
		this.timeSlotAttribue = false;
		remplitDebit(tabDebits);
		this.energie = 0;
		this.debitParTemps = 0;
	}

	// Modif
	//reset des valeurs pour les T simulation
	public void softReset(){
		this.energie = 0;
		this.debitParTemps = 0;
		packet = new ArrayList<Packet>();
		buf = new LinkedList<>();

	}
	void resetGeneral(){
		for(Packet p : packet){
			p.resetGeneral();
		}
		for(Packet p : buf){
			p.resetGeneral();
		}
		buf.clear();
		packet.clear();
		System.gc();

	}
	//somme des debit attribut à l'utilisateur au diff instant
	void sumDebitParTemps(int debit) {
		this.debitParTemps += debit;
	}
	//get de la somme des debit de l'utilisateur
	public int getDebitParTemps() {
		return debitParTemps;
	}

	public boolean getEmetPaquets(){
		return this.emetPaquets;
	}
	//get du coup énergétique de l'utilisateur
	public int getEnergie() {
		return energie;
	}
	//setter coup énergetique
	public void setEnergie(int energie){
		this.energie = energie;
	}
	//somme des coups energetique sur les diff slottime
	void sumEnergie(int energie){
		this.energie += energie;
	}

	int[][] getTabDebits(){
		return tabDebits;
	}

	boolean getTimeSlotAttribue(){
		return timeSlotAttribue;
	}

	void setTimeSlotAttribue(Boolean b){
		this.timeSlotAttribue = b;
	}
	
	//remplissage de la table de debit
	void remplitDebit(int[][] tab) {
		int debit;
		Random random = new Random();
		for(int i = 0; i< tab.length; i++){
			for(int j = 0; j < tab[0].length ; j++){
				boolean constructive_destructive = random.nextBoolean();
				int variance;
				if(debitglobal == DEBIT_LOIN){
					variance = random.nextInt(7);
				}else{
					variance = random.nextInt(9);
				}
				//Phase destructive ou constructive
				if(constructive_destructive){
					debit = this.debitglobal + variance;
				}else{
					debit = this.debitglobal - variance;
				}
				tabDebits[i][j] = debit;
			}
		}
	}

	//Modif
	//creation d'u nouveau paquet avec sa date de creation
	void createnewPacket() {
		buf.add(new Packet(this, pointacc.getTemps()));
	}

	private boolean randomBoolean() {
		return (Math.random() < 0.15);
	}
	

	public int getId() {
		return this.id;
	}

	//bool permettant de savoir si proche ou loin
	public boolean getDistance() {
		return distance;
	}
	//get le paquet en-tete de file d'attente dans notre buffer
	private Packet getPacketCourant() {
		return buf.peek();
	}
	//verification du traitement d'un paquet soustraire (bits) ou remove (packetTraitementFini) 
	void verifPacket(int debitCourant) {
		Packet packet = this.getPacketCourant();
		if(packet != null) {
			int bitsrestant = packet.getNbbitsrestant() - debitCourant;
			if(bitsrestant <= 0) {
				packet.setNbbitsrestant(0);
				this.packetTraitementFini();
			}else {
				packet.setNbbitsrestant(bitsrestant);
			}
		}
	}

	boolean checkTransmission(){
		this.emetPaquets = !buf.isEmpty();
		return emetPaquets;
	}
	//remove d'un paquet de la liste d'attente
	private void packetTraitementFini() {
		getPacketCourant().setTempsfinenvoi(pointacc.getTemps());
		this.packet.add(getPacketCourant());
		buf.removeFirst();
	}

	void setEmetPaquets(boolean emetPaquets) {
		this.emetPaquets = emetPaquets;
	}

	public void supprimerPacket() {
		this.packet.remove(getPacketCourant());
	}

	List<Packet> getPacket(){
		 return this.packet;
	 }

	Deque<Packet> getBuf() {return this.buf;}

	/**
	 * get the debit value  at position i,j in debitTable
	 * @param i cellule i ,__
	 * @param j cellelue __, j
	 * @return debit value
	 */
	int getDebit(int i, int j) {
		return tabDebits[i][j] ;
	}
}
