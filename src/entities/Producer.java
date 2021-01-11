package entities;

import contract.ContractDistributorProducer;
import entityatt.Instancer;
import entityatt.Pricer;
import io.Constants;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Producer extends Observable implements Entity {
    private long id, energyPerDistributor, maxDistributors;
    private Constants energyType;
    private double priceKW;
    private List<Observer> observers = new LinkedList<>();
    private List<ContractDistributorProducer> contractList = new LinkedList<>();
    private long tempReceiverId;

    public Producer(long id, long energyPerDistributor,
                    long maxDistributors, Constants energyType, double priceKW) {
        this.id = id;
        this.energyPerDistributor = energyPerDistributor;
        this.maxDistributors = maxDistributors;
        this.energyType = energyType;
        this.priceKW = priceKW;
    }

    /**
     *
     * @param id of the producer
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @param maxDistributors suported by the producer
     */
    public void setMaxDistributors(long maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    /**
     *
     * @param energyType which the producer provides
     */
    public void setEnergyType(Constants energyType) {
        this.energyType = energyType;
    }

    /**
     *
     * @param priceKW offered by producer
     */
    public void setPriceKW(double priceKW) {
        this.priceKW = priceKW;
    }

    /**
     *
     * @param observers of the producers
     */
    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }

    /**
     *
     * @return the observers
     */
    public List<Observer> getObservers() {
        return observers;
    }

    /**
     *
     * @return true if the producer can provide to more distributors
     */
    public boolean acceptsClients() {
        return contractList.size() < maxDistributors;
    }

    /**
     *
     * @param contractList of the producer
     */
    public void setContractList(List<ContractDistributorProducer> contractList) {
        this.contractList = contractList;
    }

    /**
     *
     * @return the maximum number of distributors supported by the producer
     */
    public long getMaxDistributors() {
        return maxDistributors;
    }

    /**
     *
     * @return the contract list
     */
    public List<ContractDistributorProducer> getContractList() {
        return contractList;
    }

    /**
     * @return the energy type provided
     */
    public Constants getEnergyType() {
        return energyType;
    }

    /**
     *
     * @return the price offered
     */
    public double getPriceKW() {
        return priceKW;
    }

    /**
     *
     * @return the quantity offered per distributor
     */
    public long getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    /**
     *
     * @param c the contract
     * @param instancer instantiates  entities
     */
    public void addContract(ContractDistributorProducer c, Instancer instancer) {
        contractList.add(c);
        tempReceiverId = c.getReceiverId();
        modify(null, instancer);

    }

    /**
     *
     * @param energyPerDistributor offered
     */
    public void setEnergyPerDistributor(long energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
        notifyObservers();
    }

    /**
     *
     * @return pretty string format of a producer
     */
    @Override
    public String toString() {
        return "Producer{"
                + "id=" + id
                + ", energyPerDistributor=" + energyPerDistributor
                + ", maxDistributors=" + maxDistributors
                + ", energyType=" + energyType
                + '}';
    }

    /**
     *
     * @return always true;
     */
    @Override
    public boolean isInGame() {
        return true;
    }

    /**
     *
     * @return producer's id
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     *
     * @return budget of the producer
     */
    @Override
    public long getBudget() {
        return -1;
    }

    /**
     *
     * @param pricer instance with various pricing methods
     * @param instancer instantiates entities
     */
    @Override
    public void modify(Pricer pricer, Instancer instancer) {
        Distributor d = instancer.getDistributor(tempReceiverId);
        addObserver(d);
    }

    /**
     *
     * @param o observer
     */
    @Override
    public synchronized void addObserver(Observer o) {
        observers.add(o);
    }

    /**
     * notifies the distributors that the producer changed its attributes
     */
    @Override
    public void notifyObservers() {
        for (Observer obs : observers) {
            obs.update(this, true);
        }
    }
}
