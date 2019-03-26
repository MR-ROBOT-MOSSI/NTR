package refactor_v2;

import java.util.*;
import java.util.Random;

public class User {
	
	//variable global 
	//definir le debit pour les utilisateurs proches
	private static final double DEBIT_PROCHE = 8;
	//definir le debit pour les utilisateurs loin
	private static final double DEBIT_LOIN = 6;
	//l'identifiant de l'utilisateur
	private int id;
	//vérificateur si l'utilisateur est proche ou loin
	private boolean distance;
	//init le point d'acces auquel appartient l'utilisateur
	private PointAcc pointacc;
	//liste des paquets d'un utilisateur ayant été traiter
	private List<Packet> packet;
	//table de debit
	private double [][] tabDebits;
	//debit attribut a chaque temps
	private double debitParTemps;
	//identificateur de TimeSlot
	private boolean timeSlotAttribue;//pour savoir si on met 10 ou 1
	private boolean emetPaquets;
	private int energie;
	//buffer contant les paquets restant à être traiter
	private Deque<Packet> buf;
	private double debitglobal;
	private int pdor;

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
		this.tabDebits = new double[5][128];
		this.timeSlotAttribue = false;
		remplitDebit(tabDebits);
		this.energie = 0;
		this.debitParTemps = 0;
		this.pdor = 0;
	}

	// Modif
	//reset des valeurs pour les T simulation
	void softReset(){
		this.energie = 0;
		this.debitParTemps = 0;
		packet = new ArrayList<>();
		buf = new LinkedList<>();
		this.pdor = 0;

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
	void sumDebitParTemps(double debit) {
		this.debitParTemps += debit;
	}
	//get de la somme des debit de l'utilisateur
	public double getDebitParTemps() {
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

	double[][] getTabDebits(){
		return tabDebits;
	}

	boolean getTimeSlotAttribue(){
		return timeSlotAttribue;
	}

	void setTimeSlotAttribue(Boolean b){
		this.timeSlotAttribue = b;
	}
	
	//remplissage de la table de debit
	void remplitDebit(double[][] tab) {
		double debit;
		Random random = new Random();
		for(int i = 0; i< tab.length; i++){
			for(int j = 0; j < tab[0].length ; j++){
				boolean constructive_destructive = random.nextBoolean();
				double generator = random.nextDouble();
				double variance;
				if(debitglobal == DEBIT_LOIN){
					variance = DEBIT_LOIN * generator;
				}else{
					variance = DEBIT_PROCHE * generator;
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
	boolean getDistance() {
		return distance;
	}
	//get le paquet en-tete de file d'attente dans notre buffer
	private Packet getPacketCourant() {
		return buf.peek();
	}
	//verification du traitement d'un paquet soustraire (bits) ou remove (packetTraitementFini) 
	void verifPacket(double debitCourant) {
		Packet packet = this.getPacketCourant();
		if(packet != null) {
			double bitsrestant = packet.getNbbitsrestant() - debitCourant;
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
		Packet p = getPacketCourant();
		p.setTempsfinenvoi(pointacc.getTemps());
		if(p.getTempsfinenvoi()-p.getTempscreation() > 15){
			this.pdor +=1;
		}
		this.packet.add(p);
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

	private Deque<Packet> getBuf() {return this.buf;}

	/**
	 * get the debit value  at position i,j in debitTable
	 * @param i cellule i ,__
	 * @param j cellelue __, j
	 * @return debit value
	 */
	double getDebit(int i, int j) {
		return tabDebits[i][j] ;
	}

	/**
	 * get the debit value  at position i,j in debitTable
	 * @return number of outdated packet
	 */
	double getPDOR() {
		return this.pdor;
	}

	double calcPdor(){
		if(packet.size() == 0){
			return 0;
		}else{
			return (double)(this.pdor/packet.size());
		}

	}
}
