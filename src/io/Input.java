package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Consumer;
import entities.Distributor;
import entities.Entity;
import entities.Producer;
import entityatt.DistributorChange;
import entityatt.ProducerChange;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observer;

public final class Input {
    private long currMonth;
    private long numberOfTurns;
    private List<Consumer> consumers = new LinkedList<>();
    private List<Distributor> distributors = new LinkedList<>();
    private List<Producer> producers = new LinkedList<>();
    private List<List<Consumer>> newConsumers = new LinkedList<>();
    private List<List<DistributorChange>> distributorChanges =  new LinkedList<>();
    private List<List<ProducerChange>> producerChanges = new LinkedList<>();



    public Input(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<?, ?> map = objectMapper.readValue(Paths.get(filePath).toFile(), Map.class);
        numberOfTurns = (Integer) map.get(Constants.getString(Constants.NUMBER_OF_TURNS));
        Map<?, ?> initialData = (Map<?, ?>) map.get(Constants.getString(Constants.INITIAL_DATA));
        List<?> consumersAtt = (List<?>) initialData.get(Constants.getString(Constants.CONSUMERS));
        EntityFactory fact =  EntityFactory.getInstance();
        for (Object consumer: consumersAtt) {
            consumers.add((Consumer) fact.createEntity(Constants.CONSUMER, (Map<?, ?>) consumer));
        }
        List<?> distributorsAtt = (List<?>) initialData.get(Constants.getString(Constants.DISTRIBUTORS));
        for (Object distributor: distributorsAtt) {
            distributors.add((Distributor) fact.createEntity(Constants.DISTRIBUTOR, (Map<?, ?>) distributor));
        }
        List<?> producersAtt = (List<?>) initialData.get(Constants.getString(Constants.PRODUCERS));
        for (Object producer: producersAtt) {
            producers.add((Producer) fact.createEntity(Constants.PRODUCER, (Map<?, ?>) producer));
        }

        for (Producer p: producers) {
            for(Observer o: distributors) {
                p.addObserver(o);
            }
        }

        List<?> monthlyUpdates = (List<?>) map.get(Constants.getString(Constants.MONTHLY_UPDATES));
        for (Object update: monthlyUpdates){
            Map<?,?> updateMap =  (Map<?, ?>)update;
            List<?> newConsumersAtt = (List<?>) updateMap.get(Constants.getString(Constants.NEW_CONSUMERS));
            List<?> distributorsChangeAtt = (List<?>) updateMap.get(Constants.getString(Constants.DISTRIBUTORS_CHANGES));
            List<?> producersChangeAtt = (List<?>) updateMap.get(Constants.getString(Constants.PRODUCER_CHANGES));
            List<Consumer> newConsumersTemp = new LinkedList<>();
            for (Object consumerAtt: newConsumersAtt) {
                newConsumersTemp.add((Consumer) fact.createEntity(Constants.CONSUMER, (Map<?,?>)consumerAtt));
            }
            newConsumers.add(newConsumersTemp);
            List<DistributorChange> distributorChangesTemp =  new LinkedList<>();
            for(Object distributorChangeAtt: distributorsChangeAtt) {
                distributorChangesTemp.add((DistributorChange) fact.createChange(Constants.DISTRIBUTOR, (Map<?, ?>) distributorChangeAtt));
            }
            distributorChanges.add(distributorChangesTemp);
            List<ProducerChange> producerChangesTemp = new LinkedList<>();
            for(Object producerChangeAtt: producersChangeAtt) {
                producerChangesTemp.add((ProducerChange) fact.createChange(Constants.PRODUCER, (Map<?, ?>) producerChangeAtt));
            }
            producerChanges.add(producerChangesTemp);
        }
    }

    /**
     *
     * @param consumer to be added
     */
    public void addConsumer(final Consumer consumer) {
        consumers.add(consumer);
    }

    public long getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final long numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public List<List<Consumer>> getNewConsumers() {
        return newConsumers;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public List<List<DistributorChange>> getDistributorChanges() {
        return distributorChanges;
    }

    public List<List<ProducerChange>> getProducerChanges() {
        return producerChanges;
    }

}
