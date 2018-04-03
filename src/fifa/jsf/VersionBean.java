// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VersionBean.java

package fifa.jsf;

import com.mysql.jdbc.StringUtils;
import fifa.facts.dao.FactDao;
import fifa.facts.utilities.FactUtilities;
import fifa.utilities.*;
import java.io.Serializable;
import java.util.*;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

public class VersionBean
    implements Serializable, FIFAConstants
{

    public VersionBean()
    {
        versionSelectItems = loadVersionSelectItems();
    }

    public String getVersionId()
    {
        resetSessionAttributes();
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
        return versionId;
    }

    public String getVersionIdNotAll()
    {
        resetSessionAttributes();
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
        if(versionId == null || versionId.equalsIgnoreCase("ALL"))
            versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "latestVersion");
        return versionId;
    }

    private void resetSessionAttributes()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("playersRestrictRows", Integer.valueOf(200));
        session.setAttribute("statsRestrictRows", Integer.valueOf(10));
        session.setAttribute("teamsRestrictRows", Integer.valueOf(50));
        session.setAttribute("countriesRestrictRows", Integer.valueOf(50));
    }

    public void setVersionId(String s)
    {
    }

    public void versionIdChanged(ValueChangeEvent arg0)
        throws AbortProcessingException
    {
        String versionId = arg0.getNewValue().toString();
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        propertiesUtilities.setMessageProperty(propertiesUtilities.getMessageResource(), "defaultVersion", versionId);
        NavigationUtilities navigationUtilities = NavigationUtilities.getInstance();
        navigationUtilities.goHome();
    }

    public String getDatabaseEnvironment()
    {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        return propertiesUtilities.getDatabaseEnvironment();
    }

    public void buildAllFactsInteractive()
    {
        buildAllFacts(true);
    }

    public void buildAllFacts(boolean interactive)
    {
        FactUtilities factUtilities = FactUtilities.getInstance();
        factUtilities.buildAllFacts(interactive);
    }

    public String getLastFactBuildDate()
    {
        FactUtilities factUtilities = FactUtilities.getInstance();
        return factUtilities.getLastFactBuildDate();
    }

    public List getFacts()
    {
        FactDao factDao = new FactDao();
        List facts = factDao.getFacts();
        long seed = System.nanoTime();
        Collections.shuffle(facts, new Random(seed));
        return facts;
    }

    public void doVersionToggle()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
        String factVersionId = getNextVersionId();
        session.setAttribute("factVersion", factVersionId);
    }

    public String getNextVersionId()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
        String factVersionId = (String)session.getAttribute("factVersion");
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        if(factVersionId != null && factVersionId.equals("ALL"))
        {
            factVersionId = "FIFA12";
        } else
        {
            if(StringUtils.isNullOrEmpty(factVersionId))
                factVersionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "defaultVersion");
            if(factVersionId != null && factVersionId.equals("ALL"))
            {
                factVersionId = "FIFA12";
            } else
            {
                Integer versionNumber = Integer.valueOf(0);
                if(factVersionId != null)
                {
                    String versionNumberString = factVersionId.length() <= 2 ? factVersionId : factVersionId.substring(factVersionId.length() - 2);
                    versionNumber = Integer.valueOf(Integer.parseInt(versionNumberString));
                }
                int latestVersionNumber = getVersionNumber("lastest");
                if(versionNumber.intValue() < latestVersionNumber)
                {
                    versionNumber = Integer.valueOf(versionNumber.intValue() + 1);
                } else
                {
                    versionNumber = Integer.valueOf(0);
                    factVersionId = "ALL";
                }
                if(versionNumber.intValue() > 0)
                    factVersionId = (new StringBuilder("FIFA")).append(versionNumber.toString()).toString();
            }
        }
        return factVersionId;
    }

    public List getVersionSelectItems()
    {
        return versionSelectItems;
    }

    public void setVersionSelectItems(List versionSelectItems)
    {
        this.versionSelectItems = versionSelectItems;
    }

    private List loadVersionSelectItems()
    {
        int earliestVersionNumber = getVersionNumber("earliest");
        int latestVersionNumber = getVersionNumber("latest");
        versionSelectItems = new ArrayList();
        SelectItem item = new SelectItem("ALL", "All versions");
        versionSelectItems.add(item);
        for(Integer x = Integer.valueOf(earliestVersionNumber); x.intValue() <= latestVersionNumber; x = Integer.valueOf(x.intValue() + 1))
        {
            item = new SelectItem((new StringBuilder("FIFA")).append(x.toString()).toString(), (new StringBuilder("FIFA ")).append(x.toString()).toString());
            versionSelectItems.add(item);
        }

        return versionSelectItems;
    }

    private int getVersionNumber(String versionType)
    {
        PropertiesUtilities propertiesUtilities = PropertiesUtilities.getInstance();
        String versionId = null;
        if(versionType.equalsIgnoreCase("latest"))
            versionId = propertiesUtilities.getProperty(propertiesUtilities.getMessageResource(), "latestVersion");
        else
            versionId = "FIFA12";
        String versionNumberString = "";
        if(StringUtils.isEmptyOrWhitespaceOnly(versionId))
            versionNumberString = "0";
        else
            versionNumberString = versionId.length() <= 2 ? versionId : versionId.substring(versionId.length() - 2);
        int versionNumber = Integer.parseInt(versionNumberString);
        return versionNumber;
    }

    private static final long serialVersionUID = 1L;
    private List versionSelectItems;
    private final String FIFA = "FIFA";
    private final String ALL = "ALL";
}
