package contract;

import entities.Consumer;
import entities.Distributor;
import entities.Producer;
import entityatt.Instancer;
import io.Input;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ContractorProducer extends Contractor {

   Input input;
    public ContractorProducer(Instancer instancer) {
        super(instancer);
        this.input = instancer.getInput();
    }
    @Override
    public void setContracts() {

        List<Producer> producers = input.getProducers();
        List<Distributor> distributors = input.getDistributors();
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
