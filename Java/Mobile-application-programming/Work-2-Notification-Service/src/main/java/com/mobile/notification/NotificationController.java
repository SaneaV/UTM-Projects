package com.mobile.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.logging.Logger;

@RestController
@RequestMapping("/notifications")
@EnableScheduling
public class NotificationController {

  private static final Logger logger = Logger.getLogger(NotificationController.class.getName());

  @Autowired
  private TaskScheduler taskScheduler;

  @Autowired
  private NotificationService notificationService;

  @PostMapping("/create")
  public ResponseEntity<String> createNotification(@RequestBody NotificationRequest notificationRequest) {
    logger.info("Received request to create notification: " + notificationRequest);
    scheduleNotification(notificationRequest.getTitle(), notificationRequest.getDescription(), notificationRequest.getTimeInMillis());
    return new ResponseEntity<>("Notification created successfully", HttpStatus.OK);
  }

  private void scheduleNotification(String title, String description, long timeInMillis) {
    long currentTimeMillis = System.currentTimeMillis();
    long oneHourBeforeInMillis = timeInMillis - 60 * 60 * 1000;

    if (oneHourBeforeInMillis > currentTimeMillis) {
      logger.info("Scheduling 'Reminder in 1 hour' notification for: " + oneHourBeforeInMillis);
      taskScheduler.schedule(() -> {
        logger.info("Sending 'Reminder in 1 hour' notification");
        notificationService.sendNotification(title, "Reminder in 1 hour");
      }, Instant.ofEpochMilli(oneHourBeforeInMillis));
    }

    if (timeInMillis > currentTimeMillis) {
      logger.info("Scheduling main notification for: " + timeInMillis);
      taskScheduler.schedule(() -> {
        logger.info("Sending main notification");
        notificationService.sendNotification(title, description);
      }, Instant.ofEpochMilli(timeInMillis));
    }
  }
}