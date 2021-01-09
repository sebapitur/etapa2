package entityatt;

public class Contract {

    protected long receiverId, providerId;

    public long getReceiverId() {
        return receiverId;
    }

    public long getProviderId() {
        return providerId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }

//    public Contract(long receiverId, long providerId) {
//        this.receiverId = receiverId;
//        this.providerId = providerId;
//    }
}
