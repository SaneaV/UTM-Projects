package org.example;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AggregationResponse {

  private boolean success;
  private String message;
  private Map<String, Object> data;
}
