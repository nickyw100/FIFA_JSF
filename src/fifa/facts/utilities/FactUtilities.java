// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FactUtilities.java

package fifa.facts.utilities;

import com.mysql.jdbc.StringUtils;
import fifa.facts.FactBean;
import fifa.facts.dao.FactDao;
import fifa.jsf.StatsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.PropertiesUtilities;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

// Referenced classes of package fifa.facts.utilities:
//            BuildFactsException

public class FactUtilities
    implements FIFAConstants
{

    protected FactUtilities()
    {
        versionIds = getListOfVersions();
    }

    public static FactUtilities getInstance()
    {
        if(instance == null)
            instance = new FactUtilities();
        return instance;
    }

    public String getLastFactBuildDate()
    {
        FactDao factDao = new FactDao();
        String lastBuildDate = factDao.getLastFactBuildDate();
        return lastBuildDate;
    }

    public void buildAllFacts(boolean interactive)
    {
        setInteractive(interactive);
        buildGamesSinceLastWinFact();
        buildGamesSinceLastLossFact();
        buildMostGoalsConceededAtHomeFact();
        buildMostGoalsConceededAwayFact();
        buildMostGoalsScoredAtHomeFact();
        buildMostGoalsScoredAwayFact();
        buildTeamMostPlayed();
        buildTeamLeastPlayed();
        buildHighestDivisionReached();
        if(interactive)
        {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, "Build Facts completed successfully", " "));
        }
    }

    private void buildHighestDivisionReached()
    {
        Boolean active = Boolean.valueOf(true);
        FactBean factBean;
        for(Iterator iterator = versionIds.iterator(); iterator.hasNext(); addUpdateFact(factBean))
        {
            String versionId = (String)iterator.next();
            FactDao factDao = new FactDao();
            int division = 0;
            try
            {
                division = factDao.getHighestDivisionReached(versionId);
                if(division == 0)
                    active = Boolean.valueOf(false);
            }
            catch(BuildFactsException e)
            {
                if(isInteractive())
                {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
                }
                active = Boolean.valueOf(false);
            }
            factBean = new FactBean();
            factBean.setFactId("HDR");
            factBean.setVersionId(versionId);
            factBean.setFactDescription("Highest division reached in season play:");
            factBean.setFactResult((new StringBuilder("Division: ")).append(Integer.toString(division)).toString());
            factBean.setGoodFact(true);
            factBean.setActive(active);
        }

    }

    private void buildTeamMostPlayed()
    {
        FactBean factBean;
        for(Iterator iterator = versionIds.iterator(); iterator.hasNext(); addUpdateFact(factBean))
        {
            String versionId = (String)iterator.next();
            Boolean active = Boolean.valueOf(true);
            FactDao factDao = new FactDao();
            StatsBean statsBean = new StatsBean();
            try
            {
                statsBean = factDao.getTeamMostPlayed(versionId);
            }
            catch(BuildFactsException e)
            {
                active = Boolean.valueOf(false);
                if(isInteractive())
                {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
                }
            }
            if(statsBean == null)
                active = Boolean.valueOf(false);
            factBean = new FactBean();
            factBean.setFactId("TMP");
            factBean.setVersionId(versionId);
            factBean.setFactDescription("Team most played:");
            if(statsBean != null)
                factBean.setFactResult((new StringBuilder(String.valueOf(statsBean.getTeamName()))).append(" (").append(statsBean.getGoalsFor()).append(" times!)").toString());
            factBean.setGoodFact(true);
            factBean.setActive(active);
        }

    }

    private void buildTeamLeastPlayed()
    {
        for(Iterator iterator = versionIds.iterator(); iterator.hasNext();)
        {
            String versionId = (String)iterator.next();
            Boolean active = Boolean.valueOf(true);
            FactDao factDao = new FactDao();
            StatsBean statsBean = new StatsBean();
            try
            {
                statsBean = factDao.getTeamLeastPlayed(versionId);
            }
            catch(BuildFactsException e)
            {
                active = Boolean.valueOf(false);
                if(isInteractive())
                {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
                }
            }
            if(statsBean == null)
            {
                active = Boolean.valueOf(false);
            } else
            {
                FactBean factBean = new FactBean();
                factBean.setFactId("TLP");
                factBean.setVersionId(versionId);
                factBean.setFactDescription("Team least played:");
                if(statsBean.getGoalsFor() > 1)
                    factBean.setFactResult((new StringBuilder(String.valueOf(statsBean.getTeamName()))).append(" (").append(statsBean.getGoalsFor()).append(" times!)").toString());
                else
                    factBean.setFactResult((new StringBuilder(String.valueOf(statsBean.getTeamName()))).append(" (").append(statsBean.getGoalsFor()).append(" time!)").toString());
                factBean.setGoodFact(false);
                factBean.setActive(active);
                addUpdateFact(factBean);
            }
        }

    }

    private void buildGamesSinceLastWinFact()
    {
        String messageResources = PropertiesUtilities.getInstance().getMessageResource();
        String versionId = PropertiesUtilities.getInstance().getProperty(messageResources, "defaultVersion");
        if(versionId.equals("ALL"))
            versionId = PropertiesUtilities.getInstance().getProperty(messageResources, "latestVersion");
        FactDao factDao = new FactDao();
        int numberOfGames = 0;
        Boolean active = Boolean.valueOf(true);
        try
        {
            numberOfGames = factDao.getGamesSinceWin(versionId);
        }
        catch(BuildFactsException e)
        {
            active = Boolean.valueOf(false);
            if(isInteractive())
            {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
            }
        }
        FactBean factBean = new FactBean();
        factBean.setFactId("GSLW");
        factBean.setVersionId(versionId);
        factBean.setFactDescription("Number of games since last win:");
        factBean.setFactResult((new StringBuilder(String.valueOf(Integer.toString(numberOfGames)))).append(" games!").toString());
        if(numberOfGames > 0)
            factBean.setGoodFact(false);
        else
            factBean.setGoodFact(true);
        factBean.setActive(active);
        addUpdateFact(factBean);
    }

    private void buildGamesSinceLastLossFact()
    {
        String messageResources = PropertiesUtilities.getInstance().getMessageResource();
        String versionId = PropertiesUtilities.getInstance().getProperty(messageResources, "defaultVersion");
        if(versionId.equals("ALL"))
            versionId = PropertiesUtilities.getInstance().getProperty(messageResources, "latestVersion");
        FactDao factDao = new FactDao();
        int numberOfGames = 0;
        Boolean active = Boolean.valueOf(true);
        try
        {
            numberOfGames = factDao.getGamesSinceLoss(versionId);
        }
        catch(BuildFactsException e)
        {
            active = Boolean.valueOf(false);
            if(isInteractive())
            {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
            }
        }
        FactBean factBean = new FactBean();
        factBean.setFactId("GSLL");
        factBean.setVersionId(versionId);
        factBean.setFactDescription("Number of games since last loss:");
        factBean.setFactResult((new StringBuilder(String.valueOf(Integer.toString(numberOfGames)))).append(" games!").toString());
        if(numberOfGames > 0)
            factBean.setGoodFact(true);
        else
            factBean.setGoodFact(false);
        factBean.setActive(active);
        addUpdateFact(factBean);
    }

    private void buildMostGoalsConceededAtHomeFact()
    {
        FactBean factBean;
        for(Iterator iterator = versionIds.iterator(); iterator.hasNext(); addUpdateFact(factBean))
        {
            String versionId = (String)iterator.next();
            Boolean active = Boolean.valueOf(true);
            FactDao factDao = new FactDao();
            StatsBean statsBean = new StatsBean();
            try
            {
                statsBean = factDao.getMostGoalsConceeded(versionId, "H");
            }
            catch(BuildFactsException e)
            {
                active = Boolean.valueOf(false);
                if(isInteractive())
                {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
                }
            }
            if(statsBean == null || statsBean.getTeamName() == null)
                active = Boolean.valueOf(false);
            factBean = new FactBean();
            factBean.setFactId("MGCH");
            factBean.setVersionId(versionId);
            factBean.setFactDescription("Most goals conceeded at home:");
            if(active.booleanValue())
            {
                String formattedDate = String.format("%tm/%td/%tY", new Object[] {
                    statsBean.getGameDateTime(), statsBean.getGameDateTime(), statsBean.getGameDateTime()
                });
                factBean.setFactResult((new StringBuilder(String.valueOf(Integer.toString(statsBean.getGoalsAgainst())))).append(" goals! ").append("(").append(statsBean.getTeamName()).append(" on ").append(formattedDate).append(")").toString());
            } else
            {
                factBean.setFactResult("NO DATA FOUND");
            }
            factBean.setGoodFact(false);
            factBean.setActive(active);
        }

    }

    private void buildMostGoalsConceededAwayFact()
    {
        FactBean factBean;
        for(Iterator iterator = versionIds.iterator(); iterator.hasNext(); addUpdateFact(factBean))
        {
            String versionId = (String)iterator.next();
            Boolean active = Boolean.valueOf(true);
            FactDao factDao = new FactDao();
            StatsBean statsBean = new StatsBean();
            try
            {
                statsBean = factDao.getMostGoalsConceeded(versionId, "A");
            }
            catch(BuildFactsException e)
            {
                active = Boolean.valueOf(false);
                if(isInteractive())
                {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
                }
            }
            if(statsBean == null || statsBean.getTeamName() == null)
                active = Boolean.valueOf(false);
            factBean = new FactBean();
            factBean.setFactId("MGCA");
            factBean.setVersionId(versionId);
            factBean.setFactDescription("Most goals conceeded away:");
            if(active.booleanValue())
            {
                String formattedDate = String.format("%tm/%td/%tY", new Object[] {
                    statsBean.getGameDateTime(), statsBean.getGameDateTime(), statsBean.getGameDateTime()
                });
                factBean.setFactResult((new StringBuilder(String.valueOf(Integer.toString(statsBean.getGoalsAgainst())))).append(" goals! ").append("(").append(statsBean.getTeamName()).append(" on ").append(formattedDate).append(")").toString());
            } else
            {
                factBean.setFactResult("NO DATA FOUND");
            }
            factBean.setGoodFact(false);
            factBean.setActive(active);
        }

    }

    private void buildMostGoalsScoredAtHomeFact()
    {
        FactBean factBean;
        for(Iterator iterator = versionIds.iterator(); iterator.hasNext(); addUpdateFact(factBean))
        {
            String versionId = (String)iterator.next();
            Boolean active = Boolean.valueOf(true);
            FactDao factDao = new FactDao();
            StatsBean statsBean = new StatsBean();
            try
            {
                statsBean = factDao.getMostGoalsScored(versionId, "H");
            }
            catch(BuildFactsException e)
            {
                active = Boolean.valueOf(false);
                if(isInteractive())
                {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
                }
            }
            if(statsBean == null || statsBean.getTeamName() == null)
                active = Boolean.valueOf(false);
            factBean = new FactBean();
            factBean.setFactId("MGSH");
            factBean.setVersionId(versionId);
            factBean.setFactDescription("Most goals scored at home:");
            if(active.booleanValue())
            {
                String formattedDate = String.format("%tm/%td/%tY", new Object[] {
                    statsBean.getGameDateTime(), statsBean.getGameDateTime(), statsBean.getGameDateTime()
                });
                factBean.setFactResult((new StringBuilder(String.valueOf(Integer.toString(statsBean.getGoalsFor())))).append(" goals! ").append("(").append(statsBean.getTeamName()).append(" on ").append(formattedDate).append(")").toString());
            } else
            {
                factBean.setFactResult("NO DATA FOUND");
            }
            factBean.setGoodFact(true);
            factBean.setActive(active);
        }

    }

    private void buildMostGoalsScoredAwayFact()
    {
        FactBean factBean;
        for(Iterator iterator = versionIds.iterator(); iterator.hasNext(); addUpdateFact(factBean))
        {
            String versionId = (String)iterator.next();
            Boolean active = Boolean.valueOf(true);
            FactDao factDao = new FactDao();
            StatsBean statsBean = new StatsBean();
            try
            {
                statsBean = factDao.getMostGoalsScored(versionId, "A");
            }
            catch(BuildFactsException e)
            {
                active = Boolean.valueOf(false);
                if(isInteractive())
                {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
                }
            }
            if(statsBean == null || statsBean.getTeamName() == null)
                active = Boolean.valueOf(false);
            factBean = new FactBean();
            factBean.setFactId("MGSA");
            factBean.setVersionId(versionId);
            factBean.setFactDescription("Most goals scored away:");
            if(active.booleanValue())
            {
                String formattedDate = String.format("%tm/%td/%tY", new Object[] {
                    statsBean.getGameDateTime(), statsBean.getGameDateTime(), statsBean.getGameDateTime()
                });
                factBean.setFactResult((new StringBuilder(String.valueOf(Integer.toString(statsBean.getGoalsFor())))).append(" goals! ").append("(").append(statsBean.getTeamName()).append(" on ").append(formattedDate).append(")").toString());
            } else
            {
                factBean.setFactResult("NO DATA FOUND");
            }
            factBean.setGoodFact(true);
            factBean.setActive(active);
        }

    }

    private void addUpdateFact(FactBean factBean)
    {
        FactDao factDao = new FactDao();
        String factDescription = factDao.getFactDescription(factBean.getVersionId(), factBean.getFactId());
        if(!StringUtils.isEmptyOrWhitespaceOnly(factDescription))
            factDao.updateFact(factBean);
        else
            factDao.addFact(factBean);
    }

    private List getListOfVersions()
    {
        List versionIds = new ArrayList(Arrays.asList(new String[] {
            "FIFA12", "FIFA13", "FIFA14", "FIFA15"
        }));
        return versionIds;
    }

    public List getVersionIds()
    {
        return versionIds;
    }

    public void setVersionIds(List versionIds)
    {
        this.versionIds = versionIds;
    }

    public boolean isInteractive()
    {
        return interactive;
    }

    public void setInteractive(boolean interactive)
    {
        this.interactive = interactive;
    }

    private static FactUtilities instance = null;
    private final String goalsString = " goals! ";
    private List versionIds;
    private boolean interactive;

}
