package entities;

import entityatt.Instancer;
import entityatt.Pricer;

public interface Entity {
    /**
     * @return true if entity is not bankrupt
     */
    boolean isInGame();

    /**
     * @return entity's id
     */
    long getId();

    /**
     * @return entity's budget
     */
    long getBudget();

    /**
     *
     * @param pricer instance with various pricing methods
     * @param instancer instantiates entities
     */
    void modify(Pricer pricer, Instancer instancer);
}
