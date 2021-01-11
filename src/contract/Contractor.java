package contract;

import entityatt.Instancer;
import entityatt.Mapper;

public abstract class Contractor {
    private final Mapper mapper = new Mapper();
    private final Instancer instancer;

    public Contractor(Instancer instancer) {
        this.instancer = instancer;
    }

    /**
     *
     * @return a Mapper instance which can map
     * distributors with the price of contract
     */
    public Mapper getMapper() {
        return mapper;
    }

    /**
     *
     * @return a Instancer entity which can instantiate other entities
     */
    public Instancer getInstancer() {
        return instancer;
    }

    /**
     * sets the contracts between receivers and providers
     */
    public abstract void setContracts();
}
