package refactor_V1;

import java.util.Iterator;
import java.util.List;

public class SchedulerMAXSNR implements Scheduler {

	static final String nom = "MAXSNR_SCHEDULER";
	private List<User> users;

	@Override
	public void initialiser(List<User> listusers) {
		// TODO Auto-generated method stub
		this.users = listusers;

	}

	private User getMaxSNR(){
		User user;
		User maxsnr = null;

		Iterator<User> it = users.iterator();
		while(it.hasNext()){
			user = it.next();
			user.CalculDebit();
			if(maxsnr == null || user.getSNR() > maxsnr.getSNR() && user.getPacketCourant() != null){
				maxsnr = user;
			}
		}
		return maxsnr;
	}

	@Override
	public UR GestionUR(UR ur) {
		// TODO Auto-generated method stub
		User user = getMaxSNR();
		if(user != null){
			ur.AffectationURtoUser(user);
		}
		return ur;
	}

	@Override
	public String getSchedulerName() {
		// TODO Auto-generated method stub
		return nom;
	}

}
