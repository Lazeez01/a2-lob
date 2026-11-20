package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;
import ca.yorku.cmg.lob.trader.Trader;

/**
 * A trading agent acting for a retail trader. Its reaction to news is determined by the strategy it is given.
 */
public class TradingAgentRetail extends TradingAgent {

	public TradingAgentRetail(Trader t, StockExchange e, NewsBoard n, ITradingStrategy s) {
		super(t, e, n, s);
	}
}
