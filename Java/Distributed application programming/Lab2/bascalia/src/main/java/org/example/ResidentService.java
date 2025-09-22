package org.example;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResidentService {

  private final ResidentRepository repository;

  public AggregationResponse handle(AggregationRequest request) {
    Map<String, Object> data = new HashMap<>();

    return switch (request.getAction()) {
      case "findEntity" -> {
        var payload = request.getPayload();
        if (payload != null && payload.get("id") != null) {
          Long id = Long.valueOf(payload.get("id").toString());
          Resident r = repository.findById(id)
              .orElseThrow(() -> new RuntimeException("Resident not found with id " + id));
          data.put("resident", r);
        } else {
          data.put("residents", repository.findAll());
        }
        yield new AggregationResponse(true, "Residents found", data);
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
        yield new AggregationResponse(true, "Resident created", data);
      }
      case "updateEntity" -> {
        var payload = request.getPayload();
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
        yield new AggregationResponse(true, "Resident updated", data);
      }
      case "deleteEntity" -> {
        Long id = Long.valueOf(request.getPayload().get("id").toString());
        repository.deleteById(id);
        data.put("deletedId", id);
        yield new AggregationResponse(true, "Resident deleted", data);
      }
      default -> new AggregationResponse(false, "Unknown action", null);
    };
  }
}
