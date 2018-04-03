// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HomeAwayEnum.java

package fifa.utilities;

import java.util.*;

public final class HomeAwayEnum extends Enum
{

    private HomeAwayEnum(String s, int i, String value)
    {
        super(s, i);
        this.value = value;
    }

    public static HomeAwayEnum findByValue(String value)
    {
        HomeAwayEnum type = (HomeAwayEnum)valueMap.get(value);
        if(type != null)
            return type;
        else
            return Home;
    }

    public String getValue()
    {
        return value;
    }

    public static HomeAwayEnum[] values()
    {
        HomeAwayEnum ahomeawayenum[];
        int i;
        HomeAwayEnum ahomeawayenum1[];
        System.arraycopy(ahomeawayenum = ENUM$VALUES, 0, ahomeawayenum1 = new HomeAwayEnum[i = ahomeawayenum.length], 0, i);
        return ahomeawayenum1;
    }

    public static HomeAwayEnum valueOf(String s)
    {
        return (HomeAwayEnum)Enum.valueOf(fifa/utilities/HomeAwayEnum, s);
    }

    public static final HomeAwayEnum Home;
    public static final HomeAwayEnum Away;
    private static final Map valueMap;
    private String value;
    private static final HomeAwayEnum ENUM$VALUES[];

    static 
    {
        Home = new HomeAwayEnum("Home", 0, "H");
        Away = new HomeAwayEnum("Away", 1, "A");
        ENUM$VALUES = (new HomeAwayEnum[] {
            Home, Away
        });
        valueMap = new HashMap();
        HomeAwayEnum instance;
        for(Iterator iterator = EnumSet.allOf(fifa/utilities/HomeAwayEnum).iterator(); iterator.hasNext(); valueMap.put(instance.getValue(), instance))
            instance = (HomeAwayEnum)iterator.next();

    }
}
