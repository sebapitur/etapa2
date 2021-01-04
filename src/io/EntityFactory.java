package io;

import entities.Consumer;
import entities.Distributor;
import entities.Entity;
import org.json.simple.JSONObject;

public final class EntityFactory {
    public static final String CONSUMER = "consumer";
    public static final String DISTRIBUTOR = "distributor";
    private static EntityFactory instance = null;
    private EntityFactory() { }
    static EntityFactory getInstance() {
        if (instance == null) {
            instance = new EntityFactory();
        }
         return instance;
    }
    Entity createEntity(final String type, final JSONObject obj) {
        switch (type) {
            case CONSUMER -> {
                return new Consumer((long) obj.get("id"),
                        (long) obj.get("initialBudget"),
                        (long) obj.get("monthlyIncome"));
            }
            case DISTRIBUTOR -> {
                return new Distributor((long) obj.get("id"),
                        (long) obj.get("contractLength"),
                        (long) obj.get("initialBudget"),
                        (long) obj.get("initialInfrastructureCost"),
                        (long) obj.get("initialProductionCost"));
            }
            default -> { }
        }
        return null;
    }
}
