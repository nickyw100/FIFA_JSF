// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FactBean.java

package fifa.facts;

import java.io.Serializable;

public class FactBean
    implements Serializable
{

    public FactBean(String versionId, String factId, String factDescription, String factResult, Boolean goodFact)
    {
        this.goodFact = true;
        this.versionId = versionId;
        versionDescription = getVersionDescription();
        this.factId = factId;
        this.factDescription = factDescription;
        this.factResult = factResult;
        this.goodFact = goodFact.booleanValue();
    }

    public FactBean()
    {
        goodFact = true;
    }

    public String getVersionId()
    {
        return versionId;
    }

    public void setVersionId(String versionId)
    {
        this.versionId = versionId;
    }

    public String getFactDescription()
    {
        return factDescription;
    }

    public void setFactDescription(String factDescription)
    {
        this.factDescription = factDescription;
    }

    public String getFactResult()
    {
        return factResult;
    }

    public void setFactResult(String factResult)
    {
        this.factResult = factResult;
    }

    public String getFactId()
    {
        return factId;
    }

    public void setFactId(String factId)
    {
        this.factId = factId;
    }

    public boolean getGoodFact()
    {
        return goodFact;
    }

    public void setGoodFact(boolean goodFact)
    {
        this.goodFact = goodFact;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public String getVersionDescription()
    {
        String versionNumber = versionId.substring(Math.max(versionId.length() - 2, 0));
        versionDescription = (new StringBuilder("FIFA ")).append(versionNumber).toString();
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription)
    {
        this.versionDescription = versionDescription;
    }

    private static final long serialVersionUID = 0x21b49e9L;
    private String versionId;
    private String versionDescription;
    private String factId;
    private String factDescription;
    private String factResult;
    private Boolean active;
    private boolean goodFact;
}
