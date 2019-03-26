package refactor_V1;

import java.io.*;
import java.util.*;

public class URPerformance implements Performance {
	
	private Cellule cellule;
	private Map<PointAcc, Double> tauxUR;
	
	public URPerformance(Cellule cellule) {
		// TODO Auto-generated constructor stub
		this.cellule = cellule;
		this.tauxUR = new HashMap<PointAcc, Double>();
		for(PointAcc pa : cellule.getPointAcc())
			this.tauxUR.put(pa, 0.0);
	}

	@Override
	public void PerformanceCell() {
		// TODO Auto-generated method stub
		int compteur;
		User user;
		for(PointAcc pa : this.tauxUR.keySet()) {
			compteur = 0;
			for(UR ur : pa.getUr()) {
				user = ur.getUser();
				if(user != null) {
					compteur++;
				}
			}
			this.tauxUR.put(pa, this.tauxUR.get(pa) + (compteur/PointAcc.NB_TOTAL_UR));
		}

	}

	@Override
	public void Resultats(int NbUtilisateurs) {
		// TODO Auto-generated method stub
		int rt;
		int idPA = 1;
		File file = null;
		BufferedWriter wt = null;
		String fileName;
		
		for(PointAcc pa : this.tauxUR.keySet()) {
			rt = (int)((this.tauxUR.get(pa) / (Cellule.TEMPS_MAX)) * 100);
			fileName = cellule.getNbPointAcc() + "_cell_" + cellule.getAllocation().getSchedulerName() + "_UR_of_cell_nb" + idPA + ".csv";
			try {
				file = new File(fileName);
				file.createNewFile();
				wt = new BufferedWriter(new FileWriter(fileName, true));
				wt.write(NbUtilisateurs + ";" + rt + "\n");
			}catch(IOException e){
				e.printStackTrace();
			}finally {
				try {
					wt.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			idPA++;
		}

	}

}
