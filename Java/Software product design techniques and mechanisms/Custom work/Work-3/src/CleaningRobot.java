class CleaningRobot {

  private CleaningStrategy strategy;

  public void setStrategy(CleaningStrategy strategy) {
    this.strategy = strategy;
  }

  public void clean() {
    if (strategy != null) {
      strategy.clean();
    }
  }
}