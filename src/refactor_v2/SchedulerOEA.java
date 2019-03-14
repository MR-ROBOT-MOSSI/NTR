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
        int compteur =0;
        int valeurJ = 0;
        //On ajoute les users et leur debit dans les UR
        for (int i = 0; i < nbLigne; i++) {
            for (int j = 0; j < nbColonne; j++) {
                User userChoisi = getOEA(getPa().getListUsers(),i,j);
                if(userChoisi == null){
                    nouser = true;
                    break;
                }
                traitement(userChoisi, i, j);
                compteur+=1;
                valeurJ =j;
            }
            if(nouser){
                getPa().resetEnergieParTimeSLot(i, valeurJ+1);
                break;
            }
            getPa().resetEnergieParTimeSLot(i, valeurJ+1);
        }

    }

    private User getOEA(List<User> users, int i , int j ){
        User user;
        User oea = null;
        int debitOea =0;
        int debitNextUser;

        for (User user1 : users) {
            user = user1;

            debitNextUser = (user.getTimeSlotAttribue() ? 1 : 10) * user.getDebit(i, j);

            if (oea == null || (debitNextUser > debitOea) && user.checkTransmission()) {
                oea = user;
                debitOea = debitNextUser;
            }
        }
        return oea;
    }
}
