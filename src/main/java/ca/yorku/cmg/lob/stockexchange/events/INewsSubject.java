package ca.yorku.cmg.lob.stockexchange.events;

import ca.yorku.cmg.lob.stockexchange.tradingagent.INewsObserver;

/**
 * Interface for objects that announce news {@linkplain Event}s to registered {@linkplain INewsObserver}s.
 */
public interface INewsSubject {
	void registerObserver(INewsObserver o);
	void removeObserver(INewsObserver o);
	void notifyObservers(Event e);
}
