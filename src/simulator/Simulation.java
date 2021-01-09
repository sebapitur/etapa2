package simulator;

import entities.Producer;
import entityatt.Contractor;
import entityatt.EntityModifier;
import entityatt.Instancer;
import entityatt.Pricer;
import io.Input;
import io.Writer;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Simulation {
    Input input;
    Instancer instancer;
    Contractor contractor;
    Pricer pricer;
    EntityModifier mod;


    public Simulation(Input input) {
        this.input = input;
        instancer = new Instancer(input);
        pricer = new Pricer(instancer);
        contractor = new Contractor(instancer);
        mod = new EntityModifier(pricer, instancer);
    }


    public void initialRun() {
        contractor.setContractsProducers(input.getProducers(), input.getDistributors());
        pricer.setProductionCost(input.getDistributors());
        pricer.setPrices(input.getDistributors());
        contractor.setContractsConsumers(input.getConsumers(), input.getDistributors());
        pricer.setMonthlyExpenses(input.getDistributors());
        mod.modifyEntities(input.getDistributors(), input.getConsumers());
    }


    public void normalRun(final int currentMonthIndex) {

        contractor.nullifyContracts(input.getDistributors());
        instancer.addNewEntities(currentMonthIndex);
        pricer.setPrices(input.getDistributors());
        contractor.setNrConsumers(input.getDistributors());
        contractor.setContractsConsumers(input.getConsumers(), input.getDistributors());
        pricer.setMonthlyExpenses(input.getDistributors());
        mod.setMonthIndex(currentMonthIndex);
        mod.modifyEntities(input.getDistributors(), input.getConsumers());
        contractor.setContractsProducers(input.getProducers(), input.getDistributors());
        pricer.setProductionCost(input.getDistributors());
//        mod.modifyProducers();

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
