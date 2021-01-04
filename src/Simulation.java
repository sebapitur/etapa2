import entityatt.*;
import io.Input;


public final class Simulation {

    public static final double RESTANT = 1.2;
    Input input;
    Instancer instancer;
    Contractor contractor;
    Pricer pricer = new Pricer();
    EntityModifier mod;

    public Simulation(Input input) {
        this.input = input;
        instancer = new Instancer(input);
        contractor = new Contractor(instancer);
        mod = new EntityModifier(pricer, instancer);
    }


    public void initialRun() {
        pricer.setPrices(input.getDistributors());
        contractor.setContracts(input.getConsumers(), input.getDistributors());
        pricer.setMonthlyExpenses(input.getDistributors());
        mod.modifyEntities(input.getDistributors(), input.getConsumers());
    }


    public void normalRun(final int currentMonthIndex) {
        contractor.nullifyContracts(input.getDistributors());
        instancer.addNewEntities(currentMonthIndex);
        pricer.setPrices(input.getDistributors());
        contractor.setNrConsumers(input.getDistributors());
        contractor.setContracts(input.getConsumers(), input.getDistributors());
        pricer.setMonthlyExpenses(input.getDistributors());
        mod.modifyEntities(input.getDistributors(), input.getConsumers());
    }


}
