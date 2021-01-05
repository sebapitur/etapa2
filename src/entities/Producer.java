package entities;

import entityatt.Instancer;
import entityatt.Pricer;
import io.Constants;
import strategies.EnergyChoiceStrategyType;

public class Producer implements Entity{
    long id, energyPerDistributor, maxDistributors;
    Constants energyType;
    double priceKW;

    public Producer(long id, long energyPerDistributor, long maxDistributors, Constants energyType, double priceKW) {
        this.id = id;
        this.energyPerDistributor = energyPerDistributor;
        this.maxDistributors = maxDistributors;
        this.energyType = energyType;
        this.priceKW = priceKW;
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

    }
}
