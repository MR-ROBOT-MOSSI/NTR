package refactor_v2;

import java.io.BufferedWriter;
import java.io.*;
import java.util.*;

class Performance {
    private static final int TEMPS_MAX = 4;
    private Scheduler sh;

	//init scheduler
    Performance(Scheduler sh){
        this.sh = sh;
    }

    private void writing(int nbusers, int aEcrire, String fileName) {
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

    //calcul des performances débit d'un scheduler
    void performanceDebit(int nbusers, double debit){
        String fileName;
        fileName = sh.getSchedulerName() + "_debit_of_cell_nb.csv";
        writing(nbusers, (int) debit, fileName);
    }

    //calcul des performances energie d'un scheduler
    void performanceEnergie(int nbusers, double energie){
        String fileName;
        fileName = sh.getSchedulerName() + "_energie_of_cell_nb.csv";
        writing(nbusers, (int) energie, fileName);
    }

	//calcul des performances UR d'un scheduler
    void performanceURUtilise(int nbusers, double pourcentageUr){
        String fileName;
        fileName = sh.getSchedulerName() + "_URUtilise_of_cell_nb.csv";
        writing(nbusers, (int) pourcentageUr, fileName);
    }

    //calcul des performances latences d'un scheduler
    void performanceLatence(int nbusers) {
        String fileName;
        int resultat = (int)(calculLatence() *100);
        fileName = sh.getSchedulerName() + "_Latence_of_cell_nb.csv";
        writing(nbusers, resultat, fileName);
    }

    private double calculLatence() {
        int latenceInstantT;
        float nbPHD = 0;
        int taille = 0;
        int compteur = 0;
        for (User u : sh.getPa().getListUsers()) {
            if (u != null) {
                List<Packet> packet = u.getPacket();
                for (Packet packet_s : packet) {
                    latenceInstantT = (packet_s.getTempsfinenvoi() - packet_s.getTempscreation());
                    if (latenceInstantT >= TEMPS_MAX) {
                        nbPHD += 1;
                    }
                }
            }
            assert u != null;
            taille += u.getPacket().size();
        }
        return nbPHD/taille;
    }

    //efficacité spectrale
    private double calculCapSpec(){
        int ontime;
        float nbPHD = 0;
        int taille = 0;
        for (User u : sh.getPa().getListUsers()) {
            if (u != null) {
                List<Packet> packet = u.getPacket();
                for (Packet packet_s : packet) {
                    ontime = (packet_s.getTempsfinenvoi() - packet_s.getTempscreation());
                    if (ontime <= TEMPS_MAX) {
                        nbPHD += 1;
                    }
                }
            }
            assert u != null;
            taille += u.getPacket().size();
        }
        return nbPHD/taille;

    }

    void performanceCapSpectrale(int nbusers) {
        String fileName;
        int resultat = (int)(calculCapSpec() *100);
        fileName = sh.getSchedulerName() + "_CapSpectrale_of_cell_nb.csv";
        writing(nbusers, resultat, fileName);
    }
}