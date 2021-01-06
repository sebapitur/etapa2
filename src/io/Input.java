package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Entity;
import entityatt.Change;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class Input {

    private long numberOfTurns;
    private List<Entity> consumers = new LinkedList<>();
    private List<Entity> distributors = new LinkedList<>();
    private List<Entity> producers = new LinkedList<>();
    private List<List<Entity>> newConsumers = new LinkedList<>();
    private List<List<Change>> distributorChanges =  new LinkedList<>();
    private List<List<Change>> producerChanges = new LinkedList<>();



    public Input(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<?, ?> map = objectMapper.readValue(Paths.get(filePath).toFile(), Map.class);
        numberOfTurns = (Integer) map.get(Constants.getString(Constants.NUMBER_OF_TURNS));
        Map<?, ?> initialData = (Map<?, ?>) map.get(Constants.getString(Constants.INITIAL_DATA));
        List<?> consumersAtt = (List<?>) initialData.get(Constants.getString(Constants.CONSUMERS));
        EntityFactory fact =  EntityFactory.getInstance();
        for (Object consumer: consumersAtt) {
            consumers.add(fact.createEntity(Constants.CONSUMER, (Map<?, ?>) consumer));
        }
        List<?> distributorsAtt = (List<?>) initialData.get(Constants.getString(Constants.DISTRIBUTORS));
        for (Object distributor: distributorsAtt) {
            distributors.add(fact.createEntity(Constants.DISTRIBUTOR, (Map<?, ?>) distributor));
        }
        List<?> producersAtt = (List<?>) initialData.get(Constants.getString(Constants.PRODUCERS));
        for (Object producer: producersAtt) {
            producers.add(fact.createEntity(Constants.PRODUCER, (Map<?, ?>) producer));
        }
        List<?> monthlyUpdates = (List<?>) map.get(Constants.getString(Constants.MONTHLY_UPDATES));
        for (Object update: monthlyUpdates){
            Map<?,?> updateMap =  (Map<?, ?>)update;
            List<?> newConsumersAtt = (List<?>) updateMap.get(Constants.getString(Constants.NEW_CONSUMERS));
            List<?> distributorsChangeAtt = (List<?>) updateMap.get(Constants.getString(Constants.DISTRIBUTORS_CHANGES));
            List<?> producersChangeAtt = (List<?>) updateMap.get(Constants.getString(Constants.PRODUCER_CHANGES));
            List<Entity> newConsumersTemp = new LinkedList<>();
            for (Object consumerAtt: newConsumersAtt) {
                newConsumersTemp.add(fact.createEntity(Constants.CONSUMER, (Map<?,?>)consumerAtt));
            }
            newConsumers.add(newConsumersTemp);
            List<Change> distributorChangesTemp =  new LinkedList<>();
            for(Object distributorChangeAtt: distributorsChangeAtt) {
                distributorChangesTemp.add(fact.createChange(Constants.DISTRIBUTOR, (Map<?, ?>) distributorChangeAtt));
            }
            distributorChanges.add(distributorChangesTemp);
            List<Change> producerChangesTemp = new LinkedList<>();
            for(Object producerChangeAtt: producersChangeAtt) {
                producerChangesTemp.add(fact.createChange(Constants.PRODUCER, (Map<?, ?>) producerChangeAtt));
            }
            producerChanges.add(producerChangesTemp);
        }
    }

    /**
     *
     * @param consumer to be added
     */
    public void addConsumer(final Entity consumer) {
        consumers.add(consumer);
    }

    public long getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final long numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public List<Entity> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<Entity> consumers) {
        this.consumers = consumers;
    }

    public List<Entity> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<Entity> distributors) {
        this.distributors = distributors;
    }


    public List<List<Entity>> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final List<List<Entity>> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<Entity> getProducers() {
        return producers;
    }

    public List<List<Change>> getDistributorChanges() {
        return distributorChanges;
    }

    public List<List<Change>> getProducerChanges() {
        return producerChanges;
    }

}
