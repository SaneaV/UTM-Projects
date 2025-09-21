package org.example;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AggregationResponse {


  private boolean success;
  private String message;
  private Map<String, Object> data;
}