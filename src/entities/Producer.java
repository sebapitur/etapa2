package entities;
import entityatt.ContractDistributorProducer;
import entityatt.Instancer;
import entityatt.Pricer;
import io.Constants;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Producer extends Observable implements Entity {
    long id, energyPerDistributor, maxDistributors;
    Constants energyType;
    double priceKW;
    List<Observer> observers = new LinkedList<>();
    List<ContractDistributorProducer> contractList = new LinkedList<>();
    public Producer(long id, long energyPerDistributor, long maxDistributors, Constants energyType, double priceKW) {
        this.id = id;
        this.energyPerDistributor = energyPerDistributor;
        this.maxDistributors = maxDistributors;
        this.energyType = energyType;
        this.priceKW = priceKW;
    }

    public boolean acceptsClients() {
        return contractList.size() < maxDistributors;
    }

    public void setContractList(List<ContractDistributorProducer> contractList) {
        this.contractList = contractList;
    }

    public long getMaxDistributors() {
        return maxDistributors;
    }

    public List<ContractDistributorProducer> getContractList() {
        return contractList;
    }

    public Constants getEnergyType() {
        return energyType;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public long getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void addContract(ContractDistributorProducer c) {
        contractList.add(c);
    }

    public void resetContractList(){
        contractList = new LinkedList<>();
    }

    public void setEnergyPerDistributor(long energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", energyPerDistributor=" + energyPerDistributor +
                ", maxDistributors=" + maxDistributors +
                ", energyType=" + energyType +
                ", priceKW=" + priceKW +
                '}';
    }

    @Override
    public boolean isInGame() {
        return true;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public long getBudget() {
        return -1;
    }

    @Override
    public void modify(Pricer pricer, Instancer instancer) {
        System.out.println("Cannot modify me like this!");
    }
    @Override
    public synchronized void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        resetContractList();
        for(Observer obs:  observers) {
            obs.update(this, true);
        }
    }
}
