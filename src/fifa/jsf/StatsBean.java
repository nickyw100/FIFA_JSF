package fifa.jsf;

import fifa.dao.CountryDao;
import fifa.dao.StatsDao;
import fifa.dao.TeamDao;
import fifa.utilities.PropertiesUtilities;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.lang3.ArrayUtils;

@ManagedBean
@ViewScoped
public class StatsBean
        extends AbstractResultBean
{
    private static final long serialVersionUID = -4163535146058707662L;
    private String countryId;
    private String countryName;
    private String teamId;
    private GameTypeEnum gameType;
    private HomeAwayEnum homeAway;
    private int division;
    private int opponentDivision;
    private boolean matchAbandoned;
    private String lastPlayerNameAdded;

    public StatsBean() {
        StatsDao statsDao = new StatsDao();
        List<LastResultBean> lastResultList = statsDao.getLastResult();
        if (lastResultList != null) {
            LastResultBean lastResult = (LastResultBean)lastResultList.get(0);
            this.gameType = lastResult.getGameType();
            this.division = lastResult.getDivision();
            this.opponentDivision = lastResult.getDivision();
        } else {
            this.gameType = GameTypeEnum.Friendly;
            this.division = 0;
            this.opponentDivision = 0;
        }

        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        this.versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(),
                "defaultVersion");
    }


    public HomeAwayEnum[] getHomeAwayEnums() {
        HomeAwayEnum[] arrayOfHomeAwayEnum = HomeAwayEnum.values();

        for (int x = 0; x < arrayOfHomeAwayEnum.length; x++) {
            if (!arrayOfHomeAwayEnum[x].isSelectable()) {
                arrayOfHomeAwayEnum = (HomeAwayEnum[])ArrayUtils.removeElement(arrayOfHomeAwayEnum, arrayOfHomeAwayEnum[x]);
            }
        }

        return arrayOfHomeAwayEnum;
    }

    public GameTypeEnum[] getGameTypeEnums() {
        GameTypeEnum[] arrayOfGameTypeEnum = GameTypeEnum.values();

        for (int x = 0; x < arrayOfGameTypeEnum.length; x++) {
            if (!arrayOfGameTypeEnum[x].isSelectable()) {
                arrayOfGameTypeEnum = (GameTypeEnum[])ArrayUtils.removeElement(arrayOfGameTypeEnum, arrayOfGameTypeEnum[x]);
            }
        }

        return arrayOfGameTypeEnum;
    }




    public String getTeamId() { return this.teamId; }



    public void setTeamId(String teamId) { this.teamId = teamId; }



    public String getTeamName() {
        if (this.teamName == null) {
            TeamDao teamDao = new TeamDao();
            this.teamName = teamDao.getTeamName(this.countryId, this.teamId);
        }

        return this.teamName;
    }

    public String getGameDateTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
        Date date = getGameDateTime();
        return sdf.format(date);
    }



    public int getDivision() { return this.division; }



    public void setDivision(int division) { this.division = division; }



    public boolean isMatchAbandoned() { return this.matchAbandoned; }



    public void setMatchAbandoned(boolean matchAbandoned) { this.matchAbandoned = matchAbandoned; }



    public String getCountryId() { return this.countryId; }



    public void setCountryId(String countryId) { this.countryId = countryId; }



    public void setCountryName(String countryName) { this.countryName = countryName; }


    public String getCountryName() {
        if (this.countryName == null) {
            CountryDao countryDao = new CountryDao();
            this.countryName = countryDao.getCountryName(this.countryId);
        }
        return this.countryName;
    }


    public String getLastPlayerNameAdded() { return this.lastPlayerNameAdded; }



    public void setLastPlayerNameAdded(String lastPlayerNameAdded) { this.lastPlayerNameAdded = lastPlayerNameAdded; }



    public int getOpponentDivision() { return this.opponentDivision; }



    public void setOpponentDivision(int opponentDivision) { this.opponentDivision = opponentDivision; }



    public HomeAwayEnum getHomeAway() { return this.homeAway; }




    public void setHomeAway(HomeAwayEnum homeAway) { this.homeAway = homeAway; }




    public enum HomeAwayEnum
    {
        Home("H", true), Away("A", true), Both("B", false);
        static  {
            valueMap = new HashMap();


            for (HomeAwayEnum instance : EnumSet.allOf(HomeAwayEnum.class))
                valueMap.put(instance.getValue(), instance);
        }

        private static final Map<String, HomeAwayEnum> valueMap;
        private String value;
        private boolean selectable;

        HomeAwayEnum(String value, boolean selectable) {
            this.value = value;
            this.selectable = selectable;
        }

        public static HomeAwayEnum findByValue(String value) {
            HomeAwayEnum type = (HomeAwayEnum)valueMap.get(value);
            if (type != null) {
                return type;
            }
            return Home;
        }


        public String getValue() { return this.value; }



        public void setValue(String value) { this.value = value; }



        public boolean isSelectable() { return this.selectable; }



        public void setSelectable(boolean selectable) { this.selectable = selectable; }
    }


    public enum SearchCriteriaEnum
            implements Serializable
    {
        Greater(">"), LessThan("<"), Equals("=");

        private final String label;


        SearchCriteriaEnum(String label) { this.label = label; }



        public String getLabel() { return this.label; }
    }



    public enum GameTypeEnum
    {
        All("A", false), Friendly("F", true), Season("S", true), Cup("C", true);
        static  {
            valueMap = new HashMap();


            for (GameTypeEnum instance : EnumSet.allOf(GameTypeEnum.class))
                valueMap.put(instance.getValue(), instance);
        }

        private static final Map<String, GameTypeEnum> valueMap;
        private String value;
        private boolean selectable;

        GameTypeEnum(String value, boolean selectable) {
            this.value = value;
            this.selectable = selectable;
        }

        public static GameTypeEnum findByValue(String value) {
            GameTypeEnum type = (GameTypeEnum)valueMap.get(value);
            if (type != null) {
                return type;
            }
            return All;
        }


        public String getValue() { return this.value; }



        public void setValue(String value) { this.value = value; }



        public boolean isSelectable() { return this.selectable; }



        public void setSelectable(boolean selectable) { this.selectable = selectable; }
    }




    public GameTypeEnum getGameType() { return this.gameType; }



    public void setGameType(GameTypeEnum gameType) { this.gameType = gameType; }
}
