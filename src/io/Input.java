package io;

import entities.Entity;
import entityatt.CostChange;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.LinkedList;
import java.util.List;

public final class Input {
    public static final String NUMBER_OF_TURNS = "numberOfTurns";
    public static final String INITIAL_DATA = "initialData";
    public static final String CONSUMERS = "consumers";
    public static final String CONSUMER = "consumer";
    public static final String DISTRIBUTORS = "distributors";
    public static final String DISTRIBUTOR = "distributor";
    public static final String MONTHLY_UPDATES = "monthlyUpdates";
    public static final String NEW_CONSUMERS = "newConsumers";
    public static final String COSTS_CHANGES = "costsChanges";
    public static final String ID = "id";
    public static final String INFRASTRUCTURE_COST = "infrastructureCost";
    public static final String PRODUCTION_COST = "productionCost";
    private long numberOfTurns;
    private List<Entity> consumers = new LinkedList<>();
    private List<Entity> distributors = new LinkedList<>();
    private final List<List<CostChange>> costChanges = new LinkedList<>();
    private List<List<Entity>> newConsumers = new LinkedList<>();

    /**
     *
     * @param consumer to be added
     */
    public void addConsumer(final Entity consumer) {
        consumers.add(consumer);
    }

    public long getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final long numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public List<Entity> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<Entity> consumers) {
        this.consumers = consumers;
    }

    public List<Entity> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<Entity> distributors) {
        this.distributors = distributors;
    }

    public List<List<CostChange>> getCostChanges() {
        return costChanges;
    }

    public List<List<Entity>> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final List<List<Entity>> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public Input(final JSONObject jsonObject) {

        EntityFactory factory = EntityFactory.getInstance();
        numberOfTurns = (long) (jsonObject.get(NUMBER_OF_TURNS));
        JSONObject initialData = (JSONObject) jsonObject.get(INITIAL_DATA);
        JSONArray consumers = (JSONArray) initialData.get(CONSUMERS);
        for (Object consumer: consumers) {
            JSONObject consumerJSON = (JSONObject) consumer;
            this.consumers.add(factory.createEntity(CONSUMER, consumerJSON));
        }
        JSONArray distributors = (JSONArray) initialData.get(DISTRIBUTORS);
        for (Object distributor: distributors) {
            JSONObject distributorJSON = (JSONObject) distributor;
            this.distributors.add(factory.createEntity(DISTRIBUTOR, distributorJSON));
        }
        JSONArray monthlyUpdates = (JSONArray) (jsonObject.get(MONTHLY_UPDATES));
        for (Object monthlyUpdate: monthlyUpdates) {
            JSONObject monthlyUpdateJSON = (JSONObject) monthlyUpdate;
            JSONArray monthlyUpdateArray = (JSONArray) monthlyUpdateJSON.get(NEW_CONSUMERS);
            List<Entity> newConsumers = new LinkedList<>();
            for (Object monthlyUpdateObject :monthlyUpdateArray) {
                JSONObject monthlyUpdateObjectJSON = (JSONObject) monthlyUpdateObject;
                newConsumers.add(factory.createEntity(CONSUMER, monthlyUpdateObjectJSON));
            }
            JSONArray costChangesArray = (JSONArray) monthlyUpdateJSON.get(COSTS_CHANGES);
            List<CostChange> costChanges = new LinkedList<>();
            if (costChangesArray != null) {
                for (Object costChange: costChangesArray) {
                    JSONObject costChangeJSON = (JSONObject) costChange;
                    costChanges.add(new CostChange((long) costChangeJSON.get(ID),
                            (long) costChangeJSON.get(INFRASTRUCTURE_COST),
                            (long) costChangeJSON.get(PRODUCTION_COST)));
                }
            }
            this.costChanges.add(costChanges);
            this.newConsumers.add(newConsumers);
        }

    }
}
