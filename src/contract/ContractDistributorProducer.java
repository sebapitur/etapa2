package contract;

public class ContractDistributorProducer extends Contract{

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
}
