package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aggregation")
public class AggregationController {

  private final AggregationService aggregationService;

  public AggregationController(AggregationService aggregationService) {
    this.aggregationService = aggregationService;
  }

  @PostMapping
  public ResponseEntity<AggregationResponse> handleRequest(@RequestBody AggregationRequest request) {
    return ResponseEntity.ok(aggregationService.processRequest(request));
  }
}
