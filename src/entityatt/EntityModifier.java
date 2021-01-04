package entityatt;

import entities.Entity;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class EntityModifier {
    Pricer pricer;
    Instancer instancer;

    public EntityModifier(Pricer pricer, Instancer instancer) {
        this.pricer = pricer;
        this.instancer = instancer;
    }

    public void modifyEntities(List<Entity> distributors, List<Entity> consumers) {
        distributors = distributors.stream().filter(Entity::isInGame).
                collect(Collectors.toCollection(LinkedList::new));
        this.modifyConsumers(consumers);
        this.modifyDistributors(distributors);
    }

    private void modifyDistributors(List<Entity> distributors) {
        for (Entity distributorEntity : distributors) {
            distributorEntity.modify(pricer, instancer);
        }
    }

    /**
     *
     * @param consumers list
     */
    private void modifyConsumers(List<Entity> consumers) {
        for (Entity consumerEntity : consumers) {
            consumerEntity.modify(pricer, instancer);
        }
    }
}
