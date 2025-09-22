package org.example;

import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AggregationResponder {

  private final KafkaTemplate<String, AggregationResponse> kafkaTemplate;

  public void send(Supplier<AggregationResponse> supplier) {
    AggregationResponse response;
    try {
      response = supplier.get();
    } catch (Exception e) {
      response = new AggregationResponse(false, "Error: " + e.getMessage(), null);
    }
    kafkaTemplate.send("residents-responses", response);
  }
}
