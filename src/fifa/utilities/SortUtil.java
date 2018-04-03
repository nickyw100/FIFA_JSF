// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SortUtil.java

package fifa.utilities;


public class SortUtil
{

    public SortUtil()
    {
    }

    public static int compareNullMin(Comparable o1, Comparable o2)
    {
        if(o1 == null && o2 == null)
            return 0;
        if(o1 == null && o2 != null)
            return -1;
        if(o2 == null && o1 != null)
            return 1;
        if(o1 != null)
            return o1.compareTo(o2);
        else
            return 0;
    }

    public static int compareNullMax(Comparable o1, Comparable o2)
    {
        if(o1 == null && o2 == null)
            return 0;
        if(o1 == null && o2 != null)
            return 1;
        if(o2 == null && o1 != null)
            return -1;
        if(o1 != null)
            return o1.compareTo(o2);
        else
            return 0;
    }

    public static int compareNullZero(Comparable o1, Comparable o2)
    {
        if(o1 == null || o2 == null)
            return 0;
        else
            return o1.compareTo(o2);
    }

    public static int compareCaseInsensitive(Comparable o1, Comparable o2)
    {
        if((o1 instanceof String) && (o2 instanceof String))
        {
            String s1 = (String)o1;
            String s2 = (String)o2;
            return s1.compareToIgnoreCase(s2);
        } else
        {
            return compareNullMin(o1, o2);
        }
    }
}
