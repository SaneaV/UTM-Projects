package com.mobile.notification;

public class NotificationRequest {
  private String title;
  private String description;
  private long timeInMillis;

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

  public long getTimeInMillis() {
    return timeInMillis;
  }

  public void setTimeInMillis(long timeInMillis) {
    this.timeInMillis = timeInMillis;
  }

  @Override
  public String toString() {
    return "NotificationRequest{" +
        "title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", timeInMillis=" + timeInMillis +
        '}';
  }
}