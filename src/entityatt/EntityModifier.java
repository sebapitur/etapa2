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
    private Pricer pricer;
    private Instancer instancer;
    private long monthIndex = 0;

    public EntityModifier(Pricer pricer, Instancer instancer) {
        this.pricer = pricer;
        this.instancer = instancer;
    }

    /**
     *
     * @return the pricer instance
     */
    public Pricer getPricer() {
        return pricer;
    }

    /**
     *
     * @param pricer instance
     */
    public void setPricer(Pricer pricer) {
        this.pricer = pricer;
    }

    /**
     *
     * @return the instancer
     */
    public Instancer getInstancer() {
        return instancer;
    }

    /**
     *
     * @param instancer entity
     */
    public void setInstancer(Instancer instancer) {
        this.instancer = instancer;
    }

    /**
     *
     * @return round current month
     */
    public long getMonthIndex() {
        return monthIndex;
    }

    /**
     *
     * @param monthIndex of the current round
     */
    public void setMonthIndex(long monthIndex) {
        this.monthIndex = monthIndex;
    }

    /**
     *
     * @param distributors list
     * @param consumers list
     */
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

    private void modifyProducers() {
        Input input = instancer.getInput();
        List<ProducerChange> producerChanges = input.getProducerChanges().get((int) monthIndex);
        for (ProducerChange producerChange : producerChanges) {
            long id = producerChange.getId();
            long newEnergyPerDistributor = producerChange.getEnergyPerDistributor();
            Producer p = instancer.getProducer(id);
            p.setEnergyPerDistributor(newEnergyPerDistributor);
        }
    }

    /**
     * @param consumers list
     */
    private void modifyConsumers(List<Consumer> consumers) {
        for (Entity consumerEntity : consumers) {
            consumerEntity.modify(pricer, instancer);
        }
    }
}
