package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
    org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration.class
})
public class AdministrationApp {

  public static void main(String[] args) {
    SpringApplication.run(AdministrationApp.class, args);
  }
}
