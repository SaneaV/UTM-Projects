package algorithm;

import static java.util.Arrays.asList;

import java.util.List;

public class AlgorithmFactory {

  private static final List<Algorithm> algorithms = asList(new RC4(), new XOR(), new Atbash(), new Vigenere());

  public Algorithm getAlgorithm(String algorithmName) {
    return algorithms.stream()
        .filter(algorithm -> algorithm.getName().equalsIgnoreCase(algorithmName))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Algorithm not found"));
  }
}
