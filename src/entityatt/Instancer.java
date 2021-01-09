package entityatt;

import entities.Consumer;
import entities.Distributor;
import entities.Entity;
import entities.Producer;
import io.Input;

import java.util.List;

public class Instancer {
    Input input;

    public Instancer(Input input) {
        this.input = input;
    }

    public Input getInput() {
        return input;
    }

    public void addNewEntities (final int currentMonthIndex) {

        List<Consumer> newConsumers = input.getNewConsumers().get(currentMonthIndex);
        newConsumers.forEach(input::addConsumer);
        List<DistributorChange> distributorChanges = input.getDistributorChanges().get(currentMonthIndex);
        for (DistributorChange distributorChange: distributorChanges) {
            long id = distributorChange.getId();
            long newInfrastructureCost = distributorChange.getInfrastructureCost();
            Distributor d = getDistributor(id);
            d.setInfrastructureCost(newInfrastructureCost);
        }

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

    public Producer getProducer(final long id) {
        List<Producer> producers = input.getProducers();
        for (Producer producer: producers) {
            if (producer.getId() == id) {
                return producer;
            }
        }
        return null;
    }
}
