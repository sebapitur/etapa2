package entityatt;

public class ContractDistributorProducer extends Contract{

    long getDistributorId() {
        return getReceiverId();
    }
    long getProducerId() {
        return getProviderId();
    }

    public ContractDistributorProducer(long distributorId, long producerId) {
        this.receiverId = distributorId;
        this.providerId = producerId;
    }
}
