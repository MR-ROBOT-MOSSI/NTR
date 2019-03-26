package refactor_V1;

import java.util.Iterator;
import java.util.List;

public class SchedulerOEA implements Scheduler {

	static final String nom = "OEA_SCHEDULER";
	private List<User> users;


	@Override
	public void initialiser(List<User> listusers) {
		// TODO Auto-generated method stub
		this.users = listusers;

	}

	@Override
	public UR GestionUR(UR ur) {
		// TODO Auto-generated method stub

		User user = getOEA();
		if(user != null){
			ur.AffectationURtoUser(user);
		}
		return ur;
//		return null;
	}

	@Override
	public String getSchedulerName() {
		// TODO Auto-generated method stub
		return null;
	}


	private User getOEA(){
		User user;
		User oea = null;

		Iterator<User> it = users.iterator();
		while(it.hasNext()){
			user = it.next();
			user.CalculDebit();
			if(oea == null || user.getSNR() > oea.getSNR() && user.getPacketCourant() != null){
				oea = user;
			}
		}
		return oea;
	}

}
