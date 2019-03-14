package refactor_v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PointAcc {

	private UR[][] listUR;
	private double nbURUtilise;
	private List<User> listUsers;
	private int temps;
	private Scheduler sh;
	private int debitGlobal;
	private int energieGlobale;
	private Performance p;

	//constructeur
	public PointAcc(Scheduler sh) {
		this.energieGlobale =0;
		this.debitGlobal = 0;
		this.nbURUtilise = 0;
		this.sh = sh;
		this.temps = 0;
		sh.setPa(this);
		this.listUR = new UR[5][128];
		this.listUsers = new ArrayList<>();
		this.p = new Performance(sh);


	}

	/**
	 * Crée un user et un nombre de paquet aléatoire
	 * @param id : identifiant du nouvel utilisateur
	 */
	void createUserEtPaquet(int id){
		int nbPaquets = 8;
		Random random = new Random();
		boolean proche_ou_loin = random.nextBoolean();
		User user = new User(id,this,proche_ou_loin);
		Random var = new Random();
		boolean varPaquet = var.nextBoolean();
		int variance = var.nextInt(9);
		if(varPaquet){
			nbPaquets = nbPaquets + variance;
		}else{
			nbPaquets = nbPaquets - variance;
		}
		for(int i =0; i<nbPaquets; i++){
			user.createnewPacket();
		}
		user.setEmetPaquets(true);
		this.listUsers.add(user);
	}

	/**
	 * Ajoute un nombre de paquet aléatoire à chaque user
	 */
	private void userCreatePacket() {
		Random random = new Random();
		for (User user : this.listUsers) {
			int nbPaquets = 8;
			boolean varPaquet = random.nextBoolean();
			int variance = random.nextInt(9);
			if(varPaquet){
				nbPaquets = nbPaquets + variance;
			}else{
				nbPaquets = nbPaquets - variance;
			}
			for(int i =0; i<nbPaquets; i++){
				user.createnewPacket();
			}
		}
	}

	/**
	 * Fonction principale d'une simulation
	 * @param tempsDeSimulation : temps de simulation
	 */
	void simulation(int tempsDeSimulation){
		this.temps = 0;
		while(tempsDeSimulation > 0){
			sh.scheduling();
			clearUR();
			resetTabdebits();
			this.temps +=1;
			userCreatePacket();
			tempsDeSimulation-=1;
		}
	}

	/**
	 * Traite les données à chaque temps
	 * @param tempsDeSimulation : durée d'une simulation
	 */
	void traitementDonnees(double tempsDeSimulation){
		int nbUser = this.listUsers.size();
		//Debit
		double debit = debitGlobal/tempsDeSimulation;
		p.performanceDebit(nbUser,debit);
		//Energie
		double energie = energieGlobale/tempsDeSimulation;
		p.performanceEnergie(nbUser,energie);
		//TauxURUtilisé
		double urUse = (nbURUtilise/((5*128)*tempsDeSimulation))*100;
		p.performanceURUtilise(nbUser,urUse);
		//Latence
		p.performanceLatence(nbUser);
	}

	//Méthodes de reset

	/**
	 * Supprime tous les sous objets crées (User,Paquet,UR) pour liberer le GC
	 * Et reinitialise les valeurs des données utiles pour les graphs
	 */
	void resetGeneral(){
		resetValeurs();
		resetUser();
		clearUR();
	}

	private void resetUser(){
		for(User u : listUsers){
			u.softReset();
		}
	}

	private void resetTabdebits(){
		for(User u : listUsers){
			u.remplitDebit(u.getTabDebits());
		}
	}
	private void resetValeurs(){
		setDebitGlobal(0);
		setEnergieGlobale(0);
		setNbURUtilise(0);
		setTemps(0);
	}

	/**
	 * Reset le boolean qui permet de savoir si un user a deja
	 * eut la parole sur une colonne OFDM
	 */
	void resetEnergieParTimeSLot(int i, int j){
		for(int c = 0 ; c < j ; c++){
			listUR[i][c].getUser().setTimeSlotAttribue(false);
		}
	}

	/**
	 * Reinitialise le tableau d'UR
	 */
	private void clearUR(){
		listUR = new UR[5][128];
	}


	//Méthodes pour performances

	void sumURUtilise(){
		this.nbURUtilise +=1;
	}
	void sumEnergieGlobale(int energie) {
		this.energieGlobale+= energie;
	}
	void sumDebitGlobal(int debit) {
		this.debitGlobal += debit;
	}

	//Getters & Setters
	List<User> getListUsers() {
		return listUsers;
	}

	public int getEnergieGlobale() {
		return energieGlobale;
	}

	private void setEnergieGlobale(int energieGlobale) {
		this.energieGlobale = energieGlobale;
	}

	public int getDebitGlobal() {
		return debitGlobal;
	}

	private void setDebitGlobal(int debitGlobal) {
		this.debitGlobal = debitGlobal;
	}

	UR[][] getListUR(){
		return listUR;
	}

	void addListUr(User user, int debit, int i, int j){
		listUR[i][j] = new UR(user,debit);
	}

	int getTemps() {
		return temps;
	}

	private void setTemps(int temps) {
		this.temps = temps;
	}

	public double getNbURUtilise() {
		return nbURUtilise;
	}

	private void setNbURUtilise(double nbURUtilise) {
		this.nbURUtilise = nbURUtilise;
	}


}
