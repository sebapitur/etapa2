package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Consumer;
import entities.Distributor;
import entities.Entity;
import entities.Producer;
import org.w3c.dom.ls.LSInput;

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
        for (Entity entity: distributors){
            System.out.println((Distributor) entity);
        }
        List<?> producersAtt = (List<?>) initialData.get(Constants.getString(Constants.PRODUCERS));
        for (Object producer: producersAtt) {
            producers.add(fact.createEntity(Constants.PRODUCER, (Map<?, ?>) producer));
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


}
