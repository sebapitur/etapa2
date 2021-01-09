package entityatt;

import entities.Distributor;
import entities.Entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Mapper {

    public Map<Distributor, Long> getMap(final List<Distributor> distributors) {
        Map<Distributor, Long> distributorMonthlyPrice = new LinkedHashMap<>();
        for (Distributor distributor : distributors) {
            Long monthlyPrice = distributor.getPriceOfContract();
            distributorMonthlyPrice.put(distributor, monthlyPrice);
        }
        return distributorMonthlyPrice;
    }
}
