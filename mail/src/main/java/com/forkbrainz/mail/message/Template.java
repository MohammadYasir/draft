package com.forkbrainz.mail.message;

import java.util.ArrayList;
import java.util.HashMap;

public class Template
{
    private HashMap<String, String> imageMap;
    private String templateFileName;
    private ArrayList<String> contentKeys;
    
    public ArrayList<String> getContentKeys()
    {
        return contentKeys;
    }
    public void setContentKeys(ArrayList<String> contentKeys)
    {
        this.contentKeys = contentKeys;
    }
    public HashMap<String, String> getImageMap()
    {
        return imageMap;
    }
    public void setImageMap(HashMap<String, String> imageMap)
    {
        this.imageMap = imageMap;
    }
    public String getTemplateFileName()
    {
        return templateFileName;
    }
    public void setTemplateFileName(String templateFileName)
    {
        this.templateFileName = templateFileName;
    }
}
