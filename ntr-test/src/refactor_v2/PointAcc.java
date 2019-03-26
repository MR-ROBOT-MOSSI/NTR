package refactor_v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PointAcc {
	private static final int DEBIT_MOYEN  = 7;
	private UR[][] listUR;
	private double nbURUtilise;
	private List<User> listUsers;
	private double nbUrLoin;
	private double nbUrProche;
	private int temps;
	private Scheduler sh;
	private double debitGlobal;
	private double debitProche;
	private double debitLoin;
	private int energieGlobale;
	private int energieLoin;
	private int energieProche;
	private int dureeEmission;
	private int moyenneNbPaquets;
	private Performance p;

	//constructeur
	public PointAcc(Scheduler sh) {
		this.energieGlobale =0;
		this.debitGlobal = 0;
		this.nbURUtilise = 0;
		this.nbUrLoin = 0;
		this.nbUrProche = 0;
		this.energieLoin = 0;
		this.energieProche = 0;
		this.debitLoin = 0;
		this.debitProche =0;
		this.dureeEmission = 0;
		this.moyenneNbPaquets = 0;
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
		gestionPaquets();
		int nbPaquets1 = 0;
		int nbPaquets2 = 0;
		User user1 = new User(id,this,true);
		User user2 = new User(id+1,this,false);
		Random var1 = new Random();
		Random var2 = new Random();
		boolean varPaquet = var1.nextBoolean();
		boolean varPaquet2 = var2.nextBoolean();
		int variance1;
		int variance2;
		if(moyenneNbPaquets/2>0){
			variance1 = var1.nextInt(moyenneNbPaquets/2);
			variance2 = var2.nextInt(moyenneNbPaquets/2);
		}else{
			variance1 = var1.nextInt(1+moyenneNbPaquets/2);
			variance2 = var2.nextInt(1+moyenneNbPaquets/2);
		}
		if(varPaquet){
			nbPaquets1 = moyenneNbPaquets + variance1;
		}else{
			nbPaquets1 = moyenneNbPaquets - variance1;
		}
		if(varPaquet2){
			nbPaquets2 = moyenneNbPaquets + variance2;
		}else{
			nbPaquets2 = moyenneNbPaquets - variance2;
		}
		for(int i =0; i<nbPaquets1; i++){
			user1.createnewPacket();
		}
		for(int i=0; i< nbPaquets2; i++){
			user2.createnewPacket();
		}
		user2.setEmetPaquets(true);
		user1.setEmetPaquets(true);
		this.listUsers.add(user1);
		this.listUsers.add(user2);
	}
	private void gestionPaquets(){
		if(this.dureeEmission == 0){
			Random nbTic = new Random();
			//Duree d'emission entre 1 et 5 temps
			this.dureeEmission = 1+ nbTic.nextInt(5);
			Random nbPaquets = new Random();
			//Nombre de paquets moyen entre 0 et 2* Debit moyen
			this.moyenneNbPaquets = nbPaquets.nextInt(DEBIT_MOYEN *2);
		}

	}
	/**
	 * Ajoute un nombre de paquet aléatoire à chaque user
	 */
	private void userCreatePacket() {
		gestionPaquets();
		Random random = new Random();
		int nbPaquets;
		for (User user : this.listUsers) {
			boolean varPaquet = random.nextBoolean();
			int variance;
			if(moyenneNbPaquets/2 >0){
				variance = random.nextInt(moyenneNbPaquets/2);
			}else{
				variance = random.nextInt(1+moyenneNbPaquets/2);
			}

			if(varPaquet){
				nbPaquets = moyenneNbPaquets + variance;
			}else{
				nbPaquets = moyenneNbPaquets - variance;
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
	void simulation(double tempsDeSimulation){
		this.temps = 0;
		while(tempsDeSimulation > 0){
			sh.scheduling();
			clearUR();
			resetTabdebits();
			this.temps +=1;
			userCreatePacket();
			this.dureeEmission -= 1;
			tempsDeSimulation -=1;
		}
	}

	/**
	 * Traite les données à chaque temps
	 * @param tempsDeSimulation : durée d'une simulation
	 */
	void traitementDonnees(double tempsDeSimulation){
		int nbUser = this.listUsers.size();
		//Debit
		double debitL = debitLoin/tempsDeSimulation;
		double debitP = debitProche/tempsDeSimulation;
		double debit = debitGlobal/tempsDeSimulation;
		p.performanceDebit(nbUser,debit, debitP, debitL);
		//Energie
		double energieL = energieLoin/tempsDeSimulation;
		double energieP = energieProche/tempsDeSimulation;
		double energie = energieGlobale/tempsDeSimulation;
		p.performanceEnergie(nbUser,energie,energieP,energieL);
		//TauxURUtilisé
		double urUseProche = (nbUrProche/((5*128)*tempsDeSimulation))*100;
		double urUseLoin= (nbUrLoin/((5*128)*tempsDeSimulation))*100;
		double urUse = (nbURUtilise/((5*128)*tempsDeSimulation))*100;
		p.performanceURUtilise(nbUser,urUse,urUseProche,urUseLoin);
		//Latence
		p.performanceLatence(nbUser);
		//delai
		p.performanceDelai(nbUser);
		//efficacitéspectrale
		p.performanceEfSpectrale(nbUser);
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
		setDebitLoin(0);
		setDebitProche(0);
		setEnergieGlobale(0);
		setNbURUtilise(0);
		setNbUrProche(0);
		setNbUrLoin(0);
		setEnergieProche(0);
		setEnergieLoin(0);
		setTemps(0);
		this.dureeEmission = 0;
		this.moyenneNbPaquets = 0;
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
	void sumURUtiliseProche() {
		this.nbUrProche += 1;
	}
	void sumURUtiliseLoin() {
		this.nbUrLoin += 1;
	}
	void sumEnergieLoin(int energie){this.energieLoin +=energie;}
	void sumEnergieProche(int energie){this.energieProche +=energie;}
	void sumEnergieGlobale(int energie) {
		this.energieGlobale+= energie;
	}
	void sumDebitGlobal(double debit) {
		this.debitGlobal += debit;
	}
	void sumDebitProche(double debit) { this.debitProche += debit;}
	void sumDebitLoin(double debit){ this.debitLoin += debit;}
	//Getters & Setters
	List<User> getListUsers() {
		return listUsers;
	}

	private void setEnergieLoin(int energieLoin) {
		this.energieLoin = energieLoin;
	}

	private void setEnergieProche(int energieProche) {
		this.energieProche = energieProche;
	}

	private void setDebitLoin(double debitLoin) {
		this.debitLoin = debitLoin;
	}

	private void setDebitProche(double debitProche) {
		this.debitProche = debitProche;
	}

	private void setNbUrLoin(double nbUrLoin) {
		this.nbUrLoin = nbUrLoin;
	}

	private void setNbUrProche(double nbUrProche) {
		this.nbUrProche = nbUrProche;
	}

	public int getEnergieGlobale() {
		return energieGlobale;
	}

	private void setEnergieGlobale(int energieGlobale) {
		this.energieGlobale = energieGlobale;
	}

	double getDebitGlobal() {
		return debitGlobal;
	}

	private void setDebitGlobal(double debitGlobal) {
		this.debitGlobal = debitGlobal;
	}

	UR[][] getListUR(){
		return listUR;
	}

	void addListUr(User user, double debit, int i, int j){
		listUR[i][j] = new UR(user,debit);
	}

	int getTemps() {
		return temps;
	}

	private void setTemps(int temps) {
		this.temps = temps;
	}

	double getNbURUtilise() {
		return nbURUtilise;
	}

	private void setNbURUtilise(double nbURUtilise) {
		this.nbURUtilise = nbURUtilise;
	}

	public Performance getP() {
		return p;
	}
}