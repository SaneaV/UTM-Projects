package org.example;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

  @Bean
  public NewTopic requestsTopic() {
    return new NewTopic("residents-requests", 3, (short) 1);
  }

  @Bean
  public NewTopic responsesTopic() {
    return new NewTopic("residents-responses", 3, (short) 1);
  }
}
