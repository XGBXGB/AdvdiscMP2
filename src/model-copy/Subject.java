package model;

import java.util.Iterator;

public interface Subject {
	public void registerObserver(Observer o);
	public void unRegisterObserver(Observer o);
	public void notifyObservers(/*Iterator Object*/);
}
