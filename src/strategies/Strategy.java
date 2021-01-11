package strategies;

import entities.Producer;

import java.util.List;

public interface Strategy {
    /**
     *
     * @param list producers to be sorted
     * @return the sorted list
     */
    List<Producer> getSortedList(List<Producer> list);
}
