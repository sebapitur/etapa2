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
//        List<CostChange> costChanges = input.getCostChanges().get(currentMonthIndex);
        List<Consumer> newConsumers = input.getNewConsumers().get(currentMonthIndex);
        newConsumers.forEach(input::addConsumer);
//        for (CostChange costChange : costChanges) {
//            Distributor distributor = getDistributor(costChange.getId());
//            assert distributor != null;
//            distributor.setInfrastructureCost(costChange.getInfrastructureCost());
//            distributor.setProductionCost(costChange.getProductionCost());
//        }
    }

    public Consumer getConsumer(final long id) {
        List<Consumer> consumers = input.getConsumers();
        for (Consumer consumer : consumers) {
            if (consumer.getId() == id) {
                return consumer;
            }
        }
        return null;
    }
    public Distributor getDistributor(final long id) {
        List<Distributor> distributors = input.getDistributors();
        for (Distributor distributor : distributors) {
            if (distributor.getId() == id) {
                return distributor;
            }
        }
        return null;
    }
}
