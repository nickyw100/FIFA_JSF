package fifa.jsf;

import fifa.utilities.FIFAConstants;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;



public abstract class AbstractResultBean
        implements Serializable, FIFAConstants
{
    private static final long serialVersionUID = 5520009024579958071L;
    protected String versionId;
    protected String teamName;
    protected String playerName;
    protected Date gameDateTime;
    protected int goalsFor;
    protected int goalsAgainst;
    protected boolean extraTime;
    protected int penaltiesFor;
    protected int penaltiesAgainst;
    protected int possessionPercentage;
    protected int shotAccuracyPercentage;
    protected int shots;
    protected int shotsOnTarget;
    protected int opponentShots;
    protected int opponentShotsOnTarget;
    protected int opponentDivision;
    protected String gameComments;

    public String getTeamName() { return this.teamName; }



    public void setTeamName(String teamName) { this.teamName = teamName; }



    public String getPlayerName() { return this.playerName; }



    public void setPlayerName(String playerName) { this.playerName = playerName; }



    public Date getGameDateTime() { return this.gameDateTime; }



    public void setGameDateTime(Date gameDateTime) { this.gameDateTime = gameDateTime; }



    public int getGoalsFor() { return this.goalsFor; }



    public void setGoalsFor(int goalsFor) { this.goalsFor = goalsFor; }



    public int getGoalsAgainst() { return this.goalsAgainst; }



    public void setGoalsAgainst(int goalsAgainst) { this.goalsAgainst = goalsAgainst; }



    public boolean isExtraTime() { return this.extraTime; }



    public void setExtraTime(boolean extraTime) { this.extraTime = extraTime; }



    public int getPenaltiesFor() { return this.penaltiesFor; }



    public void setPenaltiesFor(int penaltiesFor) { this.penaltiesFor = penaltiesFor; }



    public int getPenaltiesAgainst() { return this.penaltiesAgainst; }



    public void setPenaltiesAgainst(int penaltiesAgainst) { this.penaltiesAgainst = penaltiesAgainst; }



    public String getVersionId() { return this.versionId; }



    public void setVersionId(String versionId) { this.versionId = versionId; }



    public String getGameComments() { return this.gameComments; }



    public void setGameComments(String gameComments) { this.gameComments = gameComments; }



    public int getPossessionPercentage() { return this.possessionPercentage; }



    public void setPossessionPercentage(int possessionPercentage) { this.possessionPercentage = possessionPercentage; }



    public int getShots() { return this.shots; }



    public void setShots(int shots) { this.shots = shots; }



    public int getShotsOnTarget() { return this.shotsOnTarget; }



    public void setShotsOnTarget(int shotsOnTarget) { this.shotsOnTarget = shotsOnTarget; }



    public int getOpponentShots() { return this.opponentShots; }



    public void setOpponentShots(int opponentShots) { this.opponentShots = opponentShots; }



    public int getOpponentShotsOnTarget() { return this.opponentShotsOnTarget; }



    public void setOpponentShotsOnTarget(int opponentShotsOnTarget) { this.opponentShotsOnTarget = opponentShotsOnTarget; }




    public int getShotAccuracyPercentage() {
        BigDecimal bg3, bg1 = new BigDecimal(this.shotsOnTarget);
        BigDecimal bg2 = new BigDecimal(this.shots);
        if (!bg2.equals(BigDecimal.ZERO)) {
            bg3 = bg1.divide(bg2, 2, RoundingMode.HALF_UP);
            bg3 = bg3.multiply(BIG_DECIMAL_ONE_HUNDRED);
        } else {
            bg3 = BigDecimal.ZERO;
        }
        this.shotAccuracyPercentage = bg3.intValue();
        return this.shotAccuracyPercentage;
    }


    public void setShotAccuracyPercentage(int shotAccuracyPercentage) { this.shotAccuracyPercentage = shotAccuracyPercentage; }



    public int getOpponentDivision() { return this.opponentDivision; }



    public void setOpponentDivision(int opponentDivision) { this.opponentDivision = opponentDivision; }




    public String getFullGameComments() {
        StringBuilder fullGameCommentText = new StringBuilder();
        fullGameCommentText.append("Possession: ");
        fullGameCommentText.append(getPossessionPercentage());
        fullGameCommentText.append("% Shots: ");
        fullGameCommentText.append(getShots());
        fullGameCommentText.append("(");
        fullGameCommentText.append(getShotsOnTarget());
        fullGameCommentText.append(") v ");
        fullGameCommentText.append(getOpponentShots());
        fullGameCommentText.append("(");
        fullGameCommentText.append(getOpponentShotsOnTarget());
        fullGameCommentText.append(") ");
        fullGameCommentText.append(getShotAccuracyPercentage());
        fullGameCommentText.append("% ");
        if (getGameComments() != null) {
            fullGameCommentText.append(getGameComments());
        }

        if (!fullGameCommentText.toString().substring(fullGameCommentText.toString().length() - 1).equals(".")) {
            fullGameCommentText.append(".");
        }
        if (getOpponentDivision() > 0) {
            fullGameCommentText.append(" Opponent's current division: " + getOpponentDivision());
        }

        return fullGameCommentText.toString();
    }
}
