package character;

public class Mage extends Character {

  private final String staff;
  private final Integer mana;
  private final String pet;

  private Mage(Builder builder) {
    super(builder.name, builder.level, builder.clan, builder.rarity, "Staff");
    this.staff = builder.staff;
    this.mana = builder.mana;
    this.pet = builder.pet;
  }

  @Override
  public void displayInfo() {
    System.out.println("Mage: " + name + " (Level: " + level + ")");
    System.out.println("Clan: " + clan + ", Rarity: " + rarity);
    System.out.println("Staff: " + staff);
    if (mana != null) {
      System.out.println("Mana: " + mana);
    }
    if (pet != null) {
      System.out.println("Pet: " + pet);
    }
    System.out.println("Health: " + health.get());
  }

  public static class Builder {

    private String name;
    private int level = 1;
    private String clan = "Neutral";
    private String rarity = "Common";
    private String staff = "Basic Staff";
    private Integer mana;
    private String pet;

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

    public Builder setStaff(String staff) {
      this.staff = staff;
      return this;
    }

    public Builder setMana(Integer mana) {
      this.mana = mana;
      return this;
    }

    public Builder setPet(String pet) {
      this.pet = pet;
      return this;
    }

    public Mage build() {
      if (mana == null) {
        mana = level * 10;
      }
      return new Mage(this);
    }
  }
}
