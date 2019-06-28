package fifa.utilities;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;


@ManagedBean
@ApplicationScoped
public class SortHelper
        implements Serializable {
    private static final long serialVersionUID = 1L;

    public static int sortNullMin(Object o1, Object o2) {
        return SortUtil.compareNullMin((Comparable) o1, (Comparable) o2);
    }


    public static int sortNullMax(Object o1, Object o2) {
        return SortUtil.compareNullMax((Comparable) o1, (Comparable) o2);
    }


    public static int sortNullZero(Object o1, Object o2) {
        return SortUtil.compareNullZero((Comparable) o1, (Comparable) o2);
    }


    public static int sortCaseInsensitive(Object o1, Object o2) {
        return SortUtil.compareCaseInsensitive((Comparable) o1, (Comparable) o2);
    }
}
