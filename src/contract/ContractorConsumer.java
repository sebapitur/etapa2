package contract;

import entities.Consumer;
import entities.Distributor;
import entities.Entity;
import entityatt.Instancer;
import io.Input;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContractorConsumer extends Contractor{

    Input input;

    public ContractorConsumer(Instancer instancer) {
        super(instancer);
        this.input = instancer.getInput();
    }
@Override
    public void setContracts() {
        List<Consumer> consumers = input.getConsumers();
        List<Distributor> distributors = input.getDistributors();
        consumers = consumers.stream().filter(Entity::isInGame).
                collect(Collectors.toCollection(LinkedList::new));
        distributors = distributors.stream().filter(Entity::isInGame).
                collect(Collectors.toCollection(LinkedList::new));
        Map<Distributor, Long> distributorMonthlyPrice = mapper.getMap(distributors);
        List<Map.Entry<Distributor, Long>> sortedDistributors
                = new LinkedList<>(distributorMonthlyPrice.entrySet());
        sortedDistributors.sort(Comparator.comparingLong(Map.Entry::getValue));
        for (Consumer consumer : consumers) {
            ContractConsumerDistributor contractConsumerDistributor = new ContractConsumerDistributor(consumer.getId(),
                    sortedDistributors.get(0).getKey().getId(),
                    sortedDistributors.get(0).getKey().getContractLength(),
                    sortedDistributors.get(0).getValue());
            if (consumer.getActiveContract() == null
                    || consumer.getActiveContract().getMonthsRemained() <= 0) {
                if (consumer.getActiveContract() != null
                        && consumer.getActiveContract().isRestant()) {
                    contractConsumerDistributor.setRestant(true);
                    contractConsumerDistributor.setOldPrice(consumer.getActiveContract().getPrice());
                }
                Consumer originalConsumer = instancer.getConsumer(consumer.getId());
                assert originalConsumer != null;
                originalConsumer.setActiveContract(contractConsumerDistributor);
                Distributor originalDistributor = instancer.getDistributor(
                        sortedDistributors.get(0).getKey().getId());
                assert originalDistributor != null;
                originalDistributor.addContract(contractConsumerDistributor);
                originalDistributor.setNrConsumers(originalDistributor.getNrConsumers() + 1);
            }
        }
    }

    public void setNrConsumers() {
        List<Distributor> distributors = input.getDistributors();
        distributors.forEach((d) -> d.setNrConsumers(d.getActiveConsumerContracts().size()));
    }

    public void nullifyContracts() {
        List<Distributor> distributors = input.getDistributors();
        distributors.forEach((d) -> d.getActiveConsumerContracts().removeIf((q) -> q.getMonthsRemained() <= 0));
    }
}
