package strategies;

/**
 * Strategy types for distributors to choose their producers
 */
public enum EnergyChoiceStrategyType {

    GREEN("GREEN"),
    PRICE("PRICE"),
    QUANTITY("QUANTITY");
    public final String label;

    public static EnergyChoiceStrategyType getConstant(String str) {
        switch (str) {
            case "GREEN" -> {return EnergyChoiceStrategyType.GREEN;}
            case "PRICE" -> {return EnergyChoiceStrategyType.PRICE;}
            case "QUANTITY" -> {return EnergyChoiceStrategyType.QUANTITY;}
            default -> {return null;}
        }
    }

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
