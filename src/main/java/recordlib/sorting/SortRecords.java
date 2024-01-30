package recordlib.sorting;

import recordlib.Record;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortRecords {

    public static void sort(List<Record> items, String propertyName) {
        Collections.sort(items, new ItemsComparator(propertyName));
    }

    public static class ItemsComparator implements Comparator<Record> {

        private String propertyName;

        public ItemsComparator(String propertyName) {
            this.propertyName = propertyName;
        }

        public int compare(Record item1, Record item2) {
            Object value1 = item1.getValue(propertyName);
            Object value2 = item2.getValue(propertyName);

            if (value1 == null && value2 == null) return 0;
            if (value1 == null) return -1;
            if (value2 == null) return 1;

            if (value1 instanceof Comparable && value2 instanceof Comparable) {
                return ((Comparable)value1).compareTo(value2);
            }

            return 0;
        }
    }

}
