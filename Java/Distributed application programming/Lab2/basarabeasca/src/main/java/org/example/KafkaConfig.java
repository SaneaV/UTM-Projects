package org.example;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConfig {

  @Bean
  public ConsumerFactory<String, AggregationRequest> consumerFactory() {
    JsonDeserializer<AggregationRequest> deserializer = new JsonDeserializer<>(AggregationRequest.class);
    deserializer.addTrustedPackages("org.example");
    deserializer.setRemoveTypeHeaders(false);

    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "basarabeasca-service");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, AggregationRequest> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, AggregationRequest> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }
}
