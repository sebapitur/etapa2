package entities;

import entityatt.ContractConsumerDistributor;
import entityatt.Instancer;
import entityatt.Pricer;

public class Consumer implements Entity {
    private final long id;
    private long budget;
    private final long monthlyIncome;
    private ContractConsumerDistributor activeContractConsumerDistributor = null;
    private boolean inGame = true;

    /**
     *
     * @return consumer's monthly income
     */
    public long getMonthlyIncome() {
        return monthlyIncome;
    }

    /**
     *
     * @param inGame true if consumer is in game, false otherwise
     */
    public void setInGame(final boolean inGame) {
        this.inGame = inGame;
    }

    /**
     *
     * @return consumer's budget
     */
    public long getBudget() {
        return budget;
    }

    /**
     *
     * @param budget sets consumer's budget
     */
    public void setBudget(final long budget) {
        this.budget = budget;
    }

    public Consumer(final long id, final long initialBudget, final long monthlyIncome) {
        this.id = id;
        this.budget = initialBudget;
        this.monthlyIncome = monthlyIncome;
    }

    /**
     *
     * @return consumer's id
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @return consumer's active contract
     */
    public ContractConsumerDistributor getActiveContract() {
        return activeContractConsumerDistributor;
    }

    /**
     *
     * @param activeContractConsumerDistributor set consumer's active contract
     */
    public void setActiveContract(final ContractConsumerDistributor activeContractConsumerDistributor) {
        this.activeContractConsumerDistributor = activeContractConsumerDistributor;
    }

    /**
     *
     * @return prints consumer in a more readable way
     */
    @Override
    public String toString() {
        return "Consumer{"
                + "id=" + id
                + ", budget=" + budget
                + ", monthlyIncome=" + monthlyIncome
                + ", activeContract=" + activeContractConsumerDistributor
                + ", inGame=" + inGame
                + '}';
    }


    /**
     *
     * @return false if the consumer is bankrupt, true otherwise
     */
    @Override
    public boolean isInGame() {
        return this.inGame;
    }
    
    public void modify(Pricer pricer, Instancer instancer){
        Consumer consumer = this;
        if (consumer.isInGame()) {
            consumer.setBudget(consumer.getBudget() + consumer.getMonthlyIncome());
            Distributor distributor = instancer.getDistributor(
                    consumer.getActiveContract().getDistributorId());
            long oldBudget = consumer.getBudget();
            if (!consumer.getActiveContract().isRestant()) {
                pricer.normalPricing(consumer, distributor, oldBudget);
            } else {
                pricer.restantPricing(consumer, distributor, oldBudget);
            }
            if (consumer.getBudget() < 0) {
                pricer.returnMoney(consumer, distributor, oldBudget);
            }
        }
    }
}
