package entityatt;

import contract.ContractDistributorProducer;
import entities.Consumer;
import entities.Distributor;
import entities.Entity;
import entities.Producer;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Pricer {
    private static final double PROFITPERCENT = 0.2;
    private static final double RESTANT = 1.2;
    Instancer instancer;

    public Pricer(Instancer instancer) {
        this.instancer = instancer;
    }

    /**
     *
     * @param consumer to pay
     * @param distributor to receive
     * @param oldBudget of the consumer
     */
    public void normalPricing(Consumer consumer, Distributor distributor, long oldBudget) {
        consumer.setBudget(oldBudget - consumer.getActiveContract().getPrice());
        assert distributor != null;
        distributor.setBudget(distributor.getBudget()
                + consumer.getActiveContract().getPrice());
    }

    /**
     *
     * @param consumer to pay
     * @param distributor to receive
     * @param oldBudget of the consumer
     */
    public void restantPricing(Consumer consumer, Distributor distributor, long oldBudget) {
        if (consumer.getActiveContract().getOldprice() == 0) {
            consumer.getActiveContract().setOldPrice(consumer.
                    getActiveContract().getPrice());
        }
        consumer.setBudget(Math.round(Math.floor(oldBudget
                - consumer.getActiveContract().getPrice()
                - RESTANT * consumer.getActiveContract().getOldprice())));
        if (consumer.getBudget() >= 0) {
            consumer.getActiveContract().setRestant(false);
        }
        assert distributor != null;
        distributor.setBudget(Math.round(Math.floor(distributor.getBudget()
                + consumer.getActiveContract().getPrice()
                + RESTANT * consumer.getActiveContract().getOldprice())));
    }

    /**
     *
     * @param consumer who cannot pay
     * @param distributor who won't receive money
     * @param oldBudget of the consumer
     */
    public void returnMoney(Consumer consumer, Distributor distributor, long oldBudget) {
        consumer.setBudget(oldBudget);

        if (consumer.getActiveContract().isRestant()) {
            consumer.setInGame(false);
            distributor.setBudget(distributor.getBudget()
                    - Math.round(Math.floor(consumer.getActiveContract().getPrice()
                    + RESTANT * consumer.getActiveContract().getOldprice())));
            distributor.getActiveConsumerContracts().remove(consumer.getActiveContract());
            distributor.setNrConsumers(distributor.getNrConsumers() - 1);
        } else {
            distributor.setBudget(distributor.getBudget()
                    - Math.round(Math.floor(consumer.getActiveContract().getPrice())));
        }
        if (consumer.isInGame()) {
            consumer.getActiveContract().setRestant(true);
        }
    }

    /**
     *
     * @param distributors list
     */
    public void setMonthlyExpenses(final List<Distributor> distributors) {
        for (Distributor distributor : distributors) {
            distributor.setMonthlyExpense(distributor.getInfrastructureCost()
                    + distributor.getProductionCost() * distributor.getNrConsumers());
        }
    }

    /**
     *
     * @param distributors list
     */
    public void setPrices(List<Distributor> distributors) {
        distributors = distributors.stream().filter(Entity::isInGame).
                collect(Collectors.toCollection(LinkedList::new));
        for (Distributor distributor : distributors) {
            if (distributor.getNrConsumers() == 0) {
                distributor.setPriceOfContract(Math.round(distributor.getInfrastructureCost()
                        + distributor.getProductionCost()
                        + Math.round(Math.floor(PROFITPERCENT * distributor.getProductionCost()))));
            } else {
                distributor.setPriceOfContract(Math.round(
                        Math.floor((float) distributor.getInfrastructureCost()
                                / distributor.getNrConsumers())
                                + distributor.getProductionCost()
                                + Math.round(Math.floor(PROFITPERCENT
                                * distributor.getProductionCost()))));
            }
        }
    }

    /**
     *
     * @param distributors list
     */
    public void setProductionCost(List<Distributor> distributors) {
        long productionCost = 0;
        for (Distributor distributor : distributors) {
            List<ContractDistributorProducer> contractList;
            contractList = distributor.getActiveProducersContracts();
            double sum = 0;
            for (ContractDistributorProducer c : contractList) {
                long pId = c.getProducerId();
                Producer producer = instancer.getProducer(pId);
                sum += producer.getEnergyPerDistributor() * producer.getPriceKW();
            }
            productionCost = Math.round(Math.floor(sum / 10));
            distributor.setProductionCost(productionCost);
        }

    }

}
