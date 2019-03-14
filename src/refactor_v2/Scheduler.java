package refactor_v2;

public abstract class Scheduler {
	private PointAcc pa;
	private String name;

	//constructeur
	public Scheduler(){
		this.pa = null;
		this.name = "";
	}

	public abstract void scheduling();

	/**
	 * Gère la valeur de toutes les données pendant le scheduling
	 * @param userChoisi : utilisateur
	 * @param i : numero de la ligne
	 * @param j : numero de la colonne
 	 */
	void traitement(User userChoisi, int i, int j){
		int debitUser = userChoisi.getTabDebits()[i][j];
		int energie;
		pa.sumDebitGlobal(debitUser);
		userChoisi.sumDebitParTemps(debitUser);

		//Energie
		if (userChoisi.getTimeSlotAttribue()) {
			energie = 1;

		} else {
			energie = 10;
			userChoisi.setTimeSlotAttribue(true);
		}
		pa.sumEnergieGlobale(energie);
		userChoisi.sumEnergie(energie);

		//On ajoute notre Pair(User,debit) dans le tableau d'UR
		this.pa.addListUr(userChoisi,debitUser,i,j);
		pa.sumURUtilise();
		//Gestion des paquets
		userChoisi.verifPacket(debitUser);
		userChoisi.checkTransmission();

	}

	//GETTER & SETTER

	void setName(String name) {
		this.name = name;
	}

	public String getSchedulerName(){
		return name;
	}

	PointAcc getPa() {
		return pa;
	}

	void setPa(PointAcc pa) {
		this.pa = pa;
	}
}
