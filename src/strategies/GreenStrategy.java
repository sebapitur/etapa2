package strategies;

import entities.Producer;

import java.util.List;

public final class GreenStrategy implements Strategy {
    @Override
    public List<Producer> getSortedList(List<Producer> list) {
        list.sort((o1, o2) -> {
            if (!o1.getEnergyType().isRenewable() && o2.getEnergyType().isRenewable()) {
                return 1;
            }
            if (o1.getEnergyType().isRenewable() && !o2.getEnergyType().isRenewable()) {
                return -1;
            }
            if (Double.compare(o1.getPriceKW(), o2.getPriceKW()) != 0) {
                return Double.compare(o1.getPriceKW(), o2.getPriceKW());
            }
            if (o1.getEnergyPerDistributor() != o2.getEnergyPerDistributor()) {
                return Long.compare(o2.getEnergyPerDistributor(), o1.getEnergyPerDistributor());
            }
            return Long.compare(o1.getId(), o2.getId());
        });
        return list;
    }
}
