package entities;

import entityatt.Contract;
import entityatt.Instancer;
import entityatt.Pricer;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Distributor implements Entity {
    private final long id;
    private final long contractLength;
    private long budget;
    private long infrastructureCost;
    private long productionCost;
    private long nrConsumers = 0;
    private long priceOfContract;
    private long monthlyExpense;
    private List<Contract> activeContracts = new LinkedList<>();
    private boolean inGame = true;

    /**
     *
     * @param activeContracts List with the distributor's active contracts
     */
    public void setActiveContracts(List<Contract> activeContracts) {
        this.activeContracts = activeContracts;
    }

    /**
     *
     * @return distributor's budget
     */
    public long getBudget() {
        return budget;
    }

    @Override
    public void modify(Pricer pricer, Instancer instancer) {

        Distributor distributor = this;
        distributor.getActiveContracts().forEach((o) ->
                o.setMonthsRemained(o.getMonthsRemained() - 1));
        distributor.getActiveContracts().removeIf(Objects::isNull);

        distributor.setBudget(distributor.getBudget() - distributor.getMonthlyExpense());
        if (distributor.getBudget() < 0) {
            distributor.setInGame(false);
        }
    }

    /**
     *
     * @param budget sets distributor's budget
     */
    public void setBudget(long budget) {
        this.budget = budget;
    }

    /**
     *
     * @param inGame true if the distributor is not bankrupt
     */
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    /**
     *
     * @return distributor's contract lenght in months
     */
    public long getContractLength() {
        return contractLength;
    }

    /**
     *
     * @return distributor's id
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @return distributor's monthly expense
     */
    public long getMonthlyExpense() {
        return monthlyExpense;
    }

    /**
     *
     * @param monthlyExpense sets distributor's monthly expense
     */
    public void setMonthlyExpense(long monthlyExpense) {
        this.monthlyExpense = monthlyExpense;
    }

    /**
     *
     * @return distributor's infrastructure cost
     */
    public long getInfrastructureCost() {
        return infrastructureCost;
    }

    /**
     *
     * @param infrastructureCost infrastructure cost
     */
    public void setInfrastructureCost(long infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    /**
     *
     * @return production cost
     */
    public long getProductionCost() {
        return productionCost;
    }

    /**
     *
     * @param productionCost production cost
     */
    public void setProductionCost(final long productionCost) {
        this.productionCost = productionCost;
    }



    public Distributor(final long id, final long contractLength,
                       final long initialBudget, final long initialInfrastructureCost,
                       final long initialProductionCost) {
        this.id = id;
        this.contractLength = contractLength;
        this.budget = initialBudget;
        this.infrastructureCost = initialInfrastructureCost;
        this.productionCost = initialProductionCost;
    }

    /**
     *
     * @return string with distributor's fields
     */
    @Override
    public String toString() {
        return "Distributor{"
                + "id=" + id
                + ", contractLength=" + contractLength
                + ", budget=" + budget
                + ", infrastructureCost=" + infrastructureCost
                + ", productionCost=" + productionCost
                + ", nrConsumers=" + nrConsumers
                + ", priceOfContract=" + priceOfContract
                + ", monthlyExpense=" + monthlyExpense
                + ", activeContracts=" + activeContracts
                + ", inGame=" + inGame
                + '}';
    }

    /**
     *
     * @param contract distributor's contract
     */
    public void addContract(Contract contract) {
        activeContracts.add(contract);
    }

    /**
     *
     * @return distributor's price of contract
     */
    public long getPriceOfContract() {
        return priceOfContract;
    }

    /**
     *
     * @param priceOfContract distributor's price of contract
     */
    public void setPriceOfContract(long priceOfContract) {
        this.priceOfContract = priceOfContract;
    }

    /**
     *
     * @return distributor's nr of customers
     */
    public long getNrConsumers() {
        return nrConsumers;
    }

    /**
     *
     * @param nrConsumers distributor's number of customers
     */
    public void setNrConsumers(long nrConsumers) {
        this.nrConsumers = nrConsumers;
    }

    /**
     *
     * @return distributor's list of active contracts
     */
    public List<Contract> getActiveContracts() {
        return activeContracts;
    }

    /**
     *
     * @return true if distributor is not bankrupt
     */
    @Override
    public boolean isInGame() {
        return this.inGame;
    }
}
