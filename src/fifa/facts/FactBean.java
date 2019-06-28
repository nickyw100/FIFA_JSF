package fifa.facts;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class FactBean
        implements Serializable {
    private static final long serialVersionUID = -2376602221717796375L;
    private String versionId;
    private String versionDescription;
    private String factId;
    private String factDescription;
    private String factResult;
    private Boolean active;
    private boolean goodFact;

    public FactBean(String versionId, String factId, String factDescription, String factResult, Boolean goodFact) {
        this.goodFact = true;


        this.versionId = versionId;
        this.versionDescription = getVersionDescription();
        this.factId = factId;
        this.factDescription = factDescription;
        this.factResult = factResult;
        this.goodFact = goodFact.booleanValue();
    }


    public FactBean() {
        this.goodFact = true;
    }


    public String getVersionId() {
        return this.versionId;
    }


    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }


    public String getFactDescription() {
        return this.factDescription;
    }


    public void setFactDescription(String factDescription) {
        this.factDescription = factDescription;
    }


    public String getFactResult() {
        return this.factResult;
    }


    public void setFactResult(String factResult) {
        this.factResult = factResult;
    }


    public String getFactId() {
        return this.factId;
    }


    public void setFactId(String factId) {
        this.factId = factId;
    }


    public boolean getGoodFact() {
        return this.goodFact;
    }


    public void setGoodFact(boolean goodFact) {
        this.goodFact = goodFact;
    }


    public Boolean getActive() {
        return this.active;
    }


    public void setActive(Boolean active) {
        this.active = active;
    }


    public String getVersionDescription() {
        String versionNumber = this.versionId.substring(Math.max(this.versionId.length() - 2, 0));
        this.versionDescription = "FIFA " + versionNumber;
        return this.versionDescription;
    }


    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
    }
}
