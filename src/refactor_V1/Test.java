package refactor_V1;

public class Test {

	static final int INF_USER = 2;
	static final int SUP_USER = 65;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int users;
		Cellule cell = new Cellule();
		
		cell.initialiser(Cellule.RR_SCHEDULER);
		System.out.println("ROUND ROBIN SCHEDULER");
		if(!cell.addPointAcc()) {
			System.out.println("ERREUR AJOUT POINT D'ACCESS");
			System.exit(-1);
		}
		
		for(users = INF_USER; users < SUP_USER; users+=2) {
			cell.simulationCell(users);
		}

		cell.initialiser(Cellule.MAXSNR_SCHEDULER);
		System.out.println("ROUND ROBIN SCHEDULER");
		if(!cell.addPointAcc()) {
			System.out.println("ERREUR AJOUT POINT D'ACCESS");
			System.exit(-1);
		}
		
		for(users = INF_USER; users < SUP_USER; users+=2) {
			cell.simulationCell(users);
		}
		
		System.out.println("FIN");
	}

}
