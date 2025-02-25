  package com.mobile.notification;

  import jakarta.annotation.PostConstruct;
  import org.springframework.http.converter.StringHttpMessageConverter;
  import org.springframework.stereotype.Service;
  import org.springframework.web.client.RestTemplate;

  import java.nio.charset.StandardCharsets;
  import java.util.logging.Logger;

  @Service
  public class NotificationService {

    private static final Logger logger = Logger.getLogger(NotificationService.class.getName());
    private final RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    private void init() {
      restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    public void sendNotification(String title, String description) {
      logger.info("Sending notification: " + title + " - " + description);
      Notification notification = new Notification(title, description);

      String androidServerUrl = "http://localhost:9090/notification";
      restTemplate.postForObject(androidServerUrl, notification, String.class);
    }

    private static class Notification {
      private String title;
      private String description;

      public Notification(String title, String description) {
        this.title = title;
        this.description = description;
      }

      public String getTitle() {
        return title;
      }

      public void setTitle(String title) {
        this.title = title;
      }

      public String getDescription() {
        return description;
      }

      public void setDescription(String description) {
        this.description = description;
      }
    }
  }