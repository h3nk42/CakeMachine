package control.lib;

import model.verkaufsobjekte.kuchen.VerkaufsKuchen;

import java.util.Comparator;

public class KuchenComparators {

    public static class FachnummerComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            VerkaufsKuchen k1 = (VerkaufsKuchen) o1;
            VerkaufsKuchen k2 = (VerkaufsKuchen) o2;
            return k1.getFachnummer() - k2.getFachnummer();
        }
    }

    public static class HerstellerComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            VerkaufsKuchen k1 = (VerkaufsKuchen) o1;
            VerkaufsKuchen k2 = (VerkaufsKuchen) o2;
            return k1.getHersteller().getName().compareTo(k2.getHersteller().getName());
        }
    }

    public static class InspektionsDatumComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            VerkaufsKuchen k1 = (VerkaufsKuchen) o1;
            VerkaufsKuchen k2 = (VerkaufsKuchen) o2;
            if (k1.getInspektionsdatum().after(k2.getInspektionsdatum())) {
                return -1;
            } else if (k1.getInspektionsdatum().before(k2.getInspektionsdatum())) {
                return 1;
            }
            return 0;
        }
    }

    public static class HaltbarkeitComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            VerkaufsKuchen k1 = (VerkaufsKuchen) o1;
            VerkaufsKuchen k2 = (VerkaufsKuchen) o2;
            return k1.getHaltbarkeit().compareTo(k2.getHaltbarkeit());
        }
    }
}
