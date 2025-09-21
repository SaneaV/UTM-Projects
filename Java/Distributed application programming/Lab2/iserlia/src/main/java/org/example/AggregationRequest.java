package org.example;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AggregationRequest {


  private String action;
  private String object;
  private String city;
  private String role;
  private Map<String, Object> payload;
}
