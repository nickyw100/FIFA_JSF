// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StatsBean.java

package fifa.jsf;

import fifa.dao.*;
import fifa.utilities.PropertiesUtilities;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang3.ArrayUtils;

// Referenced classes of package fifa.jsf:
//            AbstractResultBean, LastResultBean

public class StatsBean extends AbstractResultBean
{
    public static final class GameTypeEnum extends Enum
    {

        public static GameTypeEnum findByValue(String value)
        {
            GameTypeEnum type = (GameTypeEnum)valueMap.get(value);
            if(type != null)
                return type;
            else
                return All;
        }

        public String getValue()
        {
            return value;
        }

        public void setValue(String value)
        {
            this.value = value;
        }

        public boolean isSelectable()
        {
            return selectable;
        }

        public void setSelectable(boolean selectable)
        {
            this.selectable = selectable;
        }

        public static GameTypeEnum[] values()
        {
            GameTypeEnum agametypeenum[];
            int i;
            GameTypeEnum agametypeenum1[];
            System.arraycopy(agametypeenum = ENUM$VALUES, 0, agametypeenum1 = new GameTypeEnum[i = agametypeenum.length], 0, i);
            return agametypeenum1;
        }

        public static GameTypeEnum valueOf(String s)
        {
            return (GameTypeEnum)Enum.valueOf(fifa/jsf/StatsBean$GameTypeEnum, s);
        }

        public static final GameTypeEnum All;
        public static final GameTypeEnum Friendly;
        public static final GameTypeEnum Season;
        public static final GameTypeEnum Cup;
        private static final Map valueMap;
        private String value;
        private boolean selectable;
        private static final GameTypeEnum ENUM$VALUES[];

        static 
        {
            All = new GameTypeEnum("All", 0, "A", false);
            Friendly = new GameTypeEnum("Friendly", 1, "F", true);
            Season = new GameTypeEnum("Season", 2, "S", true);
            Cup = new GameTypeEnum("Cup", 3, "C", true);
            ENUM$VALUES = (new GameTypeEnum[] {
                All, Friendly, Season, Cup
            });
            valueMap = new HashMap();
            GameTypeEnum instance;
            for(Iterator iterator = EnumSet.allOf(fifa/jsf/StatsBean$GameTypeEnum).iterator(); iterator.hasNext(); valueMap.put(instance.getValue(), instance))
                instance = (GameTypeEnum)iterator.next();

        }

        private GameTypeEnum(String s, int i, String value, boolean selectable)
        {
            super(s, i);
            this.value = value;
            this.selectable = selectable;
        }
    }

    public static final class HomeAwayEnum extends Enum
    {

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

        public void setValue(String value)
        {
            this.value = value;
        }

        public boolean isSelectable()
        {
            return selectable;
        }

        public void setSelectable(boolean selectable)
        {
            this.selectable = selectable;
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
            return (HomeAwayEnum)Enum.valueOf(fifa/jsf/StatsBean$HomeAwayEnum, s);
        }

        public static final HomeAwayEnum Home;
        public static final HomeAwayEnum Away;
        public static final HomeAwayEnum Both;
        private static final Map valueMap;
        private String value;
        private boolean selectable;
        private static final HomeAwayEnum ENUM$VALUES[];

        static 
        {
            Home = new HomeAwayEnum("Home", 0, "H", true);
            Away = new HomeAwayEnum("Away", 1, "A", true);
            Both = new HomeAwayEnum("Both", 2, "B", false);
            ENUM$VALUES = (new HomeAwayEnum[] {
                Home, Away, Both
            });
            valueMap = new HashMap();
            HomeAwayEnum instance;
            for(Iterator iterator = EnumSet.allOf(fifa/jsf/StatsBean$HomeAwayEnum).iterator(); iterator.hasNext(); valueMap.put(instance.getValue(), instance))
                instance = (HomeAwayEnum)iterator.next();

        }

        private HomeAwayEnum(String s, int i, String value, boolean selectable)
        {
            super(s, i);
            this.value = value;
            this.selectable = selectable;
        }
    }

