package fifa.facts.utilities;

import org.apache.commons.lang3.StringUtils;
import fifa.facts.FactBean;
import fifa.facts.dao.FactDao;
import fifa.jsf.StatsBean;
import fifa.utilities.FIFAConstants;
import fifa.utilities.PropertiesUtilities;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FactUtilities
        implements FIFAConstants {
    private static FactUtilities instance = null;
    private final String goalsString = " goals! ";
    private List<String> versionIds = getListOfVersions();


    private boolean interactive;


    public static FactUtilities getInstance() {
        if (instance == null) {
            instance = new FactUtilities();
        }
        return instance;
    }

    public String getLastFactBuildDate() {
        FactDao factDao = new FactDao();
        return factDao.getLastFactBuildDate();
    }


    public void buildAllFacts(boolean interactive) {
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
        if (interactive) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("buildFacts",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Build Facts completed successfully", " "));
        }
    }


    private void buildHighestDivisionReached() {
        Boolean active = Boolean.valueOf(true);
        for (String versionId : this.versionIds) {

            FactDao factDao = new FactDao();
            int division = 0;
            try {
                division = factDao.getHighestDivisionReached(versionId);
                if (division == 0) {
                    active = Boolean.valueOf(false);
                }
            } catch (BuildFactsException e) {
                if (isInteractive()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
                }
                active = Boolean.valueOf(false);
            }

            FactBean factBean = new FactBean();
            factBean.setFactId("HDR");
            factBean.setVersionId(versionId);
            factBean.setFactDescription("Highest division reached in season play:");
            factBean.setFactResult("Division: " + division);
            factBean.setGoodFact(true);
            factBean.setActive(active);

            addUpdateFact(factBean);
        }
    }


    private void buildTeamMostPlayed() {
        for (String versionId : this.versionIds) {
            Boolean active = Boolean.valueOf(true);
            FactDao factDao = new FactDao();
            StatsBean statsBean = new StatsBean();
            try {
                statsBean = factDao.getTeamMostPlayed(versionId);
            } catch (BuildFactsException e) {
                active = Boolean.valueOf(false);
                if (isInteractive()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
                }
            }
            if (statsBean == null) {
                active = Boolean.valueOf(false);
            }
            FactBean factBean = new FactBean();
            factBean.setFactId("TMP");
            factBean.setVersionId(versionId);
            factBean.setFactDescription("Team most played:");
            if (statsBean != null) {
                factBean.setFactResult(statsBean.getTeamName() + " (" + statsBean.getGoalsFor() + " times!)");
            }
            factBean.setGoodFact(true);
            factBean.setActive(active);

            addUpdateFact(factBean);
        }
    }


    private void buildTeamLeastPlayed() {
        for (String versionId : this.versionIds) {
            Boolean active = Boolean.valueOf(true);
            FactDao factDao = new FactDao();
            StatsBean statsBean = new StatsBean();
            try {
                statsBean = factDao.getTeamLeastPlayed(versionId);
            } catch (BuildFactsException e) {
                active = Boolean.valueOf(false);
                if (isInteractive()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
                }
            }
            if (statsBean == null) {
                active = Boolean.valueOf(false);
                continue;
            }
            FactBean factBean = new FactBean();
            factBean.setFactId("TLP");
            factBean.setVersionId(versionId);
            factBean.setFactDescription("Team least played:");
            if (statsBean.getGoalsFor() > 1) {
                factBean.setFactResult(statsBean.getTeamName() + " (" + statsBean.getGoalsFor() + " times!)");
            } else {
                factBean.setFactResult(statsBean.getTeamName() + " (" + statsBean.getGoalsFor() + " time!)");
            }
            factBean.setGoodFact(false);
            factBean.setActive(active);

            addUpdateFact(factBean);
        }
    }


    private void buildGamesSinceLastWinFact() {
        String messageResources = PropertiesUtilities.getInstance().getMessageResource();
        String versionId = PropertiesUtilities.getInstance().getProperty(messageResources, "defaultVersion");
        if (versionId.equals("ALL")) {
            versionId = PropertiesUtilities.getInstance().getProperty(messageResources, "latestVersion");
        }
        FactDao factDao = new FactDao();
        int numberOfGames = 0;
        Boolean active = Boolean.valueOf(true);

        try {
            numberOfGames = factDao.getGamesSinceWin(versionId);
        } catch (BuildFactsException e) {
            active = Boolean.valueOf(false);
            if (isInteractive()) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
            }
        }

        FactBean factBean = new FactBean();
        factBean.setFactId("GSLW");
        factBean.setVersionId(versionId);
        factBean.setFactDescription("Number of games since last win:");
        factBean.setFactResult(numberOfGames + " games!");
        if (numberOfGames > 0) {
            factBean.setGoodFact(false);
        } else {
            factBean.setGoodFact(true);
        }
        factBean.setActive(active);

        addUpdateFact(factBean);
    }


    private void buildGamesSinceLastLossFact() {
        String messageResources = PropertiesUtilities.getInstance().getMessageResource();
        String versionId = PropertiesUtilities.getInstance().getProperty(messageResources, "defaultVersion");
        if (versionId.equals("ALL")) {
            versionId = PropertiesUtilities.getInstance().getProperty(messageResources, "latestVersion");
        }
        FactDao factDao = new FactDao();
        int numberOfGames = 0;
        Boolean active = Boolean.valueOf(true);

        try {
            numberOfGames = factDao.getGamesSinceLoss(versionId);
        } catch (BuildFactsException e) {
            active = Boolean.valueOf(false);
            if (isInteractive()) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
            }
        }

        FactBean factBean = new FactBean();
        factBean.setFactId("GSLL");
        factBean.setVersionId(versionId);
        factBean.setFactDescription("Number of games since last loss:");
        factBean.setFactResult(numberOfGames + " games!");
        if (numberOfGames > 0) {
            factBean.setGoodFact(true);
        } else {
            factBean.setGoodFact(false);
        }
        factBean.setActive(active);

        addUpdateFact(factBean);
    }


    private void buildMostGoalsConceededAtHomeFact() {
        for (String versionId : this.versionIds) {
            Boolean active = Boolean.valueOf(true);
            FactDao factDao = new FactDao();
            StatsBean statsBean = new StatsBean();
            try {
                statsBean = factDao.getMostGoalsConceeded(versionId, "H");
            } catch (BuildFactsException e) {
                active = Boolean.valueOf(false);
                if (isInteractive()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
                }
            }

            if (statsBean == null || statsBean.getTeamName() == null) {
                active = Boolean.valueOf(false);
            }

            FactBean factBean = new FactBean();
            factBean.setFactId("MGCH");
            factBean.setVersionId(versionId);
            factBean.setFactDescription("Most goals conceeded at home:");
            if (active.booleanValue()) {
                String formattedDate = String.format("%tm/%td/%tY", statsBean.getGameDateTime(), statsBean.getGameDateTime(),
                        statsBean.getGameDateTime());
                factBean.setFactResult(statsBean.getGoalsAgainst() + " goals! " + "(" + statsBean.getTeamName() +
                        " on " + formattedDate + ")");
            } else {
                factBean.setFactResult("NO DATA FOUND");
            }
            factBean.setGoodFact(false);
            factBean.setActive(active);

            addUpdateFact(factBean);
        }
    }


    private void buildMostGoalsConceededAwayFact() {
        for (String versionId : this.versionIds) {
            Boolean active = Boolean.valueOf(true);
            FactDao factDao = new FactDao();
            StatsBean statsBean = new StatsBean();
            try {
                statsBean = factDao.getMostGoalsConceeded(versionId, "A");
            } catch (BuildFactsException e) {
                active = Boolean.valueOf(false);
                if (isInteractive()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
                }
            }

            if (statsBean == null || statsBean.getTeamName() == null) {
                active = Boolean.valueOf(false);
            }

            FactBean factBean = new FactBean();
            factBean.setFactId("MGCA");
            factBean.setVersionId(versionId);
            factBean.setFactDescription("Most goals conceeded away:");
            if (active.booleanValue()) {
                String formattedDate = String.format("%tm/%td/%tY", statsBean.getGameDateTime(), statsBean.getGameDateTime(),
                        statsBean.getGameDateTime());
                factBean.setFactResult(statsBean.getGoalsAgainst() + " goals! " + "(" + statsBean.getTeamName() +
                        " on " + formattedDate + ")");
            } else {
                factBean.setFactResult("NO DATA FOUND");
            }
            factBean.setGoodFact(false);
            factBean.setActive(active);

            addUpdateFact(factBean);
        }
    }


    private void buildMostGoalsScoredAtHomeFact() {
        for (String versionId : this.versionIds) {
            Boolean active = Boolean.valueOf(true);
            FactDao factDao = new FactDao();
            StatsBean statsBean = new StatsBean();
            try {
                statsBean = factDao.getMostGoalsScored(versionId, "H");
            } catch (BuildFactsException e) {
                active = Boolean.valueOf(false);
                if (isInteractive()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
                }
            }

            if (statsBean == null || statsBean.getTeamName() == null) {
                active = Boolean.valueOf(false);
            }

            FactBean factBean = new FactBean();
            factBean.setFactId("MGSH");
            factBean.setVersionId(versionId);
            factBean.setFactDescription("Most goals scored at home:");
            if (active.booleanValue()) {
                String formattedDate = String.format("%tm/%td/%tY", statsBean.getGameDateTime(), statsBean.getGameDateTime(),
                        statsBean.getGameDateTime());
                factBean.setFactResult(statsBean.getGoalsFor() + " goals! " + "(" + statsBean.getTeamName() +
                        " on " + formattedDate + ")");
            } else {
                factBean.setFactResult("NO DATA FOUND");
            }
            factBean.setGoodFact(true);
            factBean.setActive(active);

            addUpdateFact(factBean);
        }
    }


    private void buildMostGoalsScoredAwayFact() {
        for (String versionId : this.versionIds) {
            Boolean active = Boolean.valueOf(true);
            FactDao factDao = new FactDao();
            StatsBean statsBean = new StatsBean();

            try {
                statsBean = factDao.getMostGoalsScored(versionId, "A");
            } catch (BuildFactsException e) {
                active = Boolean.valueOf(false);
                if (isInteractive()) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("buildFacts", new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), " "));
                }
            }

            if (statsBean == null || statsBean.getTeamName() == null) {
                active = Boolean.valueOf(false);
            }

            FactBean factBean = new FactBean();
            factBean.setFactId("MGSA");
            factBean.setVersionId(versionId);
            factBean.setFactDescription("Most goals scored away:");
            if (active.booleanValue()) {
                String formattedDate = String.format("%tm/%td/%tY", statsBean.getGameDateTime(), statsBean.getGameDateTime(),
                        statsBean.getGameDateTime());
                factBean.setFactResult(statsBean.getGoalsFor() + " goals! " + "(" + statsBean.getTeamName() +
                        " on " + formattedDate + ")");
            } else {
                factBean.setFactResult("NO DATA FOUND");
            }
            factBean.setGoodFact(true);
            factBean.setActive(active);

            addUpdateFact(factBean);
        }
    }


    private void addUpdateFact(FactBean factBean) {
        FactDao factDao = new FactDao();
        String factDescription = factDao.getFactDescription(factBean.getVersionId(), factBean.getFactId());
        if (!StringUtils.isBlank(factDescription)) {
            factDao.updateFact(factBean);
        } else {
            factDao.addFact(factBean);
        }
    }


    private List<String> getListOfVersions() {
        return new ArrayList<String>(Arrays.asList("FIFA12", "FIFA13", "FIFA14", "FIFA15"));
    }


    public List<String> getVersionIds() {
        return this.versionIds;
    }


    public void setVersionIds(List<String> versionIds) {
        this.versionIds = versionIds;
    }


    public boolean isInteractive() {
        return this.interactive;
    }


    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }
}
