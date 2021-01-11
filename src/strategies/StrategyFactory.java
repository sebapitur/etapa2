package strategies;

public final class StrategyFactory {


    private StrategyFactory() {
    }

    /**
     *
     * @param strategyType constant
     * @return the instance of strategy needed
     */
    public static Strategy getStrategy(EnergyChoiceStrategyType strategyType) {
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