    public static final class SearchCriteriaEnum extends Enum
        implements Serializable
    {

        public String getLabel()
        {
            return label;
        }

        public static SearchCriteriaEnum[] values()
        {
            SearchCriteriaEnum asearchcriteriaenum[];
            int i;
            SearchCriteriaEnum asearchcriteriaenum1[];
            System.arraycopy(asearchcriteriaenum = ENUM$VALUES, 0, asearchcriteriaenum1 = new SearchCriteriaEnum[i = asearchcriteriaenum.length], 0, i);
            return asearchcriteriaenum1;
        }

        public static SearchCriteriaEnum valueOf(String s)
        {
            return (SearchCriteriaEnum)Enum.valueOf(fifa/jsf/StatsBean$SearchCriteriaEnum, s);
        }

        public static final SearchCriteriaEnum Greater;
        public static final SearchCriteriaEnum LessThan;
        public static final SearchCriteriaEnum Equals;
        private final String label;
        private static final SearchCriteriaEnum ENUM$VALUES[];

        static 
        {
            Greater = new SearchCriteriaEnum("Greater", 0, ">");
            LessThan = new SearchCriteriaEnum("LessThan", 1, "<");
            Equals = new SearchCriteriaEnum("Equals", 2, "=");
            ENUM$VALUES = (new SearchCriteriaEnum[] {
                Greater, LessThan, Equals
            });
        }

        private SearchCriteriaEnum(String s, int i, String label)
        {
            super(s, i);
            this.label = label;
        }
    }


    public StatsBean()
    {
        possessionPercentage = 50;
        gameDateTime = Calendar.getInstance().getTime();
        StatsDao statsDao = new StatsDao();
        List lastResultList = statsDao.getLastResult();
        if(lastResultList != null)
        {
            LastResultBean lastResult = (LastResultBean)lastResultList.get(0);
            gameType = lastResult.getGameType();
            division = lastResult.getDivision();
            opponentDivision = lastResult.getDivision();
        } else
        {
            gameType = GameTypeEnum.Friendly;
            division = 0;
            opponentDivision = 0;
        }
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
    }

    public HomeAwayEnum[] getHomeAwayEnums()
    {
        HomeAwayEnum enums[] = HomeAwayEnum.values();
        for(int x = 0; x < enums.length; x++)
            if(!enums[x].isSelectable())
                enums = (HomeAwayEnum[])ArrayUtils.removeElement(enums, enums[x]);

        return enums;
    }

    public GameTypeEnum[] getGameTypeEnums()
    {
        GameTypeEnum enums[] = GameTypeEnum.values();
        for(int x = 0; x < enums.length; x++)
            if(!enums[x].isSelectable())
                enums = (GameTypeEnum[])ArrayUtils.removeElement(enums, enums[x]);

        return enums;
    }

    public String getTeamId()
    {
        return teamId;
    }

    public void setTeamId(String teamId)
    {
        this.teamId = teamId;
    }

    public String getTeamName()
    {
        if(teamName == null)
        {
            TeamDao teamDao = new TeamDao();
            teamName = teamDao.getTeamName(countryId, teamId);
        }
        return teamName;
    }

    public String getGameDateTimeString()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
        java.util.Date date = getGameDateTime();
        String sDate = sdf.format(date);
        return sDate;
    }

    public int getDivision()
    {
        return division;
    }

    public void setDivision(int division)
    {
        this.division = division;
    }

    public boolean isMatchAbandoned()
    {
        return matchAbandoned;
    }

    public void setMatchAbandoned(boolean matchAbandoned)
    {
        this.matchAbandoned = matchAbandoned;
    }

    public String getCountryId()
    {
        return countryId;
    }

    public void setCountryId(String countryId)
    {
        this.countryId = countryId;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    public String getCountryName()
    {
        if(countryName == null)
        {
            CountryDao countryDao = new CountryDao();
            countryName = countryDao.getCountryName(countryId);
        }
        return countryName;
    }

    public String getLastPlayerNameAdded()
    {
        return lastPlayerNameAdded;
    }

    public void setLastPlayerNameAdded(String lastPlayerNameAdded)
    {
        this.lastPlayerNameAdded = lastPlayerNameAdded;
    }

    public int getOpponentDivision()
    {
        return opponentDivision;
    }

    public void setOpponentDivision(int opponentDivision)
    {
        this.opponentDivision = opponentDivision;
    }

    public HomeAwayEnum getHomeAway()
    {
        return homeAway;
    }

    public void setHomeAway(HomeAwayEnum homeAway)
    {
        this.homeAway = homeAway;
    }

    public GameTypeEnum getGameType()
    {
        return gameType;
    }

    public void setGameType(GameTypeEnum gameType)
    {
        this.gameType = gameType;
    }

    private static final long serialVersionUID = 0x11510d32L;
    private String countryId;
    private String countryName;
    private String teamId;
    private GameTypeEnum gameType;
    private HomeAwayEnum homeAway;
    private int division;
    private int opponentDivision;
    private boolean matchAbandoned;
    private String lastPlayerNameAdded;
}
