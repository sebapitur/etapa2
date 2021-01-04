package entityatt;

public final class Contract {
    private long consumerId;
    private final long distributorId;
    private long monthsRemained;
    private long price;
    private long oldprice = 0;
    private boolean restant;

    public Contract(final long consumerId,
                    final long distributorId,
                    final long monthsRemained,
                    final long price) {
        this.consumerId = consumerId;
        this.distributorId = distributorId;
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
        return consumerId;
    }

    public void setConsumerId(long consumerId) {
        this.consumerId = consumerId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getDistributorId() {
        return distributorId;
    }

    public long getMonthsRemained() {
        return monthsRemained;
    }

    public void setMonthsRemained(long monthsRemained) {
        this.monthsRemained = monthsRemained;
    }

    public void setOldPrice(long price) {
        oldprice = price;
    }

    public long getOldprice() {
        return oldprice;
    }
}
