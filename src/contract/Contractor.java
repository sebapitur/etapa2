package contract;

import entityatt.Instancer;
import entityatt.Mapper;

public abstract class Contractor {
    Mapper mapper = new Mapper();
    Instancer instancer;
    public Contractor(Instancer instancer) {
        this.instancer = instancer;
    }

    public abstract void setContracts();
}
