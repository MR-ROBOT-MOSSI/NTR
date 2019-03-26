package refactor_v2;

import java.io.BufferedWriter;
import java.io.*;
import java.util.*;

class Performance {
    private static final int TEMPS_MAX = 15;
    private Scheduler sh;

	//init scheduler
    Performance(Scheduler sh){
        this.sh = sh;
    }

    private void writing(int nbusers, double aEcrire, String fileName) {
        File file;
        BufferedWriter wt = null;
        try {
            file = new File(fileName);
            boolean b = file.createNewFile();
            wt = new BufferedWriter(new FileWriter(fileName, true));
            wt.write(nbusers + ";" + aEcrire + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert wt != null;
                wt.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

       private void writing2(int nbusers, int aEcrire,int entier2, int entier3, String fileName) {
        File file;
        BufferedWriter wt = null;
        try {
            file = new File(fileName);
            boolean b = file.createNewFile();
            wt = new BufferedWriter(new FileWriter(fileName, true));
            wt.write(nbusers + ";" + aEcrire + ";"+ entier2 + ";" + entier3  + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert wt != null;
                wt.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //calcul des performances débit d'un scheduler
    void performanceDebit(int nbusers, double debit, double debitProche, double debitLoin){
        String fileName;
        fileName = sh.getSchedulerName() + "_debit_of_cell_nb.csv";
        writing2(nbusers, (int) debit, (int) debitProche, (int) debitLoin, fileName);
    }

    //calcul des performances energie d'un scheduler
    void performanceEnergie(int nbusers, double energie, double energieP, double energieL){
        String fileName;
        fileName = sh.getSchedulerName() + "_energie_of_cell_nb.csv";
        writing2(nbusers, (int) energie,(int) energieP, (int) energieL, fileName);
    }

    //calcul des performances UR d'un scheduler
    void performanceURUtilise(int nbusers, double pourcentageUr,double proche, double loin){
        String fileName;
        fileName = sh.getSchedulerName() + "_URUtilise_of_cell_nb.csv";
        writing2(nbusers, (int) pourcentageUr,(int)proche, (int)loin, fileName);
    }


    //calcul des performances latences d'un scheduler
    void performanceLatence(int nbusers) {
        String fileName;
        List<Double> res = calculLatence();
        int resTotale = (int)(res.get(0)*100);
        int resProche = (int)(res.get(1)*100);
        int resLoin = (int)(res.get(2)*100);
        fileName = sh.getSchedulerName() + "_Latence_of_cell_nb.csv";
        writing2(nbusers, resTotale,resProche,resLoin, fileName);

    }

    private List<Double> calculLatence() {
        int latenceInstantT;
        double nbPHDtotale = 0;
        double nbPHDproche =0;
        double nbPHDloin = 0;
        int tailleProche = 0;
        int tailleLoin = 0;
        int tailleTotale = 0;
        int compteur = 0;
        for (User u : sh.getPa().getListUsers()) {
            if (u != null) {
                List<Packet> packet = u.getPacket();
                for (Packet packet_s : packet) {
                    latenceInstantT = (packet_s.getTempsfinenvoi() - packet_s.getTempscreation());
                    if (latenceInstantT > TEMPS_MAX) {
                        if(u.getDistance()){
                            nbPHDproche +=1;
                        }else{
                            nbPHDloin += 1;
                        }
                        nbPHDtotale += 1;
                    }
                }
                if(u.getDistance()){
                    tailleProche +=u.getPacket().size();
                }else{
                    tailleLoin +=u.getPacket().size();
                }
                tailleTotale += u.getPacket().size();
            }
        }
        List<Double> res = new ArrayList<>();
        res.add(nbPHDtotale/tailleTotale);
        res.add(nbPHDproche/tailleProche);
        res.add(nbPHDloin/tailleLoin);
        return res;
    }

    void performanceDelai(int nbusers){
        String fileName;
        double delaiMoyen =0;
        double delaiProche = 0;
        double delaiLoin = 0;
        int tailleProche = 0;
        int tailleLoin = 0;
        int taille  = 0;
        int latenceInstantT;
        for (User u : sh.getPa().getListUsers()) {
            if (u != null) {
                List<Packet> packet = u.getPacket();
                for (Packet packet_s : packet) {
                    latenceInstantT = (packet_s.getTempsfinenvoi() - packet_s.getTempscreation());
                    if (u.getDistance()) {
                        delaiProche += latenceInstantT;
                    } else {
                        delaiLoin += latenceInstantT;
                    }
                    delaiMoyen += latenceInstantT;
                }
                if(u.getDistance()){
                    tailleProche +=u.getPacket().size();
                }else{
                    tailleLoin +=u.getPacket().size();
                }
                taille += u.getPacket().size();
            }
        }
        delaiMoyen = delaiMoyen/taille;
        delaiProche = delaiProche/tailleProche;
        delaiLoin = delaiLoin/tailleLoin;
        fileName = sh.getSchedulerName() + "_Delai_of_cell_nb.csv";
        writing2(nbusers, (int)delaiMoyen,(int)delaiProche,(int)delaiLoin, fileName);
    }

    void performanceEfSpectrale(int nbusers) {
        String fileName;
        double resultat = sh.getPa().getNbURUtilise();
        double Dglobal = sh.getPa().getDebitGlobal();
        fileName = sh.getSchedulerName() + "_EffSpectrale_of_cell_nb.csv";
        writing(nbusers, (Dglobal/ resultat), fileName);
    }
}