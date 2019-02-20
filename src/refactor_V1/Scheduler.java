package refactor_V1;

import java.util.List;

public interface Scheduler {

	public void initialiser(List<User> listusers);
	public UR GestionUR(UR ur);
	public String getSchedulerName();
	
}
