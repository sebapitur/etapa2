package simulator;

import contract.ContractorConsumer;
import contract.ContractorProducer;
import entities.Producer;
import entityatt.EntityModifier;
import entityatt.Instancer;
import entityatt.Pricer;
import io.Input;
import io.Writer;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class Simulation {
    private final Input input;
    private final Instancer instancer;
    private final ContractorConsumer contractorConsumer;
    private final ContractorProducer contractorProducer;
    private final Pricer pricer;
    private final EntityModifier mod;


    public Simulation(Input input) {
        this.input = input;
        instancer = new Instancer(input);
        pricer = new Pricer(instancer);
        contractorConsumer = new ContractorConsumer(instancer);
        contractorProducer = new ContractorProducer(instancer);
        mod = new EntityModifier(pricer, instancer);
    }

    /**
     * initial round of simulation
     */
    public void initialRun() {
        contractorProducer.setContracts();
        pricer.setProductionCost(input.getDistributors());
        pricer.setPrices(input.getDistributors());
        contractorConsumer.setContracts();
        pricer.setMonthlyExpenses(input.getDistributors());
        mod.modifyEntities(input.getDistributors(), input.getConsumers());
    }

    /**
     * arbitrary round of simulation
     * @param currentMonthIndex of the round
     */
    public void normalRun(final int currentMonthIndex) {

        contractorConsumer.nullifyContracts();
        instancer.addNewEntities(currentMonthIndex);
        pricer.setPrices(input.getDistributors());
        contractorConsumer.setNrConsumers();
        contractorConsumer.setContracts();
        pricer.setMonthlyExpenses(input.getDistributors());
        mod.setMonthIndex(currentMonthIndex);
        mod.modifyEntities(input.getDistributors(), input.getConsumers());
        contractorProducer.setContracts();
        pricer.setProductionCost(input.getDistributors());

    }

    /**
     *
     * @param writer instance to get json format entities
     * @return the history of the producers in each month
     */
    public List<List<Map<String, Object>>> simulate(Writer writer) {
        List<List<Map<String, Object>>> list = new LinkedList<>();
        for (Producer producer : input.getProducers()) {
            list.add(new LinkedList<>());
        }
        this.initialRun();
        for (int i = 0; i < input.getNumberOfTurns(); i++) {
            this.normalRun(i);

            input.getProducers().sort(Comparator.comparingLong(Producer::getId));
            writer.writeProducerHistory(input, list, i + 1);

        }
        return list;
    }

    public Input getInput() {
        return input;
    }

    public Instancer getInstancer() {
        return instancer;
    }

    public ContractorConsumer getContractorConsumer() {
        return contractorConsumer;
    }

    public ContractorProducer getContractorProducer() {
        return contractorProducer;
    }

    public Pricer getPricer() {
        return pricer;
    }

    public EntityModifier getMod() {
        return mod;
    }
}
