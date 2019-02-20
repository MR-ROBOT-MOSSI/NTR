package refactor_V1;

public class UR {

	private PointAcc pointacc;
	private User user;
	private int id;
	
	public UR(int i, PointAcc pointAcc) {
		// TODO Auto-generated constructor stub
		this.pointacc = pointAcc;
		this.id = i;
	}

	public User getUser() {
		// TODO Auto-generated method stub
		return user;
	}

	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	public void AffectationURtoUser(User u) {
		this.user = u;
	}
	
	public void clearUR() {
		this.user = null;
	}

}
