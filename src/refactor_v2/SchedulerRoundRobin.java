package refactor_v2;

import java.util.List;

public class SchedulerRoundRobin extends Scheduler {

	SchedulerRoundRobin() {
		super();
		setName("RR");
	}

	@Override
	public void scheduling() {
		boolean nouser = false;
		int nbLigne = getPa().getListUR().length;
		int nbColonne = getPa().getListUR()[0].length;
		int compteur =0;
		int valeurJ = 0;
		//On ajoute les users et leur debit dans les UR
		for (int i = 0; i < nbLigne; i++) {
			for (int j = 0; j < nbColonne; j++) {
				User userChoisi = choixUser(getPa().getListUsers(),compteur);
				if(userChoisi == null){
					valeurJ = j;
					nouser = true;
					break;
				}
				traitement(userChoisi, i, j);
				compteur+=1;
				valeurJ = j;
			}
			if(nouser){
				getPa().resetEnergieParTimeSLot(i,valeurJ);
				break;
			}

			getPa().resetEnergieParTimeSLot(i,valeurJ+1);
		}

	}

	private User choixUser(List<User> users, int compteur) {
		int nbUsers = users.size();
		boolean nouser = false;
		int securite = 0;
		User userChoisi = users.get(compteur%nbUsers);
		if(!userChoisi.checkTransmission()){
			//S'il ne doit pas transmettre
			compteur+=1;
			while (!users.get(compteur % nbUsers).checkTransmission()) {
				compteur+=1;
				if(compteur%users.size() == 0 ){
					compteur = 0;
				}
				securite +=1;
				//Evite la boucle infinie dans le cas où le buffer de tous les users est vide
				if (securite > nbUsers){
					nouser=true;
					break;
				}
			}
			if(!nouser){
				userChoisi = users.get(compteur%nbUsers);
			}else{
				return null;
			}

		}
		return userChoisi;
	}
}
