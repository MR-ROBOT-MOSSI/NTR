package refactor_v2;

public class Test {
	private static final int NB_SIMULATION = 22;
	private static final double TEMPS_SIMULATION = 50000;

	public static void main(String[] args) {
		Scheduler sh = new SchedulerOEA();
		PointAcc pa = new PointAcc(sh);
		int id_user = 0;
		int nbSimulation =  NB_SIMULATION;
		int userCourant =0;
		while(nbSimulation > 0){
			userCourant += 1;
			System.out.println("User : " + userCourant + "/" + NB_SIMULATION);
			pa.createUserEtPaquet(id_user);
			id_user +=2;
			pa.simulation(TEMPS_SIMULATION);
			pa.traitementDonnees(TEMPS_SIMULATION);
			pa.resetGeneral();
			nbSimulation-=1;
		}
	}
}
