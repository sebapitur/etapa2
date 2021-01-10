package simulator;

import contract.ContractorConsumer;
import contract.ContractorProducer;
import entities.Producer;
import contract.Contractor;
import entityatt.EntityModifier;
import entityatt.Instancer;
import entityatt.Pricer;
import io.Input;
import io.Writer;

import java.sql.SQLOutput;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Simulation {
    Input input;
    Instancer instancer;
    ContractorConsumer contractorConsumer;
    ContractorProducer contractorProducer;
    Pricer pricer;
    EntityModifier mod;


    public Simulation(Input input) {
        this.input = input;
        instancer = new Instancer(input);
        pricer = new Pricer(instancer);
        contractorConsumer = new ContractorConsumer(instancer);
        contractorProducer = new ContractorProducer(instancer);
        mod = new EntityModifier(pricer, instancer);
    }


    public void initialRun() {
        contractorProducer.setContracts();
        pricer.setProductionCost(input.getDistributors());
        pricer.setPrices(input.getDistributors());
        contractorConsumer.setContracts();
        pricer.setMonthlyExpenses(input.getDistributors());
        mod.modifyEntities(input.getDistributors(), input.getConsumers());
    }


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

    public List<List<Map<String, Object>>> simulate(Input input, Writer writer) {
        List<List<Map<String, Object>>> list = new LinkedList<>();
        for(Producer producer: input.getProducers()) {
            list.add(new LinkedList<>());
        }
        this.initialRun();
        for(int i = 0; i < input.getNumberOfTurns(); i++) {
            this.normalRun(i);

            input.getProducers().sort(Comparator.comparingLong(Producer::getId));
            writer.writeProducerHistory(input, list, i+1);

        }
        return list;
    }
}
