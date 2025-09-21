package org.example;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RequestProducer {

  private final KafkaTemplate<String, AggregationRequest> kafkaTemplate;

  public RequestProducer(KafkaTemplate<String, AggregationRequest> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendRequest(AggregationRequest request) {
    kafkaTemplate.send("residents-requests", request.getCity(), request);
  }
}
