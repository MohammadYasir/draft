package com.forkbrainz.mail.message;

import java.util.HashMap;

public class MailMessage
{
    private String                  destinationAddress;
    private String                  sourceAddress;
    private String                  sourcePassword;
    private HashMap<String, String> contentMap;
    private String                  templateName;
    private String                  subject;
    
    public MailMessage(String destinationAddress, String sourceAddress, String sourcePassword, HashMap<String, String> contentMap,
            String templateName, String subject)
    {
        super();
        this.destinationAddress = destinationAddress;
        this.sourceAddress = sourceAddress;
        this.sourcePassword = sourcePassword;
        this.contentMap = contentMap;
        this.templateName = templateName;
        this.subject = subject;
    }

    public String getDestinationAddress()
    {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress)
    {
        this.destinationAddress = destinationAddress;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getSourceAddress()
    {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress)
    {
        this.sourceAddress = sourceAddress;
    }

    public String getSourcePassword()
    {
        return sourcePassword;
    }

    public void setSourcePassword(String sourcePassword)
    {
        this.sourcePassword = sourcePassword;
    }

    public HashMap<String, String> getContentMap()
    {
        return contentMap;
    }

    public void setContentMap(HashMap<String, String> contentMap)
    {
        this.contentMap = contentMap;
    }

    public String getTemplateName()
    {
        return templateName;
    }

    public void setTemplateName(String templateName)
    {
        this.templateName = templateName;
    }
}
