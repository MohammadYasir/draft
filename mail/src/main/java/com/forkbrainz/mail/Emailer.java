package com.forkbrainz.mail;

import com.forkbrainz.mail.message.Template;
import com.forkbrainz.mail.message.MailMessage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.forkbrainz.mail.util.TemplateMapParser;

public class Emailer
{
    private static Emailer                   INSTANCE;

    private LinkedBlockingQueue<MailMessage> messageQueue;
    private Properties                       smtpProp;
    public static String                     WEB_INF_PATH;
    private HashMap<String, Template>        templates;
    private Thread                           senderThread;

    public static Emailer getInstance(String configFilePath, String templateFilePath, String pathToWebInf)
            throws FileNotFoundException, IOException, ParserConfigurationException, SAXException
    {
        WEB_INF_PATH = pathToWebInf;
        if (INSTANCE == null)
        {
            INSTANCE = new Emailer(configFilePath, templateFilePath);
        }
        return INSTANCE;
    }

    public static Emailer getInstance()
    {
        return INSTANCE;
    }

    private Emailer(String configFilePath, String templateFilePath) throws FileNotFoundException, IOException,
            ParserConfigurationException, SAXException
    {
        //Initialize smtp properties
        smtpProp = new Properties();
        smtpProp.load(new FileReader(configFilePath));

        //Initialize template map
        templates = new HashMap<String, Template>();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        TemplateMapParser handler = new TemplateMapParser(templates);
        saxParser.parse(templateFilePath, handler);

        //Initialize message queue
        messageQueue = new LinkedBlockingQueue<MailMessage>();
        //Start sender thread
        senderThread = new Thread(new MailSender(messageQueue, templates, smtpProp));
        senderThread.start();

    }

    public void enqueueEmailToSend(MailMessage msg)
    {
        try
        {
            messageQueue.put(msg);
            System.out.println("Message placed in msg queue");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void shutdownEmailer()
    {
        senderThread.interrupt();
    }
}
