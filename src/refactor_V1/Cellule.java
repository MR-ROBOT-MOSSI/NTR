package refactor_V1;

import java.io.*;
import java.util.*;

public class Cellule {

	public static final int TEMPS_MAX = 250;
	public static final int RR_SCHEDULER = 1;
	public static final int MAXSNR_SCHEDULER = 2;
	public static final int WFO_SCHEDULER = 3;
	public static final int OEA_SCHEDULER = 4;
	
	private int temps;
	private int nbPointAcc;
	private Scheduler sh;
	private List<PointAcc> pa;
	private List<Performance> performances;
	
	public Cellule() {
		this.temps = 0;
		this.nbPointAcc = 0;
		this.pa = new ArrayList<PointAcc>();
		this.performances = new ArrayList<Performance>();
	}

	public int getTemps() {
		// TODO Auto-generated method stub
		return temps;
	}

	public List<PointAcc> getPointAcc() {
		// TODO Auto-generated method stub
		return pa;
	}

	public int getNbPointAcc() {
		// TODO Auto-generated method stub
		return nbPointAcc;
	}

	public Scheduler getAllocation() {
		// TODO Auto-generated method stub
		return sh;
	}
	
	public boolean addPointAcc() {
		this.pa.add(new PointAcc(this));
		++nbPointAcc;
		return (pa.get(nbPointAcc - 1) != null);
	}
	
	public void initialiser(int schedulerType) {
		switch(schedulerType) {
			case  RR_SCHEDULER :
				sh = new SchedulerRoundRobin();
				break;
			case MAXSNR_SCHEDULER :
				sh = new SchedulerMAXSNR();
			default :
				System.err.println("Scheduler type not define");
				System.exit(-1);
				break;
		}
		pa.clear();
		performances.clear();
		temps = 0;
		nbPointAcc = 0;
	}
	
	public void GestionInterference(List<UR> paur) {
		User connection;
		User connectionEval;
		
		for(int i=0; i<paur.size(); i++) {
			if(i>0) {
				connection = paur.get(i).getUser();
				connectionEval = paur.get(i - 1).getUser();
				
				if((connection != null) && (connectionEval != null)) {
					if((!connection.getDistance()) && (!connectionEval.getDistance())) {
						connection.elementInterference();
						connectionEval.elementInterference();
					}
				}
			}
		}
	}
	
	public void simulationCell(int utilisateurs) {
		
	}

}

