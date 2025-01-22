package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class EmailApplication {

  public static void sendEmail(String host, String from, String password, String to, String subject, String body) {
    Properties properties = new Properties();
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");

    Session session = Session.getInstance(properties, new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(from, password);
      }
    });

    try {
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject(subject);
      message.setText(body);

      Transport.send(message);
      System.out.println("Email sent successfully!");
    } catch (MessagingException e) {
      System.err.println("Failed to send email: " + e.getMessage());
      e.printStackTrace(); // Optional: Print stack trace for debugging purposes
    }
  }

  public static void sendEmailWithAttachment(String host, String from, String password, String to, String subject, String body,
      String attachmentPath) {
    Properties properties = new Properties();
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");

    Session session = Session.getInstance(properties, new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(from, password);
      }
    });

    try {
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject(subject);

      MimeBodyPart textPart = new MimeBodyPart();
      textPart.setText(body);

      MimeBodyPart attachmentPart = new MimeBodyPart();
      FileDataSource fileDataSource = new FileDataSource(attachmentPath);
      attachmentPart.setDataHandler(new DataHandler(fileDataSource));
      attachmentPart.setFileName(new File(attachmentPath).getName());

      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(textPart);
      multipart.addBodyPart(attachmentPart);

      message.setContent(multipart);

      Transport.send(message);
      System.out.println("Email with attachment sent successfully!");
    } catch (MessagingException e) {
      System.err.println("Failed to send email with attachment: " + e.getMessage());
      e.printStackTrace(); // Optional: Print stack trace for debugging purposes
    }
  }

  public static void readEmailsPOP3(String host, String username, String password) {
    System.out.println("\nReading emails via POP3...");
    Properties properties = new Properties();
    properties.put("mail.pop3.host", host);
    properties.put("mail.pop3.port", "995");
    properties.put("mail.pop3.ssl.enable", "true");

    try {
      Session session = Session.getInstance(properties);
      Store store = session.getStore("pop3s");
      store.connect(host, username, password);

      Folder folder = store.getFolder("inbox");
      folder.open(Folder.READ_ONLY);
      Message[] messages = folder.getMessages();
      processEmailsWithOptions(messages, store, username, password);
      folder.close(false);
      store.close();
    } catch (MessagingException e) {
      System.err.println("Failed to read emails via POP3: " + e.getMessage());
      e.printStackTrace(); // Optional: Print stack trace for debugging purposes
    }
  }

  public static void readEmailsIMAP(String host, String username, String password) {
    System.out.println("\nReading emails via IMAP...");
    Properties properties = new Properties();
    properties.put("mail.imap.host", host);
    properties.put("mail.imap.port", "993");
    properties.put("mail.imap.ssl.enable", "true");

    try {
      Session session = Session.getInstance(properties);
      Store store = session.getStore("imaps");
      store.connect(host, username, password);

      Folder folder = store.getFolder("INBOX");
      folder.open(Folder.READ_ONLY);
      Message[] messages = folder.getMessages();
      processEmailsWithOptions(messages, store, username, password);
      folder.close(false);
      store.close();
    } catch (MessagingException e) {
      System.err.println("Failed to read emails via IMAP: " + e.getMessage());
      e.printStackTrace(); // Optional: Print stack trace for debugging purposes
    }
  }

  private static void processEmailsWithOptions(Message[] messages, Store store, String from, String password) {
    Scanner scanner = new Scanner(System.in);
    for (int i = 0; i < messages.length; i++) {
      Message message = messages[i];
      try {
        System.out.println("Subject: " + message.getSubject());
        System.out.println("From: " + message.getFrom()[0]);
        System.out.println("Date: " + message.getSentDate());
        Object content = message.getContent();
        if (content instanceof Multipart) {
          Multipart multipart = (Multipart) content;
          processMultipart(multipart);
        } else {
          System.out.println("Content: " + content);
        }
        System.out.println("----------------------------");

        System.out.println("What would you like to do?");
        System.out.println("1. Reply to this message");
        System.out.println("2. Go to the next message");
        System.out.println("3. Exit to main menu");

        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
          case 1:
            replyToMessage(message, from, password);
            break;
          case 2:
            if (i == messages.length - 1) {
              System.out.println("No more messages.");
            }
            break;
          case 3:
            return;
          default:
            System.out.println("Invalid choice. Please choose a number between 1 and 3.");
        }
      } catch (MessagingException | IOException e) {
        System.err.println("Failed to process email: " + e.getMessage());
        e.printStackTrace(); // Optional: Print stack trace for debugging purposes
      }
    }
  }

  private static void processMultipart(Multipart multipart) throws MessagingException, IOException {
    for (int i = 0; i < multipart.getCount(); i++) {
      BodyPart part = multipart.getBodyPart(i);
      if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
        saveAttachment(part);
      } else if (part.isMimeType("text/plain")) {
        System.out.println("Text: " + part.getContent());
      } else if (part.isMimeType("text/html")) {
        System.out.println("HTML: " + part.getContent());
      } else if (part.isMimeType("multipart/*")) {
        processMultipart((Multipart) part.getContent());
      }
    }
  }

  private static void saveAttachment(BodyPart part) throws IOException, MessagingException {
    String fileName = part.getFileName();
    if (fileName != null) {
      fileName = MimeUtility.decodeText(fileName);
      fileName = sanitizeFileName(fileName);

      File downloadsDir = new File("downloads");
      if (!downloadsDir.exists()) {
        downloadsDir.mkdirs();
      }
      File file = new File(downloadsDir, fileName);
      try (InputStream inputStream = part.getInputStream();
          FileOutputStream outputStream = new FileOutputStream(file)) {
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
          outputStream.write(buffer, 0, bytesRead);
        }
        System.out.println("Attachment saved to: " + file.getAbsolutePath());
      }
    }
  }

  private static String sanitizeFileName(String fileName) {
    return fileName.replaceAll("[^a-zA-Z0-9.-]", "_");
  }

  private static void replyToMessage(Message originalMessage, String from, String password) {
    Scanner scanner = new Scanner(System.in);

    try {
      System.out.print("SMTP Host: ");
      String smtpHost = scanner.nextLine();
      System.out.flush();

      String to = originalMessage.getFrom()[0].toString();

      System.out.print("Subject: Re: " + originalMessage.getSubject() + "\n");
      System.out.print("Body: ");
      String body = scanner.nextLine();
      System.out.flush();

      // Optional: Ask for Reply-To address
      System.out.print("Reply-To (optional): ");
      String replyToInput = scanner.nextLine().trim();
      InternetAddress[] replyToAddresses = null;
      if (!replyToInput.isEmpty()) {
        replyToAddresses = new InternetAddress[]{new InternetAddress(replyToInput)};
      }

      Properties properties = new Properties();
      properties.put("mail.smtp.host", smtpHost);
      properties.put("mail.smtp.port", "587");
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.starttls.enable", "true");

      Session session = Session.getInstance(properties, new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(from, password);
        }
      });

      MimeMessage replyMessage = new MimeMessage(session);
      replyMessage.setFrom(new InternetAddress(from));
      replyMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      replyMessage.setSubject("Re: " + originalMessage.getSubject());
      replyMessage.setText(body);

      if (replyToAddresses != null) {
        replyMessage.setReplyTo(replyToAddresses);
      }

      // Set in-reply-to and references headers
      replyMessage.setHeader("In-Reply-To", originalMessage.getHeader("Message-ID")[0]);
      replyMessage.setHeader("References", originalMessage.getHeader("Message-ID")[0]);

      Transport.send(replyMessage);
      System.out.println("Reply sent successfully!");
    } catch (MessagingException e) {
      System.err.println("Failed to send reply message: " + e.getMessage());
      e.printStackTrace(); // Optional: Print stack trace for debugging purposes
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    boolean continueLoop = true;

    while (continueLoop) {
      System.out.println("Choose action: ");
      System.out.println("1. Send email without attachment");
      System.out.println("2. Send email with attachment");
      System.out.println("3. Read emails via POP3");
      System.out.println("4. Read emails via IMAP");
      System.out.println("5. Exit");

      int choice = 0;
      try {
        choice = Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number between 1 and 5.");
        continue;
      }

      switch (choice) {
        case 1:
          System.out.print("Host: ");
          String host = scanner.nextLine();
          System.out.flush();
          System.out.print("From: ");
          String from = scanner.nextLine();
          System.out.flush();
          System.out.print("Password: ");
          String password = scanner.nextLine();
          System.out.flush();
          System.out.print("To: ");
          String to = scanner.nextLine();
          System.out.flush();
          System.out.print("Subject: ");
          String subject = scanner.nextLine();
          System.out.flush();
          System.out.print("Body: ");
          String body = scanner.nextLine();
          System.out.flush();
          sendEmail(host, from, password, to, subject, body);
          break;
        case 2:
          System.out.print("Host: ");
          host = scanner.nextLine();
          System.out.flush();
          System.out.print("From: ");
          from = scanner.nextLine();
          System.out.flush();
          System.out.print("Password: ");
          password = scanner.nextLine();
          System.out.flush();
          System.out.print("To: ");
          to = scanner.nextLine();
          System.out.flush();
          System.out.print("Subject: ");
          subject = scanner.nextLine();
          System.out.flush();
          System.out.print("Body: ");
          body = scanner.nextLine();
          System.out.flush();
          System.out.print("Attachment path: ");
          String attachmentPath = scanner.nextLine();
          System.out.flush();
          sendEmailWithAttachment(host, from, password, to, subject, body, attachmentPath);
          break;
        case 3:
          System.out.print("Host: ");
          host = scanner.nextLine();
          System.out.flush();
          System.out.print("Username: ");
          String username = scanner.nextLine();
          System.out.flush();
          System.out.print("Password: ");
          password = scanner.nextLine();
          System.out.flush();
          readEmailsPOP3(host, username, password);
          break;
        case 4:
          System.out.print("Host: ");
          host = scanner.nextLine();
          System.out.flush();
          System.out.print("Username: ");
          username = scanner.nextLine();
          System.out.flush();
          System.out.print("Password: ");
          password = scanner.nextLine();
          System.out.flush();
          readEmailsIMAP(host, username, password);
          break;
        case 5:
          continueLoop = false;
          System.out.println("Exiting the application.");
          break;
        default:
          System.out.println("Invalid choice. Please choose a number between 1 and 5.");
      }
    }
  }
}