package entityatt;

import entities.Consumer;
import entities.Distributor;
import entities.Entity;
import entities.Producer;
import io.Input;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class EntityModifier {
    Pricer pricer;
    Instancer instancer;
    long monthIndex = 0;

    public EntityModifier(Pricer pricer, Instancer instancer) {
        this.pricer = pricer;
        this.instancer = instancer;
    }

    public void setMonthIndex(long monthIndex) {
        this.monthIndex = monthIndex;
    }

    public void modifyEntities(List<Distributor> distributors, List<Consumer> consumers) {
        distributors = distributors.stream().filter(Entity::isInGame).
                collect(Collectors.toCollection(LinkedList::new));

        this.modifyConsumers(consumers);
        this.modifyDistributors(distributors);
        modifyProducers();
    }

    private void modifyDistributors(List<Distributor> distributors) {
        for (Entity distributorEntity : distributors) {
            distributorEntity.modify(pricer, instancer);
        }
    }

    public void modifyProducers() {
        Input input = instancer.getInput();
        List<ProducerChange> producerChanges = input.getProducerChanges().get((int) monthIndex);
        for (ProducerChange producerChange: producerChanges) {
            long id = producerChange.getId();
            long newEnergyPerDistributor = producerChange.getEnergyPerDistributor();
            Producer p = instancer.getProducer(id);
            p.setEnergyPerDistributor(newEnergyPerDistributor);
        }
    }

    /**
     *
     * @param consumers list
     */
    private void modifyConsumers(List<Consumer> consumers) {
        for (Entity consumerEntity : consumers) {
            consumerEntity.modify(pricer, instancer);
        }
    }
}
