package strategies;

/**
 * Strategy types for distributors to choose their producers
 */
public enum EnergyChoiceStrategyType {

    GREEN("GREEN"),
    PRICE("PRICE"),
    QUANTITY("QUANTITY");
    private final String label;

    public String getLabel() {
        return label;
    }

    /**
     *
     * @param str to be transformed
     * @return the constant coresponding tot the string
     */
    public static EnergyChoiceStrategyType getConstant(String str) {
        switch (str) {
            case "GREEN" -> {
                return EnergyChoiceStrategyType.GREEN;
            }
            case "PRICE" -> {
                return EnergyChoiceStrategyType.PRICE;
            }
            case "QUANTITY" -> {
                return EnergyChoiceStrategyType.QUANTITY;
            }
            default -> {
                return null;
            }
        }
    }

    /**
     *
     * @param t constant to be transformed
     * @return String coresponding to the constant
     */
    public static String getString(EnergyChoiceStrategyType t) {
        switch (t) {
            case GREEN -> {
                return "GREEN";
            }
            case PRICE -> {
                return "PRICE";
            }
            case QUANTITY -> {
                return "QUANTITY";
            }
            default -> {
                return null;
            }
        }
    }

    EnergyChoiceStrategyType(String label) {
        this.label = label;
    }
}
