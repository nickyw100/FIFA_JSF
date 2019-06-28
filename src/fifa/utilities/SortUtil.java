package fifa.utilities;


public class SortUtil
{
    public static int compareNullMin(Comparable o1, Comparable o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }

        if (o1 == null && o2 != null) {
            return -1;
        }

        if (o2 == null && o1 != null) {
            return 1;
        }
        if (o1 != null) {
            return o1.compareTo(o2);
        }
        return 0;
    }





    public static int compareNullMax(Comparable o1, Comparable o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }

        if (o1 == null && o2 != null) {
            return 1;
        }

        if (o2 == null && o1 != null) {
            return -1;
        }
        if (o1 != null) {
            return o1.compareTo(o2);
        }
        return 0;
    }


    public static int compareNullZero(Comparable o1, Comparable o2) {
        if (o1 == null || o2 == null) {
            return 0;
        }

        return o1.compareTo(o2);
    }


    public static int compareCaseInsensitive(Comparable o1, Comparable o2) {
        if (o1 instanceof String && o2 instanceof String) {
            String s1 = (String)o1;
            String s2 = (String)o2;
            return s1.compareToIgnoreCase(s2);
        }
        return compareNullMin(o1, o2);
    }
}
