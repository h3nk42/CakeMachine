package model.automat.verkaufsobjekte.kuchen;

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
}
