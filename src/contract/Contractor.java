package contract;

import entities.Consumer;
import entities.Distributor;
import entities.Entity;
import entities.Producer;
import entityatt.Instancer;
import entityatt.Mapper;

import java.util.*;
import java.util.stream.Collectors;

public class Contractor {
    Mapper mapper = new Mapper();
    Instancer instancer;
    public Contractor(Instancer instancer) {
        this.instancer = instancer;
    }

    public void setNrConsumers(final List<Distributor> distributors) {
        distributors.forEach((d) -> d.setNrConsumers(d.getActiveConsumerContracts().size()));
    }

    public void nullifyContracts(final List<Distributor> distributors) {
        distributors.forEach((d) -> d.getActiveConsumerContracts().removeIf((q) -> q.getMonthsRemained() <= 0));
    }

    public void setContractsConsumers(List<Consumer> consumers, List<Distributor> distributors) {
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

    public void setContractsProducers(List<Producer> producers, List<Distributor> distributors) {
        distributors.sort(Comparator.comparingLong(Distributor::getId));
        for(Distributor distributor: distributors) {
            if(distributor.getSearchProducer()) {
                for(Producer producer: producers) {
                    List<ContractDistributorProducer> contracts;
                    contracts = producer.getContractList();
                    contracts.removeIf(o -> o.getDistributorId() == distributor.getId());
                    producer.setContractList(contracts);
                }
                distributor.setActiveContractDistributorProducers(new LinkedList<>());
                List<Producer> sortedProducers = distributor.sortedList(producers);
                int i = 0;
                while (!distributor.isSaturated() && i < sortedProducers.size()) {
                    Producer producer = null;
                    while (i < sortedProducers.size()) {
                        producer = sortedProducers.get(i);
                        if(producer.acceptsClients()) {
                            break;
                        }
                        i++;
                    }
                    ContractDistributorProducer contract =
                            new ContractDistributorProducer(distributor.getId(),
                            producer.getId());
                    distributor.getActiveProducersContracts().add(contract);
                    distributor.updateEnergyReceived(producer.getEnergyPerDistributor());
                    producer.addContract(contract, instancer);
                    distributor.setSearchProducer(false);
                    i++;
                }
            }
        }
    }
}
