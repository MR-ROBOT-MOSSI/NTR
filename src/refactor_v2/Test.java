package refactor_v2;

public class Test {
	private static final int NB_SIMULATION = 40;
	private static final int TEMPS_SIMULATION = 10000;

	public static void main(String[] args) {
		Scheduler sh = new SchedulerRoundRobin();
		PointAcc pa = new PointAcc(sh);
		int id_user = 0;
		int nbSimulation =  NB_SIMULATION;
		int userCourant;
		while(nbSimulation > 0){
			userCourant = id_user + 1;
			System.out.println("User : " + userCourant + "/" + NB_SIMULATION);
			pa.createUserEtPaquet(id_user);
			id_user +=1;
			pa.simulation(TEMPS_SIMULATION);
			pa.traitementDonnees(TEMPS_SIMULATION);
			pa.resetGeneral();
			nbSimulation-=1;
		}
	}
}
