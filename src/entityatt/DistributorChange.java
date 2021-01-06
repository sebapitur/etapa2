package entityatt;

public class DistributorChange extends Change{

    public DistributorChange(long id, long dimension) {
        super(id, dimension);
    }

    public long getInfrastructureCost() {
        return getDimension();
    }

}
