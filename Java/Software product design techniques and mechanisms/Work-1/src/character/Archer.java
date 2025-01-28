package character;

public class Archer extends Character {

  private final Integer arrows;
  private final Double accuracy;
  private final String companion;

  private Archer(Builder builder) {
    super(builder.name, builder.level, builder.clan, builder.rarity, "Bow");
    this.arrows = builder.arrows;
    this.accuracy = builder.accuracy;
    this.companion = builder.companion;
  }

  @Override
  public void displayInfo() {
    System.out.println("characters.Archer: " + name + " (Level: " + level + ")");
    System.out.println("Clan: " + clan + ", Rarity: " + rarity);
    System.out.println("Weapon: " + weapon);
    if (arrows != null) {
      System.out.println("Arrows: " + arrows);
    }
    if (accuracy != null) {
      System.out.println("Accuracy: " + accuracy);
    }
    if (companion != null) {
      System.out.println("Companion: " + companion);
    }
    System.out.println("Health: " + health.get());
  }

  public static class Builder {

    private String name;
    private int level = 1;
    private String clan = "Neutral";
    private String rarity = "Common";
    private Integer arrows;
    private Double accuracy;
    private String companion;

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setLevel(int level) {
      this.level = level;
      return this;
    }

    public Builder setClan(String clan) {
      this.clan = clan;
      return this;
    }

    public Builder setRarity(String rarity) {
      this.rarity = rarity;
      return this;
    }

    public Builder setArrows(Integer arrows) {
      this.arrows = arrows;
      return this;
    }

    public Builder setAccuracy(Double accuracy) {
      this.accuracy = accuracy;
      return this;
    }

    public Builder setCompanion(String companion) {
      this.companion = companion;
      return this;
    }

    public Archer build() {
      if (arrows == null) {
        arrows = level * 10;
      }
      if (accuracy == null) {
        accuracy = 0.75 + (level * 0.01);
      }
      return new Archer(this);
    }
  }
}
