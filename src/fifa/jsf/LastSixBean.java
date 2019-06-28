// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LastSixBean.java

package fifa.jsf;

import fifa.dao.StatsDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// Referenced classes of package fifa.jsf:
//            AbstractResultBean

public class LastSixBean extends AbstractResultBean
{

    public LastSixBean()
    {
    }

    public void setGameDateTime(Date gameDateTime)
    {
        setGameDateTimeStr(formatDate(gameDateTime));
    }

    public String getWinLossDraw()
    {
        return winLossDraw;
    }

    public void setWinLossDraw(String winLossDraw)
    {
        this.winLossDraw = winLossDraw;
    }

    public List getResults()
    {
        StatsDao statsDao = new StatsDao();
        results = statsDao.getLastSix("C");
        return results;
    }

    public void setResults(List results)
    {
        this.results = results;
    }

    public String getGameDateTimeStr()
    {
        return gameDateTimeStr;
    }

    public void setGameDateTimeStr(String gameDateTimeStr)
    {
        this.gameDateTimeStr = gameDateTimeStr;
    }

    private String formatDate(Date inputDate)
    {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        return df.format(inputDate);
    }

    public String getWinComment()
    {
        if(getPenaltiesAgainst() == 0 && getPenaltiesFor() == 0)
            return (new StringBuilder("Beat ")).append(getTeamName()).append(" ").append(getGoalsFor()).append("-").append(getGoalsAgainst()).append(" played by ").append(getPlayerName()).append(" on ").append(getGameDateTimeStr()).toString();
        else
            return (new StringBuilder("Beat ")).append(getTeamName()).append(" ").append(getGoalsFor()).append("-").append(getGoalsAgainst()).append(" (").append(getPenaltiesFor()).append(" - ").append(getPenaltiesAgainst()).append(" P) played by ").append(getPlayerName()).append(" on ").append(getGameDateTimeStr()).toString();
    }

    public String getLossComment()
    {
        if(getPenaltiesAgainst() == 0 && getPenaltiesFor() == 0)
            return (new StringBuilder("Lost to ")).append(getTeamName()).append(" ").append(getGoalsFor()).append("-").append(getGoalsAgainst()).append(" played by ").append(getPlayerName()).append(" on ").append(getGameDateTimeStr()).toString();
        else
            return (new StringBuilder("Lost to ")).append(getTeamName()).append(" ").append(getGoalsFor()).append("-").append(getGoalsAgainst()).append(" (").append(getPenaltiesFor()).append(" - ").append(getPenaltiesAgainst()).append(" P) played by ").append(getPlayerName()).append(" on ").append(getGameDateTimeStr()).toString();
    }

    public String getDrawComment()
    {
        return (new StringBuilder("Drew with ")).append(getTeamName()).append(" ").append(getGoalsFor()).append("-").append(getGoalsAgainst()).append(" played by ").append(getPlayerName()).append(" on ").append(getGameDateTimeStr()).toString();
    }

    private static final long serialVersionUID = 0x55daa70bL;
    private List results;
    private String winLossDraw;
    private String gameDateTimeStr;
}
