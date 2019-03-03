package refactor_V1;

import java.util.*;
import java.util.Random;

public class User {

	private int id;
	private boolean distance;
	private PointAcc pointacc;
	private List<Packet> packet;
	private Deque<Packet> buf;
	private int debitglobal;
	private int debitinstantT;
	
	public User(int j, PointAcc pointAcc, boolean b) {
		// TODO Auto-generated constructor stub
		this.id = j;
		this.packet = new ArrayList<Packet>();;
		this.distance = b;
		this.pointacc = pointAcc;
		buf = new LinkedList<Packet>();
		if(distance) {
			debitglobal = 6;
		}else {
			debitglobal = 3;
		}
	}

	public void createnewPacket() {
		// TODO Auto-generated method stub
		if(this.randomBoolean()) {
			Packet packet = new Packet(this, pointacc.getTemps());
			buf.add(packet);
		}
	}

	private boolean randomBoolean() {
		// TODO Auto-generated method stub
		return (Math.random() < 0.25);
	}
	

	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	
	public void CalculDebit() {
		debitinstantT = (int)(2*(Math.random()*((debitglobal*2)/2+1)));
	}
	
	public int getDebitInstantT() {
		// TODO Auto-generated method stub
		return this.debitinstantT;
	}
	
	public boolean getDistance() {
		return distance;
	}
	
	Packet getPacketCourant() {
		// TODO Auto-generated method stub
		return buf.peek();
	}
	
	public void verifPacket() {
		Packet packet = this.getPacketCourant();
		if(packet != null) {
			int bitsrestant = packet.getNbbistrestant() - debitinstantT;
			if(bitsrestant <= 0) {
				packet.setNbbistrestant(0);
				this.packetTraitementFini();
			}else {
				packet.setNbbistrestant(bitsrestant);
			}
		}
	}

	private void packetTraitementFini() {
		// TODO Auto-generated method stub
		getPacketCourant().setTempsfinenvoi(pointacc.getTemps());
		this.packet.add(getPacketCourant());
		buf.removeFirst();
	}

	public void supprimerPacket() {
		this.packet.remove(getPacketCourant());
	}
	
	
	public int getSNR() {
		return this.debitinstantT;
	}
	
	public void elementInterference() {
		debitinstantT = debitinstantT/2;
	}
	 public List<Packet> getPacket(){
		 return this.packet;
	 }

}
