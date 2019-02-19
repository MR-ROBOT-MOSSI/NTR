package ntr.impl;

import java.util.Collection;

public class UserImpl {

    private String name ;
    private String pointAcces ;
    private int distance ;
    private Collection<Package> listPaquet ;
    private int debitMoyen ;
    
    
    public UserImpl() {
    	
    }
    
    
    public String getName() {

    	return this.name ;
    }

	public String getPointAcces() {

    	return pointAcces;
	}

	public void setPointAcces(String pointAcces) {

    	this.pointAcces = pointAcces;
	}

	public int getDistance() {

    	return distance;
	}

	public void setDistance(int distance) {

    	this.distance = distance;
	}

	public Collection<Package> getListPaquet() {

    	return listPaquet;
	}

	public void setListPaquet(Collection<Package> listPaquet) {

    	this.listPaquet = listPaquet;
	}

	public int getDebitMoyen() {

    	return debitMoyen;
	}

	public void setDebitMoyen(int debitMoyen) {

    	this.debitMoyen = debitMoyen;
	}
}