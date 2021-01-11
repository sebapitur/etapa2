package strategies;

import entities.Producer;

import java.util.List;

public final class QuantityStrategy implements Strategy {
    @Override
    public List<Producer> getSortedList(List<Producer> list) {
        list.sort((o1, o2) -> {
            if (o1.getEnergyPerDistributor() == o2.getEnergyPerDistributor()) {
                return Long.compare(o1.getId(), o2.getId());
            } else {
                return Long.compare(o2.getEnergyPerDistributor(), o1.getEnergyPerDistributor());
            }
        });
        return list;
    }
}
