package strategies;

import entities.Producer;

import java.util.List;

public class PriceStrategy implements Strategy{
    @Override
    public List<Producer> getSortedList(List<Producer> list) {
        list.sort((o1, o2) -> {
            if(Double.compare(o1.getPriceKW(), o2.getPriceKW()) != 0)
                return Double.compare(o1.getPriceKW(), o2.getPriceKW());
            return Long.compare(o2.getEnergyPerDistributor(), o1.getEnergyPerDistributor());
        });

        return list;
    }
}
