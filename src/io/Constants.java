package io;

import com.sun.source.tree.BreakTree;
import strategies.EnergyChoiceStrategyType;

public enum Constants {

    NUMBER_OF_TURNS, INITIAL_DATA, CONSUMERS, CONSUMER, DISTRIBUTORS,
    DISTRIBUTOR, MONTHLY_UPDATES, NEW_CONSUMERS, INITIAL_BUDGET,
    COSTS_CHANGES, ID, INITIAL_INFRASTRUCTURE_COST, PRODUCTION_COST, MONTHLY_INCOME,
    CONTRACT_LENGTH, ENERGY_NEEDED_KW, PRODUCER_STRATEGY, PRODUCERS,
    PRODUCER, ENERGY_TYPE, MAX_DISTRIBUTORS, ENERGY_PER_DISTRIBUITOR, PRICE_KW,
    IS_BANKRUPT, BUDGET, CONTRACT_COST, CONTRACTS, CONSUMER_ID, PRICE,
    REMAINED_CONTRACT_MONTHS, MONTHLY_STATS, MONTH, DISTRIBUTORS_IDS,
    ENERGY_PRODUCERS,
    SOLAR, HYDRO, COAL, WIND, NUCLEAR



    , DISTRIBUTORS_CHANGES, PRODUCER_CHANGES, INFRASTRUCTURE_COST;

    public static String getString(Constants constant){
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
            case MONTHLY_UPDATES -> "monthlyUpdates";
            case NEW_CONSUMERS -> "newConsumers";
            case DISTRIBUTORS_CHANGES ->  "distributorChanges";
            case PRODUCER_CHANGES -> "producerChanges";
            case INFRASTRUCTURE_COST -> "infrastructureCost";
            case IS_BANKRUPT -> "isBankrupt";
            case BUDGET -> "budget";
            case CONTRACT_COST -> "contractCost";
            case CONTRACTS -> "contracts";
            case CONSUMER_ID -> "consumerId";
            case PRICE -> "price";
            case REMAINED_CONTRACT_MONTHS -> "remainedContractMonths";
            case MONTHLY_STATS -> "monthlyStats";
            case MONTH -> "month";
            case DISTRIBUTORS_IDS -> "distributorsIds";
            case SOLAR -> "SOLAR";
            case HYDRO -> "HYDRO";
            case COAL -> "COAL";
            case WIND -> "WIND";
            case NUCLEAR -> "NUCLEAR";
            case ENERGY_PRODUCERS -> "energyProducers";
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

    public boolean isRenewable() {
        return this == WIND || this == HYDRO || this == SOLAR;
    }
}
