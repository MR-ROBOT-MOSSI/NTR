package ntr.impl;

import ntr.impl.Schedulers.Scheduler;

import java.util.Collection;

public class PointAccesImpl {

	private String name;
	private Collection<URImpl> listUr ;
	private Scheduler scheduler;

	public PointAccesImpl(String n, Scheduler s, Collection<URImpl> ur){
		this.name=n;
		this.scheduler=s;
		this.listUr=ur;
	}

	public Collection<URImpl> getListUr() {
		return listUr;
	}

	public String getName(){
		return this.name;
	}

	public void setName(String n){
		this.name=n;
	}

	public void setListUr(Collection<URImpl> listUr) {

		this.listUr = listUr;
	}

	public void setScheduler(Scheduler s){
		this.scheduler=s;
	}
}