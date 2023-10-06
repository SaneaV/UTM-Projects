package algorithm;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class AlgorithmFactory {

  private final List<Algorithm> algorithms;
  private Map<String, Algorithm> algorithmByName;

  public AlgorithmFactory(Algorithm... algorithms) {
    this.algorithms = Arrays.stream(algorithms)
        .collect(toList());
    init();
  }

  private void init() {
    algorithmByName = algorithms.stream()
        .collect(toMap(Algorithm::getName, Function.identity()));
  }

  public Algorithm getAlgorithm(String algorithmName) {
    if (algorithmByName.get(algorithmName) == null) {
      throw new RuntimeException("Algorithm not found");
    }
    return algorithmByName.get(algorithmName);
  }
}
