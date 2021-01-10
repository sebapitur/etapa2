package contract;
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
        List<Distributor> distributors = new LinkedList<>(input.getDistributors());
        while(!distributors.isEmpty()) {
            distributors.sort(Comparator.comparingLong(Distributor::getId));
            Distributor distributor = distributors.get(0);
            if(distributor.getSearchProducer()) {
                removeContracts(producers, distributor);
                signContracts(producers, distributor);
                distributor.setSearchProducer(false);
            }
            distributors.remove(distributor);
        }
    }

    private void signContracts(List<Producer> producers, Distributor distributor) {
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
            i++;
        }
    }

    private void removeContracts(List<Producer> producers, Distributor distributor) {
        for(Producer producer: producers) {
            List<ContractDistributorProducer> contracts = producer.getContractList();
            contracts.removeIf(o -> o.getDistributorId() == distributor.getId());
            producer.getObservers().removeIf(observer -> {
                Distributor d = (Distributor) observer;
                return d.getId() == distributor.getId();
            });
            producer.setContractList(contracts);
        }
        distributor.setActiveContractDistributorProducers(new LinkedList<>());
    }
}
