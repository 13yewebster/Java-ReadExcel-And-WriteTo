package com.bye.project;
import javax.mail.*;
import javax.mail.internet.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
 
 
public class SendGmail {
 
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final int SMTP_HOST_PORT = 465;
    private static final String SMTP_AUTH_USER = "bye.webster@gmail.com";
    private static final String SMTP_AUTH_PWD  = "***ISI_PASSWORD_LOE**";
 
    public static void sendToMail(List<String> data) throws Exception {

      	int i = 0;
    	while (i < data.size()) {
    		sendToMail(data.get(i));
    		i++;
    	}
    }
    
    public static void sendToMail(String email) throws Exception {
    	Properties props = new Properties();

        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", SMTP_HOST_NAME);
        props.put("mail.smtps.auth", true);
        // props.put("mail.smtps.quitwait", "false");
 
        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();
        /*
        // Buka Ini Kalo Mau Replace Some Kata Didalem File Template
        Map<String, String> input = new HashMap<String, String>();
	    input.put("Author", "java2db.com");
	    input.put("Topic", "HTML Template for Email");
	    input.put("Content In", "English");
        //HTML mail content
		String htmlText = readEmailFromHtml("/Users/webster/Documents/Workspace_Fire/Java-ReadExcel-And-WriteTo/assetsFolder/template.html", input);
		*/
	    String fileName = "/Users/webster/Documents/Workspace_Fire/Java-ReadExcel-And-WriteTo/assetsFolder/template.html";
 
        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject("Testing MAILBLAST Gan !");
        message.setContent(readContentFromFile(fileName), "text/html");
        message.setFrom(new InternetAddress("bye.webster@gmail.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
         
        transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
        transport.sendMessage(message,message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
    
  //Method to replace the values for keys
    protected static String readEmailFromHtml(String filePath, Map<String, String> input)
    {
    	String msg = readContentFromFile(filePath);
	     try {
		     Set<Entry<String, String>> entries = input.entrySet();
		     for(Map.Entry<String, String> entry : entries) {
		      msg = msg.replace(entry.getKey().trim(), entry.getValue().trim());
		     }
	     }catch(Exception exception){
	      exception.printStackTrace();
	     }
     
     return msg;
     
    }
    
    //Method to read HTML file as a String 
    private static String readContentFromFile(String fileName){
	     StringBuffer contents = new StringBuffer();
	     
	     try {
	     //use buffering, reading one line at a time
	     BufferedReader reader = new BufferedReader(new FileReader(fileName));
	     try {
		     String line = null; 
		     while (( line = reader.readLine()) != null){
			     contents.append(line);
			     contents.append(System.getProperty("line.separator"));
		     }
	     }finally {
	       reader.close();
	     	}
	     }catch (IOException ex){
	    	 ex.printStackTrace();
	     }
	     
	     return contents.toString();
    }

}