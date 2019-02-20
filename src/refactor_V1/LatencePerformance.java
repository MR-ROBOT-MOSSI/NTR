package refactor_V1;

import java.io.*;
import java.util.*;

public class LatencePerformance implements Performance {

	private Cellule cellule;
	private Map<PointAcc, Integer> latence;
	
	public LatencePerformance(Cellule cellule) {
		this.cellule = cellule;
		this.latence = new HashMap<PointAcc, Integer>();
		for(PointAcc pa : Cellule.getPointAcc())
			this.latence.put(pa, 0);
	}
	
	@Override
	public void PerformanceCell() {
		// TODO Auto-generated method stub
		int Nbusers;
		int latenceInstantT;
		User utilisateur;
		
		for(PointAcc pa : this.latence.keySet()) {
			Nbusers = 0;
			latenceInstantT = 0;
			for(UR ur : pa.getUr()) {
				utilisateur = ur.getUser();
				if(utilisateur != null) {
					Nbusers++;
					List<Packet> packet = utilisateur.getPacket();
					Iterator<Packet> pk = packet.iterator();
					while(pk.hasNext()) {
						Packet packet_s = pk.next();
						latenceInstantT += (packet_s.getTempsfinenvoi() - packet_s.getTempsdebutenvoi());
					}
					if(packet.size() > 0) {
						latenceInstantT = latenceInstantT / packet.size();
					}
				}
				
				if(Nbusers > 0) {
					latenceInstantT = latenceInstantT / Nbusers;
					this.latence.put(pa, this.latence.get(pa) + latenceInstantT);
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
		
		for(PointAcc pa : latence.keySet()) {
			rt = latence.get(pa) / (Cellule.TEMPS_MAX * 2);
			fileName = cellule.getNbPointAcc() + "_cell_" + cellule.getAllocation().getName() + "_latence_of_cell_nb" + idPA + ".csv";
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
