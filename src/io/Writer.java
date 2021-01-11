package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Consumer;
import entities.Distributor;
import entities.Producer;
import contract.ContractConsumerDistributor;
import contract.ContractDistributorProducer;
import strategies.EnergyChoiceStrategyType;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The class writes the output in files
 * <p>
 * DO NOT MODIFY
 */
public final class Writer {
    /**
     * The file where the data will be written
     */

    private final FileWriter file;

    public Writer(final String path) throws IOException {
        this.file = new FileWriter(path);
    }


    /**
     *
     * @param input instance to get the producers list
     * @param list the list we use in the final file
     * @param month current month
     */
    public void writeProducerHistory(Input input, List<List<Map<String,
            Object>>> list, long month) {
        int i = 0;
        for (Producer producer : input.getProducers()) {
            List<Long> ids = new LinkedList<>();
            for (ContractDistributorProducer c : producer.getContractList()) {
                ids.add(c.getReceiverId());
            }
            ids = ids.stream().distinct().collect(Collectors.toList());
            ids.sort(Comparator.comparingLong(o -> o));
            list.get(i).add(this.writeFileProducerContract(month, ids));
            i++;
        }
    }

    /**
     * @param id         of consumer
     * @param isBankrupt true if consumer is not in game
     * @param budget     of consumer
     * @return map with consumer's attributes
     */
    public Map<String, Object> writeFileConsumer(final long id,
                                                 final boolean isBankrupt,
                                                 final long budget) {
        Map<String, Object> object = new LinkedHashMap<>();
        object.put(Constants.getString(Constants.ID), id);
        object.put(Constants.getString(Constants.IS_BANKRUPT), isBankrupt);
        object.put(Constants.getString(Constants.BUDGET), budget);
        return object;
    }

    /**
     * @param id         id of the distributor
     * @param budget     budget of the distributor
     * @param isBankrupt true if the distributor is bankrupt an false otherwise
     * @param contracts  contract list of the distributor
     * @return distributor Map<field, value>
     */
    public Map<String, Object> writeFileDistributor(final long id,
                                                    final long energyNeededKW,
                                                    final long contractCost,
                                                    final long budget,
                                                    final String producerStrategy,
                                                    final boolean isBankrupt,
                                                    final List<Map<String, Object>> contracts) {
        Map<String, Object> object = new LinkedHashMap<>();
        object.put(Constants.getString(Constants.ID), id);
        object.put(Constants.getString(Constants.ENERGY_NEEDED_KW), energyNeededKW);
        object.put(Constants.getString(Constants.CONTRACT_COST), contractCost);
        object.put(Constants.getString(Constants.BUDGET), budget);
        object.put(Constants.getString(Constants.PRODUCER_STRATEGY), producerStrategy);
        object.put(Constants.getString(Constants.IS_BANKRUPT), isBankrupt);
        object.put(Constants.getString(Constants.CONTRACTS), contracts);
        return object;
    }

    /**
     *
     * @param id of the producer
     * @param maxDistributors of the producer
     * @param priceKW of the producer
     * @param energyType of the producer
     * @param energyPerDistributor of the producer
     * @param months -> monthly stats of the producer
     * @return producer in json format
     */
    public Map<String, Object> writeFileProducer(long id, long maxDistributors,
                                                 double priceKW, String energyType,
                                                 long energyPerDistributor,
                                                 List<Map<String, Object>> months) {
        Map<String, Object> object = new LinkedHashMap<>();
        object.put(Constants.getString(Constants.ID), id);
        object.put(Constants.getString(Constants.MAX_DISTRIBUTORS), maxDistributors);
        object.put(Constants.getString(Constants.PRICE_KW), priceKW);
        object.put(Constants.getString(Constants.ENERGY_TYPE), energyType);
        object.put(Constants.getString(Constants.ENERGY_PER_DISTRIBUTOR), energyPerDistributor);
        object.put(Constants.getString(Constants.MONTHLY_STATS), months);
        return object;
    }

    /**
     *
     * @param month current month
     * @param disIds distributor ids which the producer has contract with
     * @return json format producer contract
     */

    public Map<String, Object> writeFileProducerContract(long month, List<Long> disIds) {
        Map<String, Object> object = new LinkedHashMap<>();
        object.put(Constants.getString(Constants.MONTH), month);
        object.put(Constants.getString(Constants.DISTRIBUTORS_IDS), disIds);
        return object;
    }

    /**
     * @param consumerId             in contract
     * @param price                  of contract
     * @param remainedContractMonths contracted
     * @return map with contract attributes
     */
    public Map<String, Object> writeFileConsumerContract(final long consumerId, final long price,
                                                         final long remainedContractMonths) {
        Map<String, Object> object = new LinkedHashMap<>();
        object.put(Constants.getString(Constants.CONSUMER_ID), consumerId);
        object.put(Constants.getString(Constants.PRICE), price);
        object.put(Constants.getString(Constants.REMAINED_CONTRACT_MONTHS),
                remainedContractMonths);
        return object;
    }

    /**
     *
     * @param input to get the entities lists
     * @param producersHistory the monthly stats of the producers
     * @param consumers list of consumers in json format
     * @param distributors list of distributors in json format
     * @param producers list of producers in json format
     */
    public void finalLists(Input input, List<List<Map<String,
                           Object>>> producersHistory,
                           List<Map<String, Object>> consumers,
                           List<Map<String, Object>> distributors,
                           List<Map<String, Object>> producers) {
        for (Consumer consumer : input.getConsumers()) {
            consumers.add(this.writeFileConsumer(consumer.getId(),
                    !consumer.isInGame(),
                    consumer.getBudget()));
        }

        for (Distributor distributor : input.getDistributors()) {

            List<Map<String, Object>> contracts = new LinkedList<>();
            for (ContractConsumerDistributor c : distributor.getActiveConsumerContracts()) {
                contracts.add(this.writeFileConsumerContract(c.getConsumerId(),
                                                            c.getPrice(),
                                                            c.getMonthsRemained()));
            }
            distributors.add(this.writeFileDistributor(distributor.getId(),
                    distributor.getEnergyNeededKW(), distributor.getPriceOfContract(),
                    distributor.getBudget(),
                    EnergyChoiceStrategyType.getString(distributor.getStrategyType()),
                    !distributor.isInGame(), contracts));
        }
        int i = 0;

        for (Producer producer : input.getProducers()) {
            producers.add(this.writeFileProducer(producer.getId(), producer.getMaxDistributors(),
                    producer.getPriceKW(), Constants.getString(producer.getEnergyType()),
                    producer.getEnergyPerDistributor(), producersHistory.get(i)));
            ++i;
        }
        producers.sort(Comparator.comparingLong(o -> (Long) o.get(Constants.
                getString(Constants.ID))));

    }

    /**
     * @param consumers    list
     * @param distributors list
     */
    public void closeJSON(final List<Map<String, Object>> consumers,
                          final List<Map<String, Object>> distributors,
                          final List<Map<String, Object>> producers) {
        try {

            Map<String, Object> finalObj = new LinkedHashMap<>();
            finalObj.put(Constants.getString(Constants.CONSUMERS), consumers);
            finalObj.put(Constants.getString(Constants.DISTRIBUTORS), distributors);
            finalObj.put(Constants.getString(Constants.ENERGY_PRODUCERS), producers);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, finalObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
