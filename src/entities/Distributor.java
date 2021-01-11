package entities;

import contract.ContractConsumerDistributor;
import contract.ContractDistributorProducer;
import entityatt.Instancer;
import entityatt.Pricer;
import strategies.*;

import java.util.*;

public class Distributor implements Entity, Observer {
    private final long id;
    private final long contractLength;
    private final long energyNeededKW;
    private long energyReceived;
    private long budget;
    private long infrastructureCost;
    private long productionCost;
    private final EnergyChoiceStrategyType strategyType;
    private Strategy strategy;
    private long nrConsumers = 0;
    private long priceOfContract;
    private long monthlyExpense;
    private final List<ContractConsumerDistributor>
            activeContractConsumerDistributors = new LinkedList<>();
    private List<ContractDistributorProducer>
            activeContractDistributorProducers = new LinkedList<>();
    private boolean inGame = true;
    private boolean searchProducer = true;

    public Distributor(long id, long contractLength, long initialBudget,
                       long initialInfrastructureCost,
                       long energyNeededKW, EnergyChoiceStrategyType strategyType) {
        this.id = id;
        this.contractLength = contractLength;
        this.budget = initialBudget;
        this.infrastructureCost = initialInfrastructureCost;
        this.strategyType = strategyType;
        this.energyNeededKW = energyNeededKW;
        this.energyReceived = 0;
        this.strategy = StrategyFactory.getStrategy(strategyType);
    }

    /**
     *
     * @return distributor's strategy
     */
    public Strategy getStrategy() {
        return strategy;
    }

    /**
     *
     * @param strategy sets distributor's strategy
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     *
     * @return Strategy Instance
     */
    public EnergyChoiceStrategyType getStrategyType() {
        return strategyType;
    }

    /**
     *
     * @return EnergyNeededKW
     */
    public long getEnergyNeededKW() {
        return energyNeededKW;
    }

    /**
     *
     * @param productionCost Production Cost
     */
    public void setProductionCost(long productionCost) {
        this.productionCost = productionCost;
    }

    /**
     *
     * @return true if distributor satisfied its energy needs
     */
    public boolean isSaturated() {
        return energyReceived >= energyNeededKW;
    }

    /**
     *
     * @return true if distributor should reconsider its Producer options
     */
    public boolean getSearchProducer() {
        return searchProducer;
    }

    /**
     *
     * @param searchProducer false if distributor should
     *                       not reconsider its Producer options
     */
    public void setSearchProducer(boolean searchProducer) {
        if (searchProducer) {
            this.energyReceived = 0;
        }
        this.searchProducer = searchProducer;
    }

    /**
     *
     * @param energyReceivedToBeAdded the energy to be added
     */
    public void updateEnergyReceived(long energyReceivedToBeAdded) {
        this.energyReceived += energyReceivedToBeAdded;
    }

    /**
     *
     * @param activeContractDistributorProducers list of
     *       contracts between distributors and proucers
     */
    public void setActiveContractDistributorProducers(List<ContractDistributorProducer>
                                                              activeContractDistributorProducers) {
        this.activeContractDistributorProducers = activeContractDistributorProducers;
    }

    /**
     * @return distributor's budget
     */
    public long getBudget() {
        return budget;
    }

    /**
     *
     * @param pricer instance with different pricing methods
     * @param instancer used to instantiate entities
     */
    @Override
    public void modify(Pricer pricer, Instancer instancer) {

        Distributor distributor = this;
        distributor.getActiveConsumerContracts().forEach((o) ->
                o.setMonthsRemained(o.getMonthsRemained() - 1));
        distributor.getActiveConsumerContracts().removeIf(Objects::isNull);

        distributor.setBudget(distributor.getBudget() - distributor.getMonthlyExpense());
        if (distributor.getBudget() < 0) {
            distributor.setInGame(false);
        }
    }

    /**
     * @param budget sets distributor's budget
     */
    public void setBudget(long budget) {
        this.budget = budget;
    }

    /**
     * @param inGame true if the distributor is not bankrupt
     */
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    /**
     * @return distributor's contract length in months
     */
    public long getContractLength() {
        return contractLength;
    }

    /**
     * @return distributor's id
     */
    public long getId() {
        return id;
    }

    /**
     * @return distributor's monthly expense
     */
    public long getMonthlyExpense() {
        return monthlyExpense;
    }

    /**
     * @param monthlyExpense sets distributor's monthly expense
     */
    public void setMonthlyExpense(long monthlyExpense) {
        this.monthlyExpense = monthlyExpense;
    }

    /**
     * @return distributor's infrastructure cost
     */
    public long getInfrastructureCost() {
        return infrastructureCost;
    }

    /**
     * @param infrastructureCost infrastructure cost
     */
    public void setInfrastructureCost(long infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    /**
     * @return production cost
     */
    public long getProductionCost() {
        return productionCost;
    }

    /**
     *
     * @return pretty string format of a distributor
     */
    @Override
    public String toString() {
        return "Distributor{"
                + "id=" + id
                + ", contractLength=" + contractLength
                + ", budget=" + budget
                + ", infrastructureCost=" + infrastructureCost
                + ", productionCost=" + productionCost
                + ", strategy=" + strategyType
                + ", nrConsumers=" + nrConsumers
                + ", priceOfContract=" + priceOfContract
                + ", monthlyExpense=" + monthlyExpense
                + ", activeContracts=" + activeContractConsumerDistributors
                + ", inGame=" + inGame
                + '}';
    }

    /**
     * @param contractConsumerDistributor distributor's contract
     */
    public void addContract(ContractConsumerDistributor contractConsumerDistributor) {
        activeContractConsumerDistributors.add(contractConsumerDistributor);
    }

    /**
     * @return distributor's price of contract
     */
    public long getPriceOfContract() {
        return priceOfContract;
    }

    /**
     * @param priceOfContract distributor's price of contract
     */
    public void setPriceOfContract(long priceOfContract) {
        this.priceOfContract = priceOfContract;
    }

    /**
     * @return distributor's nr of customers
     */
    public long getNrConsumers() {
        return nrConsumers;
    }

    /**
     * @param nrConsumers distributor's number of customers
     */
    public void setNrConsumers(long nrConsumers) {
        this.nrConsumers = nrConsumers;
    }

    /**
     * @return the list with the consumer contracts
     */
    public List<ContractConsumerDistributor> getActiveConsumerContracts() {
        return activeContractConsumerDistributors;
    }

    /**
     *
     * @return the list with the producer contracts
     */
    public List<ContractDistributorProducer> getActiveProducersContracts() {
        return activeContractDistributorProducers;
    }

    /**
     * @return true if distributor is not bankrupt
     */
    @Override
    public boolean isInGame() {
        return this.inGame;
    }

    /**
     *
     * @param o ignored
     * @param arg ignored
     */
    @Override
    public void update(Observable o, Object arg) {
        setSearchProducer(true);
    }

    /**
     *
     * @param list of producers
     * @return sorted producers
     */
    public List<Producer> sortedList(List<Producer> list) {
        return strategy.getSortedList(list);
    }

}
