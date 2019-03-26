package refactor_v2;

import java.util.List;

public class SchedulerWFO extends Scheduler {

    private final double beta = Math.pow(10,4);

    public SchedulerWFO() {
        super();
        setName("WFO");
    }

    @Override
    public void scheduling() {
        boolean nouser = false;
        int nbLigne = getPa().getListUR().length;
        int nbColonne = getPa().getListUR()[0].length;
        int valeurJ = 0;
        for (int i = 0; i < nbLigne; i++) {
            for (int j = 0; j < nbColonne; j++) {
                User userChoisi = getWFO(i,j);
                if(userChoisi == null){
                    valeurJ = j;
                    nouser = true;
                    break;
                }
                traitement(userChoisi, i, j);
                valeurJ =j;
            }
            if(nouser){
                getPa().resetEnergieParTimeSLot(i, valeurJ);
                break;
            }
            getPa().resetEnergieParTimeSLot(i, valeurJ+1);
        }

    }

    private User getWFO(int i , int j ){
        User userMax = null;
        double max = -1;
        double PDORNextUser;
        for (User u : getPa().getListUsers()) {
            PDORNextUser = u.getTabDebits()[i][j] * pdorfunction(u.calcPdor());
            if((PDORNextUser > max) && u.checkTransmission()){
                userMax = u;
                max = PDORNextUser;
            }
        }
        return userMax;
    }

    private double pdorfunction(double pdor){
        return 1 + beta*Math.pow(pdor,3);
    }
}