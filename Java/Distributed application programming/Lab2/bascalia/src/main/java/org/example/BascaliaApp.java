package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
    org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration.class
})
public class BascaliaApp {

  public static void main(String[] args) {
    SpringApplication.run(BascaliaApp.class, args);
  }
}
