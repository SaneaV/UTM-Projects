package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResidentKafkaListener {

  private final ResidentService residentService;
  private final AggregationResponder responder;

  @KafkaListener(topics = "residents-requests", groupId = "central-service")
  public void listen(AggregationRequest request) {
    if (!"super-admin".equalsIgnoreCase(request.getRole())) {
      return;
    }

    responder.send(() -> residentService.handle(request));
  }
}