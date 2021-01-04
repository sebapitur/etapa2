package entityatt;

import entities.Consumer;
import entities.Distributor;
import entities.Entity;
import io.Input;

import java.util.List;

public class Instancer {
    Input input;

    public Instancer(Input input) {
        this.input = input;
    }

    public void addNewEntities (final int currentMonthIndex) {
        List<CostChange> costChanges = input.getCostChanges().get(currentMonthIndex);
        List<Entity> newConsumers = input.getNewConsumers().get(currentMonthIndex);
        newConsumers.forEach(input::addConsumer);
        for (CostChange costChange : costChanges) {
            Distributor distributor = getDistributor(costChange.getId());
            assert distributor != null;
            distributor.setInfrastructureCost(costChange.getInfrastructureCost());
            distributor.setProductionCost(costChange.getProductionCost());
        }
    }

    public Consumer getConsumer(final long id) {
        List<Entity> consumers = input.getConsumers();
        for (Entity entity : consumers) {
            Consumer consumer = (Consumer) entity;
            if (consumer.getId() == id) {
                return consumer;
            }
        }
        return null;
    }
    public Distributor getDistributor(final long id) {
        List<Entity> distributors = input.getDistributors();
        for (Entity entity : distributors) {
            Distributor distributor = (Distributor) entity;
            if (distributor.getId() == id) {
                return distributor;
            }
        }
        return null;
    }
}
