package org.example;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.example.domain.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResidentKafkaListener {

  private final RepositoryRouter repositoryRouter;
  private final KafkaTemplate<String, AggregationResponse> kafkaTemplate;

  public ResidentKafkaListener(RepositoryRouter repositoryRouter,
      KafkaTemplate<String, AggregationResponse> kafkaTemplate) {
    this.repositoryRouter = repositoryRouter;
    this.kafkaTemplate = kafkaTemplate;
  }

  @KafkaListener(topics = "residents-requests", groupId = "central-service")
  public void listen(AggregationRequest request) {
    if (!"super-admin".equalsIgnoreCase(request.getRole())) {
      return;
    }
    String city = request.getCity();

    AggregationResponse response;
    Map<String, Object> data = new HashMap<>();

    final JpaRepository<Resident, Long> repository = repositoryRouter.getRepository(city);
    if (repository == null) {
      response = new AggregationResponse(false, "Unknown city: " + city, null);
      kafkaTemplate.send("residents-responses", response);
      return;
    }

    try {
      Map<String, Object> payload = request.getPayload();

      switch (request.getAction()) {
        case "findEntity" -> {
          if (payload != null && payload.get("id") != null) {
            Long id = Long.valueOf(payload.get("id").toString());
            Resident r = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resident not found with id " + id));
            data.put("resident", r);
          } else {
            data.put("residents", repository.findAll());
          }
          response = new AggregationResponse(true, "Residents found", data);
        }

        case "createEntity" -> {
          Resident r = new Resident();
          r.setFirstName((String) payload.get("firstName"));
          r.setLastName((String) payload.get("lastName"));
          r.setBirthDate(LocalDate.parse((String) payload.get("birthDate")));
          r.setPhoneNumber((String) payload.get("phoneNumber"));
          r.setPassportNumber((String) payload.get("passportNumber"));
          repository.save(r);
          data.put("resident", r);
          response = new AggregationResponse(true, "Resident created", data);
        }

        case "updateEntity" -> {
          Long id = Long.valueOf(payload.get("id").toString());
          Resident r = repository.findById(id)
              .orElseThrow(() -> new RuntimeException("Resident not found with id " + id));
          r.setFirstName((String) payload.get("firstName"));
          r.setLastName((String) payload.get("lastName"));
          r.setBirthDate(LocalDate.parse((String) payload.get("birthDate")));
          r.setPhoneNumber((String) payload.get("phoneNumber"));
          r.setPassportNumber((String) payload.get("passportNumber"));
          repository.save(r);
          data.put("resident", r);
          response = new AggregationResponse(true, "Resident updated", data);
        }

        case "deleteEntity" -> {
          Long id = Long.valueOf(payload.get("id").toString());
          repository.deleteById(id);
          data.put("deletedId", id);
          response = new AggregationResponse(true, "Resident deleted", data);
        }

        default ->
            response = new AggregationResponse(false, "Unknown action: " + request.getAction(), null);
      }

    } catch (Exception e) {
      response = new AggregationResponse(false, "Error: " + e.getMessage(), null);
    }

    kafkaTemplate.send("residents-responses", response);
  }
}
