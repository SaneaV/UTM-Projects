package character;

public class Warrior extends Character {

  private final String armor;
  private final Integer strength;
  private final String ability;

  private Warrior(Builder builder) {
    super(builder.name, builder.level, builder.clan, builder.rarity, "Sword");
    this.armor = builder.armor;
    this.strength = builder.strength;
    this.ability = builder.ability;
  }

  @Override
  public void displayInfo() {
    System.out.println("Warrior: " + name + " (Level: " + level + ")");
    System.out.println("Clan: " + clan + ", Rarity: " + rarity);
    System.out.println("Weapon: " + weapon + ", Armor: " + armor);
    if (strength != null) {
      System.out.println("Strength: " + strength);
    }
    if (ability != null) {
      System.out.println("Ability: " + ability);
    }
    System.out.println("Health: " + health.get());
  }

  public static class Builder {

    private String name;
    private int level = 1;
    private String clan = "Neutral";
    private String rarity = "Common";
    private String armor = "Leather";
    private Integer strength;
    private String ability;

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

    public Builder setArmor(String armor) {
      this.armor = armor;
      return this;
    }

    public Builder setStrength(Integer strength) {
      this.strength = strength;
      return this;
    }

    public Builder setAbility(String ability) {
      this.ability = ability;
      return this;
    }

    public Warrior build() {
      if (strength == null) {
        strength = level * 5;
      }
      return new Warrior(this);
    }
  }
}
