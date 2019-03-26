package refactor_v2;

import java.util.List;

public class SchedulerOEA extends Scheduler {


    public SchedulerOEA() {
        super();
        setName("OEA");
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
                User userChoisi = getOEA(i,j);
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

    private User getOEA( int i , int j ){
        User user;
        User oea = null;
        double debitOea =0;
        double debitNextUser;

        for (User user1 : getPa().getListUsers()) {
            user = user1;

            debitNextUser = user.getTabDebits()[i][j]/(user.getTimeSlotAttribue() ? 1 : 10);

            if ( ((debitNextUser > debitOea) && user.checkTransmission())) {
                oea = user;
                debitOea = debitNextUser;
            }
        }
        return oea;
    }
}
