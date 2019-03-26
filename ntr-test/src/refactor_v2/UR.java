package refactor_v2;

public class UR {

	private User user;
	private double debit;

	//constructeur
	public UR(User user, double debit) {
		this.user = user;
		this.debit = debit;
	}
	//getter and setter
	public User getUser() {
		return user;
	}

	public void setUser(User u) {
		this.user = u;
	}

	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}
}
