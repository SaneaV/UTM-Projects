package org.example;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AggregationRequest {


  private final String action;
  private final String object;
  private final String city;
  private final String role;
  private final Map<String, Object> payload;
}