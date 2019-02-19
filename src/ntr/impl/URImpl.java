package ntr.impl;

public class URImpl  {
	
	private PointAccesImpl pointAcces ;
	private UserImpl user ;
	private int id ;
	private int debit;
	
	public URImpl(int id, PointAccesImpl ptAcces) {
		this.id = id;
		this.pointAcces = ptAcces;
	}
	
	/*
	 * give this UR an user
	 */
	
	public PointAccesImpl getPointAcces() {
		return pointAcces;
	}

	public void setPointAcces(PointAccesImpl pointAcces) {
		this.pointAcces = pointAcces;
	}
	
	public UserImpl getUser() {
		return user;
	}
	public void setUser(UserImpl user) {
		this.user = user;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDebit(){
		return this.debit;
	}
}