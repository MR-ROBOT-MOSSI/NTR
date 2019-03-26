package refactor_V1;

import java.io.*;
import java.util.*;

public class DebitPerformance implements Performance {

	private Cellule cellule;
	private Map<PointAcc, Integer> debit;
	
	public DebitPerformance(Cellule cellule) {
		// TODO Auto-generated constructor stub
		this.cellule = cellule;
		this.debit = new HashMap<PointAcc, Integer>();
		for(PointAcc pa : cellule.getPointAcc())
			this.debit.put(pa, 0);
	}

	@Override
	public void PerformanceCell() {
		// TODO Auto-generated method stub
		User user;
		for(PointAcc pa : this.debit.keySet()) {
			for(UR ur : pa.getUr()) {
				user = ur.getUser();
				if(user != null) {
					this.debit.put(pa, this.debit.get(pa) + user.getDebitInstantT());
				}
			}
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
		
		for(PointAcc pa : this.debit.keySet()) {
			rt = this.debit.get(pa) / (Cellule.TEMPS_MAX * 2);
			fileName = cellule.getNbPointAcc() + "_cell_" + cellule.getAllocation().getSchedulerName() + "_debit_of_cell_nb" + idPA + ".csv";
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
