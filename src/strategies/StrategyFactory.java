package strategies;

public class StrategyFactory {
    static public Strategy getStrategy(EnergyChoiceStrategyType strategyType) {
        Strategy strategy;
        switch (strategyType) {
            case GREEN -> {
                strategy = new GreenStrategy();
            }
            case QUANTITY -> {
                strategy = new QuantityStrategy();
            }
            case PRICE -> {
                strategy = new PriceStrategy();
            }
            default -> {
                strategy = null;
            }
        }
        return strategy;
    }
}
