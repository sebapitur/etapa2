package entityatt;

import entities.Consumer;
import entities.Distributor;
import entities.Entity;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Contractor {
    Mapper mapper = new Mapper();
    Instancer instancer;

    public Contractor(Instancer instancer) {
        this.instancer = instancer;
    }

    public void setNrConsumers(final List<Entity> distributors) {
        distributors.forEach((o) -> {
            Distributor d = (Distributor) o;
            d.setNrConsumers(d.getActiveContracts().size());
        });
    }

    public void nullifyContracts(final List<Entity> distributors) {
        distributors.forEach((o) -> {
            Distributor d = (Distributor) o;
            d.getActiveContracts().removeIf((q) -> q.getMonthsRemained() <= 0);
        });
    }

    public void setContracts(List<Entity> consumers, List<Entity> distributors) {
        List<Entity> originalDistributors = distributors;
        List<Entity> originalConsumers = consumers;
        consumers = consumers.stream().filter(Entity::isInGame).
                collect(Collectors.toCollection(LinkedList::new));
        distributors = distributors.stream().filter(Entity::isInGame).
                collect(Collectors.toCollection(LinkedList::new));
        Map<Distributor, Long> distributorMonthlyPrice = mapper.getMap(distributors);
        List<Map.Entry<Distributor, Long>> sortedDistributors
                = new LinkedList<>(distributorMonthlyPrice.entrySet());
        sortedDistributors.sort(Comparator.comparingLong(Map.Entry::getValue));
        for (Entity entity : consumers) {
            Consumer consumer = (Consumer) entity;
            Contract contract = new Contract(consumer.getId(),
                    sortedDistributors.get(0).getKey().getId(),
                    sortedDistributors.get(0).getKey().getContractLength(),
                    sortedDistributors.get(0).getValue());
            if (consumer.getActiveContract() == null
                    || consumer.getActiveContract().getMonthsRemained() <= 0) {
                if (consumer.getActiveContract() != null
                        && consumer.getActiveContract().isRestant()) {
                    contract.setRestant(true);
                    contract.setOldPrice(consumer.getActiveContract().getPrice());
                }
                Consumer originalConsumer = instancer.getConsumer(consumer.getId());
                assert originalConsumer != null;
                originalConsumer.setActiveContract(contract);
                Distributor originalDistributor = instancer.getDistributor(
                        sortedDistributors.get(0).getKey().getId());
                assert originalDistributor != null;
                originalDistributor.addContract(contract);
                originalDistributor.setNrConsumers(originalDistributor.getNrConsumers() + 1);
            }
        }
    }
}
