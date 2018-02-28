package com.forkbrainz.mail.message;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.forkbrainz.mail.Emailer;

public class MailContentGenerator
{
    private HashMap<String, String> contentMap;
    private String                  templateFilePath;
    private HashMap<String, String> imageMap;
    private ArrayList<MimeBodyPart> imageList;

    public MailContentGenerator(HashMap<String, String> contentMap, String templateFilePath, HashMap<String, String> imageMap)
    {
        super();
        this.contentMap = contentMap;
        this.templateFilePath = templateFilePath;
        this.imageMap = imageMap;
    }

    public String getContent() throws IOException, MessagingException
    {
        File templateFile = new File(templateFilePath);
        String content = null;

        Document document = Jsoup.parse(templateFile, "UTF-8");
        Set<String> contentKeys = contentMap.keySet();
        for (Iterator<String> it = contentKeys.iterator(); it.hasNext();)
        {
            String string = it.next();
            if (string.contains("_"))
            {
                String temp[] = string.split("_");
                Element element = document.getElementById(temp[0]);
                element.attr(temp[1], contentMap.get(string));
            }
            else
            {
                Element element = document.getElementById(string);
                element.appendText(contentMap.get(string));
            }

        }
        //Initialize the image list
        imageList = new ArrayList<>();
        Set<String> imageKeys = imageMap.keySet();
        for (Iterator<String> it = imageKeys.iterator(); it.hasNext();)
        {
            String string = it.next();
            String path = Emailer.WEB_INF_PATH+"/resources/mail/templates/images/"+imageMap.get(string);
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(path);
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<" + string + ">");
            imageList.add(messageBodyPart);
            //Add content specific to images
            Element element = document.getElementById(string);
            element.attr("src", "cid:" + string);
        }
        content = document.toString();

        return content;
    }

    public ArrayList<MimeBodyPart> getImageList()
    {
        return imageList;
    }
}
