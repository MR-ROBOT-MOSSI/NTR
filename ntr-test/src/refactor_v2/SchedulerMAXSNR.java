package refactor_v2;

import java.util.List;

public class SchedulerMAXSNR extends Scheduler {

	public SchedulerMAXSNR() {
		super();
		setName("MaxSNR");
	}

	@Override
	public void scheduling() {
        boolean nouser = false;
        int nbLigne = getPa().getListUR().length;
        int nbColonne = getPa().getListUR()[0].length;
        int valeurJ = 0;
        //On ajoute les users et leur debit dans les UR
        for (int i = 0; i < nbLigne; i++) {
            for (int j = 0; j < nbColonne; j++) {
                User userChoisi = choixUser(i,j);
                if(userChoisi == null){
                    valeurJ = j;
                    nouser = true;
                    break;
                }
                traitement(userChoisi, i, j);
                valeurJ = j;
            }
            if(nouser){
                getPa().resetEnergieParTimeSLot(i,valeurJ);
                break;
            }

            getPa().resetEnergieParTimeSLot(i,valeurJ+1);
        }


	}

	public User choixUser(int i, int j){
		User userMax = null;
		double max =-1;
		double debitUser;
		for(User u : getPa().getListUsers()){
			debitUser = u.getTabDebits()[i][j];
			if((debitUser > max) && u.checkTransmission()){
				userMax = u;
				max = debitUser;
			}
		}
		return userMax;
	}
}
