package refactor_v2;

public class UR {

	private User user;
	private int debit;

	//constructeur
	public UR(User user, int debit) {
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

	public int getDebit() {
		return debit;
	}

	public void setDebit(int debit) {
		this.debit = debit;
	}
}
