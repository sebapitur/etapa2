package io;

import com.sun.source.tree.BreakTree;
import strategies.EnergyChoiceStrategyType;

public enum Constants {

    NUMBER_OF_TURNS, INITIAL_DATA, CONSUMERS, CONSUMER, DISTRIBUTORS,
    DISTRIBUTOR, MONTHLY_UPDATES, NEW_CONSUMERS, INITIAL_BUDGET,
    COSTS_CHANGES, ID, INITIAL_INFRASTRUCTURE_COST, PRODUCTION_COST, MONTHLY_INCOME,
    CONTRACT_LENGTH, ENERGY_NEEDED_KW, PRODUCER_STRATEGY, PRODUCERS,
    PRODUCER, ENERGY_TYPE, MAX_DISTRIBUTORS, ENERGY_PER_DISTRIBUITOR, PRICE_KW,
    SOLAR, HYDRO, COAL, WIND, NUCLEAR;

    static String getString(Constants constant){
        return switch (constant) {
            case ID -> "id";
            case NUMBER_OF_TURNS -> "numberOfTurns";
            case INITIAL_DATA -> "initialData";
            case CONSUMERS -> "consumers";
            case CONSUMER -> "consumer";
            case INITIAL_BUDGET -> "initialBudget";
            case MONTHLY_INCOME -> "monthlyIncome";
            case DISTRIBUTORS -> "distributors";
            case DISTRIBUTOR -> "distributor";
            case CONTRACT_LENGTH -> "contractLength";
            case INITIAL_INFRASTRUCTURE_COST -> "initialInfrastructureCost";
            case ENERGY_NEEDED_KW -> "energyNeededKW";
            case PRODUCER_STRATEGY -> "producerStrategy";
            case PRODUCERS -> "producers";
            case PRODUCER -> "producer";
            case ENERGY_TYPE -> "energyType";
            case MAX_DISTRIBUTORS -> "maxDistributors";
            case ENERGY_PER_DISTRIBUITOR ->"energyPerDistributor";
            case PRICE_KW -> "priceKW";
            default -> null;
        };
    }
    static Constants getEnergyType(String str) {
        return switch (str) {
            case "SOLAR" -> Constants.SOLAR;
            case "HYDRO" -> Constants.HYDRO;
            case "COAL" -> Constants.COAL;
            case "WIND" -> Constants.WIND;
            case "NUCLEAR" -> Constants.NUCLEAR;
            default -> null;
        };
    }

}
