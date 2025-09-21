package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AggregationService {

  private final RequestProducer requestProducer;
  private final ResponseConsumer responseConsumer;

  public AggregationResponse processRequest(AggregationRequest request) {
    requestProducer.sendRequest(request);
    try {
      Thread.sleep(1000);
      return responseConsumer.getResponse(1000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return new AggregationResponse(false, "Timeout waiting for response", null);
    }
  }
}
