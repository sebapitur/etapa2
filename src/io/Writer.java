package io;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The class writes the output in files
 * <p>
 * DO NOT MODIFY
 */
public final class Writer {
    /**
     * The file where the data will be written
     */
    public static final String BUDGET = "budget";
    public static final String ID = "id";
    public static final String IS_BANKRUPT = "isBankrupt";
    public static final String CONTRACTS = "contracts";
    public static final String CONSUMER_ID = "consumerId";
    public static final String PRICE = "price";
    public static final String REMAINED_CONTRACT_MONTHS = "remainedContractMonths";
    public static final String CONSUMERS = "consumers";
    public static final String DISTRIBUTORS = "distributors";
    private final FileWriter file;

    public Writer(final String path) throws IOException {
        this.file = new FileWriter(path);
    }

    /**
     * @param id of consumer
     * @param isBankrupt true if consumer is not in game
     * @param budget of consumer
     * @return map with consumer's attributes
     */
    public Map<String, Object> writeFileConsumer(final long id,
                                                  final boolean isBankrupt,
                                                  final long budget) {
        Map<String, Object> object = new LinkedHashMap<>();
        object.put(ID, id);
        object.put(IS_BANKRUPT, isBankrupt);
        object.put(BUDGET, budget);
        return object;
    }

    /**
     *
     * @param id id of the distributor
     * @param budget budget of the distributor
     * @param isBankrupt true if the distributor is bankrupt an false otherwise
     * @param contracts contract list of the distributor
     * @return distributor Map<field, value>
     */
    public Map<String, Object> writeFileDistributor(final long id,
                                                    final long budget,
                                                    final boolean isBankrupt,
                                                    final List<Map<String, Object>> contracts) {
        Map<String, Object> object = new LinkedHashMap<>();
        object.put(ID, id);
        object.put(BUDGET, budget);
        object.put(IS_BANKRUPT, isBankrupt);
        object.put(CONTRACTS, contracts);
        return object;
    }

    /**
     * @param consumerId in contract
     * @param price of contract
     * @param remainedContractMonths contracted
     * @return map with contract attributes
     */
    public Map<String, Object> writeFileConsumerContract(final long consumerId, final long price,
                                         final long remainedContractMonths) {
        Map<String, Object> object = new LinkedHashMap<>();
        object.put(CONSUMER_ID, consumerId);
        object.put(PRICE, price);
        object.put(REMAINED_CONTRACT_MONTHS, remainedContractMonths);
        return object;
    }

    /**
     *
     * @param consumers list
     * @param distributors list
     */
    public void closeJSON(final List<Map<String, Object>> consumers,
                          final List<Map<String, Object>> distributors) {
        try {

            Map<String, Object> finalObj = new LinkedHashMap<>();
            finalObj.put(CONSUMERS, consumers);
            finalObj.put(DISTRIBUTORS, distributors);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, finalObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
