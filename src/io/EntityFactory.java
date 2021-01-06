package io;

import entities.Consumer;
import entities.Distributor;
import entities.Entity;
import entities.Producer;
import entityatt.Change;
import entityatt.DistributorChange;
import entityatt.ProducerChange;
import strategies.EnergyChoiceStrategyType;

import java.util.Map;

public final class EntityFactory {
    private static EntityFactory instance = null;

    private EntityFactory() {
    }

    static EntityFactory getInstance() {
        if (instance == null) {
            instance = new EntityFactory();
        }
        return instance;
    }

    Entity createEntity(Constants type, Map<?, ?> entry) {
        switch (type) {
            case CONSUMER -> {
                long id = (Integer) entry.get(Constants.getString(Constants.ID));
                long initialBudget = (Integer) entry.get(Constants.getString(Constants.INITIAL_BUDGET));
                long monthlyIncome = (Integer) entry.get(Constants.getString(Constants.MONTHLY_INCOME));
                return new Consumer(id, initialBudget, monthlyIncome);
            }
            case DISTRIBUTOR -> {

                long id = (Integer) entry.get(Constants.getString(Constants.ID));
                long contractLength = (Integer) entry.get(Constants.getString(Constants.CONTRACT_LENGTH));
                long initialInfrastructureCost =
                        (Integer) entry.get(Constants.getString(Constants.INITIAL_INFRASTRUCTURE_COST));
                long initialBudget = (Integer) entry.get(Constants.getString(Constants.INITIAL_BUDGET));
                long energyNeededKW = (Integer) entry.get(Constants.getString(Constants.ENERGY_NEEDED_KW));
                String stringStrategy = (String) entry.get(Constants.getString(Constants.PRODUCER_STRATEGY));
                EnergyChoiceStrategyType strategy = EnergyChoiceStrategyType.getConstant(stringStrategy);
                return new Distributor(id, contractLength, initialBudget,
                        initialInfrastructureCost, energyNeededKW, strategy);
            }
            case PRODUCER -> {
                long id = (Integer) entry.get(Constants.getString(Constants.ID));
                String stringType = (String) entry.get(Constants.getString(Constants.ENERGY_TYPE));
                Constants energyType = Constants.getEnergyType(stringType);
                long maxDistributors = (Integer) entry.get(Constants.getString(Constants.MAX_DISTRIBUTORS));
                double priceKW = (Double) entry.get(Constants.getString(Constants.PRICE_KW));
                long energyPerDistributor = (Integer) entry.get(Constants.getString(Constants.ENERGY_PER_DISTRIBUITOR));
                return new Producer(id, energyPerDistributor, maxDistributors, energyType, priceKW);
            }
        }
        return null;
    }
    public Change createChange(Constants con, Map<?,?> map) {
        long id = (Integer)map.get(Constants.getString(Constants.ID));
        switch (con) {
            case DISTRIBUTOR -> {
                long infrastructureCost = (Integer) map.get(Constants.getString(Constants.INFRASTRUCTURE_COST));
                return new DistributorChange(id, infrastructureCost);
            }
            case PRODUCER -> {
                long energyPerDistributor = (Integer) map.get(Constants.getString(Constants.ENERGY_PER_DISTRIBUITOR));
                return new ProducerChange(id, energyPerDistributor);
            }
            default -> {
                return null;
            }
        }
    }
}
