package entityatt;

import entities.Distributor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public final class Mapper {
    /**
     *
     * @param distributors list
     * @return the map of the distributors with the price of their current contract
     */
    public Map<Distributor, Long> getMap(final List<Distributor> distributors) {
        Map<Distributor, Long> distributorMonthlyPrice = new LinkedHashMap<>();
        for (Distributor distributor : distributors) {
            Long monthlyPrice = distributor.getPriceOfContract();
            distributorMonthlyPrice.put(distributor, monthlyPrice);
        }
        return distributorMonthlyPrice;
    }
}
