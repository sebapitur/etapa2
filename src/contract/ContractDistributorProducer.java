package contract;

public final class ContractDistributorProducer extends Contract {

    public long getDistributorId() {
        return getReceiverId();
    }

    public long getProducerId() {
        return getProviderId();
    }

    public ContractDistributorProducer(long distributorId, long producerId) {
        this.receiverId = distributorId;
        this.providerId = producerId;
    }

    @Override
    public String toString() {
        return "ContractDistributorProducer{"
                + "receiverId=" + receiverId
                + ", providerId=" + providerId
                + '}';
    }
}
