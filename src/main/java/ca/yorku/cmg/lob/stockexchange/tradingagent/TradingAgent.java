package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.Event;
import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;
import ca.yorku.cmg.lob.trader.Trader;

/**
 * A trading agent that receives news and reacts by delegating to an {@linkplain ITradingStrategy}.
 * News can arrive by polling ({@link #timeAdvancedTo(long)}) or pushed by the {@linkplain NewsBoard} ({@link #update(Event)}).
 */
public abstract class TradingAgent implements INewsObserver {
	protected Trader t;
	protected StockExchange exc;
	protected NewsBoard news;
	protected ITradingStrategy strategy;

	/**
	 * Constructor. Registers the agent with the {@linkplain NewsBoard} to receive news.
	 * @param t The {@linkplain Trader} object associated with the agent.
	 * @param e The {@linkplain StockExchange} object at which the agent has an account and trades in.
	 * @param n The {@linkplain NewsBoard} object that generates news events.
	 * @param s The {@linkplain ITradingStrategy} that decides how the agent reacts to news.
	 */
	public TradingAgent(Trader t, StockExchange e, NewsBoard n, ITradingStrategy s) {
		this.t = t;
		this.exc = e;
		this.news = n;
		this.strategy = s;
		this.news.registerObserver(this);
	}

	/**
	 * Method to be called as time advances to {@code time}. In response the TradingAgent will poll the NewsBoard for events.
	 * @param time The time to advance to.
	 */
	public void timeAdvancedTo(long time) {
		pollForEvents(time);
	}

	/**
	 * Called by the {@linkplain NewsBoard} when it pushes a news event.
	 * @param e The event being announced.
	 */
	@Override
	public void update(Event e) {
		examineEvent(e);
	}

	/**
	 * Examine if an event is relevant for the Agent, i.e., if the Agent has a position on it.
	 * @param e The {@linkplain Event} object in question
	 */
	private void examineEvent(Event e) {
		int positionInSecurity = exc.getAccounts().getTraderAccount(t).getPosition(e.getSecrity().getTicker());
		if (positionInSecurity > 0) {
			strategy.actOnEvent(e, positionInSecurity, exc.getPrice(e.getSecrity().getTicker()));
		}
	}

	/**
	 * Check into the {@linkplain NewsBoard} if there are any events at time {@code time}. If there is one (it assumes only one event at a time), send it for examination.
	 * @param time The time for which to poll for events. Unit is days.
	 */
	private void pollForEvents(long time) {
		Event e = news.getEventAt(time);
		if (e != null) {
			examineEvent(e);
		}
	}
}
