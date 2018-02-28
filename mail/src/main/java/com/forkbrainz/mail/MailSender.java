package com.forkbrainz.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.forkbrainz.mail.message.MailContentGenerator;
import com.forkbrainz.mail.message.MailMessage;
import com.forkbrainz.mail.message.Template;

public class MailSender implements Runnable
{
    private LinkedBlockingQueue<MailMessage> messageQueue;
    private HashMap<String, Template>        templateMap;
    private Properties                       smtpProperties;

    public MailSender(LinkedBlockingQueue<MailMessage> messageQueue, HashMap<String, Template> templateMap,
            Properties smtpProperties)
    {
        super();
        this.messageQueue = messageQueue;
        this.templateMap = templateMap;
        this.smtpProperties = smtpProperties;
    }

    @Override
    public void run()
    {
        System.out.println("Mail Sender thread started");
        while (true)
        {
            try
            {
                System.out.println("1");
                final MailMessage message = messageQueue.take();
                System.out.println("2");
                Session session = Session.getInstance(smtpProperties, new javax.mail.Authenticator()
                {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(message.getSourceAddress(), message.getSourcePassword());
                    }
                });
                Message email = new MimeMessage(session);
                email.setFrom(new InternetAddress(message.getSourceAddress()));
                email.setRecipients(Message.RecipientType.TO, InternetAddress.parse(message.getDestinationAddress()));
                email.setSubject(message.getSubject());
                
                //Create content
                MimeMultipart multipart = new MimeMultipart("related");
                BodyPart messageBodyPart = new MimeBodyPart();
                String templateName = message.getTemplateName();
                Template template = templateMap.get(templateName);
                HashMap<String, String> imageMap = template.getImageMap();
                String templateFilePath = Emailer.WEB_INF_PATH+"/resources/mail/templates/"+template.getTemplateFileName();
                MailContentGenerator generator = new MailContentGenerator(message.getContentMap(), templateFilePath, imageMap);
                messageBodyPart.setContent(generator.getContent(), "text/html");
                multipart.addBodyPart(messageBodyPart);
                //Add images
                int noOfimages = generator.getImageList().size();
                for (int i = 0; i < noOfimages; i++) {
                    messageBodyPart = generator.getImageList().get(i);
                    multipart.addBodyPart(messageBodyPart);
                }
                System.out.println("3");
                email.setContent(multipart);
                Transport.send(email);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                break;
            }
            catch (AddressException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (MessagingException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
