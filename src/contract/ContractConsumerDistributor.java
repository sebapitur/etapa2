package contract;

public final class ContractConsumerDistributor extends Contract {
    private long monthsRemained;
    private long price;
    private long oldprice = 0;
    private boolean restant;

    public ContractConsumerDistributor(final long consumerId,
                                       final long distributorId,
                                       final long monthsRemained,
                                       final long price) {
        this.receiverId = consumerId;
        this.providerId = distributorId;
        this.monthsRemained = monthsRemained;
        this.price = price;
    }

    public boolean isRestant() {
        return restant;
    }

    public void setRestant(boolean restant) {
        this.restant = restant;
    }

    public long getConsumerId() {
        return getReceiverId();
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getDistributorId() {
        return getProviderId();
    }

    public long getMonthsRemained() {
        return monthsRemained;
    }

    public void setMonthsRemained(long monthsRemained) {
        this.monthsRemained = monthsRemained;
    }

    public void setOldPrice(long p) {
        oldprice = p;
    }

    public long getOldprice() {
        return oldprice;
    }
}
