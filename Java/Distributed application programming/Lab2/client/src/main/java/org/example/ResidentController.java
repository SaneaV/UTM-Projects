package org.example;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

  private final RestTemplate restTemplate = new RestTemplate();

  @Value("${aggregation.service.url}")
  private String aggregationServiceUrl;

  private Map<String, Object> buildRequest(String action,
      String object,
      Object data,
      String city,
      String role) {
    Map<String, Object> body = new HashMap<>();
    body.put("action", action);
    body.put("object", object);
    body.put("city", city);
    body.put("role", role);
    if (data != null) {
      body.put("payload", data);
    }
    return body;
  }

  private ResponseEntity<String> sendToAggregation(Map<String, Object> requestBody) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

    return restTemplate.exchange(
        aggregationServiceUrl + "/aggregation",
        HttpMethod.POST,
        entity,
        String.class
    );
  }

  private boolean isAuthorized(String city, String role) {
    if ("super-admin".equals(role)) {
      return true;
    }

    if (role.startsWith("mayor_")) {
      String mayorCity = role.substring(6);
      return mayorCity.equalsIgnoreCase(city);
    }

    return false;
  }


  @GetMapping
  public ResponseEntity<String> getResidents(@RequestHeader("X-City") String city,
      @RequestHeader("X-Role") String role) {
    if (!isAuthorized(city, role)) {
      return ResponseEntity.status(FORBIDDEN).body("Access denied");
    }

    Map<String, Object> req = buildRequest("findEntity", "resident", null, city, role);
    return sendToAggregation(req);
  }

  @GetMapping("/{id}")
  public ResponseEntity<String> getResident(@RequestHeader("X-City") String city,
      @RequestHeader("X-Role") String role,
      @PathVariable Long id) {
    if (!isAuthorized(city, role)) {
      return ResponseEntity.status(FORBIDDEN).body("Access denied");
    }

    Map<String, Object> data = Map.of("id", id);
    Map<String, Object> req = buildRequest("findEntity", "resident", data, city, role);
    return sendToAggregation(req);
  }

  @PostMapping
  public ResponseEntity<String> addResident(@RequestHeader("X-City") String city,
      @RequestHeader("X-Role") String role,
      @RequestBody Map<String, Object> residentData) {
    if (!isAuthorized(city, role)) {
      return ResponseEntity.status(FORBIDDEN).body("Access denied");
    }

    Map<String, Object> req = buildRequest("createEntity", "resident", residentData, city, role);
    return sendToAggregation(req);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateResident(@RequestHeader("X-City") String city,
      @RequestHeader("X-Role") String role,
      @PathVariable Long id,
      @RequestBody Map<String, Object> residentData) {
    if (!isAuthorized(city, role)) {
      return ResponseEntity.status(FORBIDDEN).body("Access denied");
    }

    residentData.put("id", id);
    Map<String, Object> req = buildRequest("updateEntity", "resident", residentData, city, role);
    return sendToAggregation(req);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteResident(@RequestHeader("X-City") String city,
      @RequestHeader("X-Role") String role,
      @PathVariable Long id) {
    if (!isAuthorized(city, role)) {
      return ResponseEntity.status(FORBIDDEN).body("Access denied");
    }

    Map<String, Object> data = Map.of("id", id);
    Map<String, Object> req = buildRequest("deleteEntity", "resident", data, city, role);
    return sendToAggregation(req);
  }
}
