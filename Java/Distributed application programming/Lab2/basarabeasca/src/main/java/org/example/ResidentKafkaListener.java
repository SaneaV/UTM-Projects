package org.example;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResidentKafkaListener {

  private final ResidentRepository repository;
  private final KafkaTemplate<String, AggregationResponse> kafkaTemplate;

  public ResidentKafkaListener(ResidentRepository repository,
      KafkaTemplate<String, AggregationResponse> kafkaTemplate) {
    this.repository = repository;
    this.kafkaTemplate = kafkaTemplate;
  }

  @KafkaListener(topics = "residents-requests", groupId = "basarabeasca-service")
  public void listen(AggregationRequest request) {
    String city = request.getCity();
    String role = request.getRole();

    if (!"basarabeasca".equalsIgnoreCase(city)) {
      return;
    }
    if ("super-admin".equalsIgnoreCase(role)) {
      return;
    }

    if (!isAuthorized(city, role)) {
      AggregationResponse response = new AggregationResponse(false, "Access denied: insufficient role", null);
      kafkaTemplate.send("residents-responses", response);
      return;
    }

    AggregationResponse response;
    Map<String, Object> data = new HashMap<>();

    try {
      switch (request.getAction()) {
        case "findEntity" -> {
          Map<String, Object> payload = request.getPayload();
          if (payload != null && payload.get("id") != null) {
            Long id = Long.valueOf(payload.get("id").toString());
            Resident r = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("org.example.Resident not found with id " + id));
            data.put("resident", r);
          } else {
            data.put("residents", repository.findAll());
          }
          response = new AggregationResponse(true, "Residents found", data);
        }
        case "createEntity" -> {
          var payload = request.getPayload();
          Resident r = new Resident();
          r.setFirstName((String) payload.get("firstName"));
          r.setLastName((String) payload.get("lastName"));
          r.setBirthDate(LocalDate.parse((String) payload.get("birthDate")));
          r.setPhoneNumber((String) payload.get("phoneNumber"));
          r.setPassportNumber((String) payload.get("passportNumber"));
          repository.save(r);
          data.put("resident", r);
          response = new AggregationResponse(true, "org.example.Resident created", data);
        }
        case "updateEntity" -> {
          var payload = request.getPayload();
          Long id = Long.valueOf(payload.get("id").toString());
          Resident r = repository.findById(id)
              .orElseThrow(() -> new RuntimeException("org.example.Resident not found with id " + id));
          r.setFirstName((String) payload.get("firstName"));
          r.setLastName((String) payload.get("lastName"));
          r.setBirthDate(LocalDate.parse((String) payload.get("birthDate")));
          r.setPhoneNumber((String) payload.get("phoneNumber"));
          r.setPassportNumber((String) payload.get("passportNumber"));
          repository.save(r);
          data.put("resident", r);
          response = new AggregationResponse(true, "org.example.Resident updated", data);
        }
        case "deleteEntity" -> {
          Long id = Long.valueOf(request.getPayload().get("id").toString());
          repository.deleteById(id);
          data.put("deletedId", id);
          response = new AggregationResponse(true, "org.example.Resident deleted", data);
        }
        default -> response = new AggregationResponse(false, "Unknown action", null);
      }
    } catch (Exception e) {
      response = new AggregationResponse(false, e.getMessage(), null);
    }

    kafkaTemplate.send("residents-responses", response);
  }

  private boolean isAuthorized(String city, String role) {
    if (role != null && role.startsWith("mayor_")) {
      String mayorCity = role.substring(6);
      return mayorCity.equalsIgnoreCase(city);
    }
    return false;
  }
}
