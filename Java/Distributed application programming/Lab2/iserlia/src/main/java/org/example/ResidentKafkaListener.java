package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResidentKafkaListener {

  private final AccessPolicy accessPolicy;
  private final ResidentService residentService;
  private final AggregationResponder responder;

  @KafkaListener(topics = "residents-requests", groupId = "iserlia-service")
  public void listen(AggregationRequest request) {
    if (!accessPolicy.isCityAllowed(request.getCity(), request.getRole())) {
      return;
    }

    if (!accessPolicy.isAuthorized(request.getCity(), request.getRole())) {
      responder.sendError("Access denied: insufficient role");
      return;
    }

    responder.send(() -> residentService.handle(request));
  }
}
