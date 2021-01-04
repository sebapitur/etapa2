package entityatt;

public final class CostChange {
    private final long id, infrastructureCost, productionCost;

    /**
     *
     * @param id of distributor
     * @param infrastructureCost of distributor
     * @param productionCost of distributor
     */
    public CostChange(final long id, final long infrastructureCost, final long productionCost) {
        this.id = id;
        this.infrastructureCost = infrastructureCost;
        this.productionCost = productionCost;
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
     * @return distributor's infrastructure cost
     */
    public long getInfrastructureCost() {
        return infrastructureCost;
    }

    /**
     *
     * @return distributor's production cost
     */
    public long getProductionCost() {
        return productionCost;
    }

    @Override
    public String toString() {
        return "CostChange{"
                + "id=" + id
                + ", infrastructureCost=" + infrastructureCost
                + ", productionCost=" + productionCost
                + '}';
    }
}
