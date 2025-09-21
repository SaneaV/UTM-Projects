package org.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ResponseConsumer {

    private final BlockingQueue<AggregationResponse> responses = new ArrayBlockingQueue<>(1);

    @KafkaListener(topics = "residents-responses", groupId = "aggregation-service")
    public void consume(AggregationResponse response) {
      responses.offer(response);
    }

    public AggregationResponse getResponse(long timeoutMillis) throws InterruptedException {
      return responses.poll(timeoutMillis, TimeUnit.MILLISECONDS);
    }
  }
