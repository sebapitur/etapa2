package entityatt;

public final class ProducerChange extends Change {
    public ProducerChange(long id, long dimension) {
        super(id, dimension);
    }

    public long getEnergyPerDistributor() {
        return getDimension();
    }
}
