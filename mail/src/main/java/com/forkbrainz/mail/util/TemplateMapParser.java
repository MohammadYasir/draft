package com.forkbrainz.mail.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.forkbrainz.mail.message.Template;

public class TemplateMapParser extends DefaultHandler
{
    private boolean                   templateTag;
    private boolean                   nameTag;
    private boolean                   fileTag;
    private boolean                   imageTag;
    private boolean                   keyTag;
    private boolean                   valueTag;
    private boolean                   contentKeysTag;

    private Template                  currentTemplate;
    private String                    curTemplateName;
    private HashMap<String, String>   imageMap;
    private String                    curKey;

    private HashMap<String, Template> templates;

    public TemplateMapParser(HashMap<String, Template> templates)
    {
        super();
        this.templates = templates;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (qName.equalsIgnoreCase("template"))
        {
            templateTag = true;
        }
        else if (qName.equalsIgnoreCase("name"))
        {
            nameTag = true;
        }
        else if (qName.equalsIgnoreCase("file"))
        {
            fileTag = true;
        }
        else if (qName.equalsIgnoreCase("key"))
        {
            keyTag = true;
        }
        else if (qName.equalsIgnoreCase("value"))
        {
            valueTag = true;
        }
        else if (qName.equalsIgnoreCase("image"))
        {
            imageTag = true;
        }
        else if (qName.equalsIgnoreCase("contentKeys"))
        {
            contentKeysTag = true;
        }
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (qName.equalsIgnoreCase("template"))
        {
            templateTag = false;
            templates.put(curTemplateName, currentTemplate);
            currentTemplate = null;
            curTemplateName = null;
        }
        else if (qName.equalsIgnoreCase("name"))
        {
            nameTag = false;
        }
        else if (qName.equalsIgnoreCase("file"))
        {
            fileTag = false;
        }
        else if (qName.equalsIgnoreCase("key"))
        {
            keyTag = false;
        }
        else if (qName.equalsIgnoreCase("value"))
        {
            valueTag = false;
        }
        else if (qName.equalsIgnoreCase("image"))
        {
            imageTag = false;
            currentTemplate.setImageMap(imageMap);
            imageMap = null;
        }
        else if (qName.equalsIgnoreCase("contentKeys"))
        {
            contentKeysTag = false;
        }
        super.endElement(uri, localName, qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        String val = new String(ch, start, length);
        if (templateTag && nameTag)
        {
            currentTemplate = new Template();
            curTemplateName = val;
        }
        else if (templateTag && fileTag)
        {
            currentTemplate.setTemplateFileName(val);
        }
        else if (templateTag && imageTag && imageMap == null)
        {
            imageMap = new HashMap<String, String>();
        }
        else if (templateTag && imageTag && keyTag)
        {
            curKey = val;
        }
        else if (templateTag && imageTag && valueTag)
        {
            imageMap.put(curKey, val);
        }
        else if (templateTag && contentKeysTag && valueTag)
        {
            if (currentTemplate.getContentKeys() == null)
            {
                currentTemplate.setContentKeys(new ArrayList<String>());
            }
            currentTemplate.getContentKeys().add(val);
        }
        super.characters(ch, start, length);
    }

    public HashMap<String, Template> getTemplates()
    {
        return templates;
    }
}
