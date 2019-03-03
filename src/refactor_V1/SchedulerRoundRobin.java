package refactor_V1;

import java.util.*;

public class SchedulerRoundRobin implements Scheduler {

	static final String nom = "ROUND ROBIN SCHEDULER";
	private List<User> user;
	private Iterator<User> i;
	
	
	@Override
	public void initialiser(List<User> listusers) {
		// TODO Auto-generated method stub
		this.user = listusers;
		this.i = this.user.iterator();

	}
	
	public void CalculDebit() {
		Iterator<User> u = user.iterator();
		User ur;
		while(u.hasNext()) {
			ur = u.next();
			ur.CalculDebit();
		}
	}

	@Override
	public UR GestionUR(UR ur) {
		// TODO Auto-generated method stub
		User u = null;
		int compteur = 0;
		boolean f = false;
		
		CalculDebit();
		
		while(!f && compteur < 2) {
			if(i.hasNext()) {
				u = i.next();
				if(u.getPacketCourant() != null) {
					f = true;
				}else {
					u = null;
				}
			}else {
				compteur++;
				i = user.iterator();
			}
		}
		if(u != null) {
			ur.AffectationURtoUser(u);
		}
		if(!i.hasNext()) {
			i = user.iterator();
		}
		return ur;
	}

	@Override
	public String getSchedulerName() {
		// TODO Auto-generated method stub
		return nom;
	}

}
