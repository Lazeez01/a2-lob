package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;
import ca.yorku.cmg.lob.trader.Trader;

/**
 * Concrete factory that creates {@linkplain TradingAgent} objects: the type picks the agent subtype, the style picks the strategy.
 */
public class TradingAgentFactory extends AbstractTradingAgentFactory {

	@Override
	public TradingAgent createAgent(String type, String style, Trader t, StockExchange e, NewsBoard n) {
		ITradingStrategy strategy;
		if ("Conservative".equals(style)) {
			strategy = new TradingStrategyConservative(t, e);
		} else if ("Aggressive".equals(style)) {
			strategy = new TradingStrategyAggressive(t, e);
		} else {
			throw new IllegalArgumentException("Unknown trading style: " + style);
		}

		if ("Institutional".equals(type)) {
			return new TradingAgentInstitutional(t, e, n, strategy);
		} else if ("Retail".equals(type)) {
			return new TradingAgentRetail(t, e, n, strategy);
		} else {
			throw new IllegalArgumentException("Unknown trading agent type: " + type);
		}
	}
}
