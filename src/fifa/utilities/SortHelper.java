// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SortHelper.java

package fifa.utilities;

import java.io.Serializable;

// Referenced classes of package fifa.utilities:
//            SortUtil

public class SortHelper
    implements Serializable
{

    public SortHelper()
    {
    }

    public static int sortNullMin(Object o1, Object o2)
    {
        return SortUtil.compareNullMin((Comparable)o1, (Comparable)o2);
    }

    public static int sortNullMax(Object o1, Object o2)
    {
        return SortUtil.compareNullMax((Comparable)o1, (Comparable)o2);
    }

    public static int sortNullZero(Object o1, Object o2)
    {
        return SortUtil.compareNullZero((Comparable)o1, (Comparable)o2);
    }

    public static int sortCaseInsensitive(Object o1, Object o2)
    {
        return SortUtil.compareCaseInsensitive((Comparable)o1, (Comparable)o2);
    }

    private static final long serialVersionUID = 1L;
}
