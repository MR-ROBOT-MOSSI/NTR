package refactor_V1;

import java.util.ArrayList;
import java.util.List;

public class PointAcc {
	
	static final int NB_TOTAL_UR = 256;
	private List<UR> listur;
	private List<User> listusers;
	private Cellule cell;
	private int nbpacket;
	
	public PointAcc(Cellule cell) {
		this.cell = cell;
		this.listur = new ArrayList<UR>();
		this.listusers = new ArrayList<User>();
		this.nbpacket = 0;
	}
	
	public int getNbPacket() {
		return this.nbpacket;
	}
	
	public int getTemps() {
		return cell.getTemps();
	}
	
	public List<UR> getUr(){
		return this.listur;
	}
	
	public void userCreatePacket() {
		for (User user : this.listusers)
			user.createnewPacket();
	}
	
	public void URstate() {
		for (UR ur : this.listur) {
			System.out.println("\t[" + ur.getId() + "] = User n°" + ((ur.getUser() != null) ? ur.getUser().getId() : "NULL"));
		}
	}
	
	public void initialiser(int nbUsers, Scheduler sh) {
		listusers.clear();
		listur.clear();
		for(int i = 0; i < NB_TOTAL_UR; i++) {
			listur.add(new UR(i, this));
		}
		for(int j = 0; j < nbUsers; j++) {
			listusers.add(new User(j, this, (j%2 == 0)));
		}
		
		sh.initialiser(listusers);
	}
}
