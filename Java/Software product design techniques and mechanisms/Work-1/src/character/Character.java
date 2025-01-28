package character;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Character {

  protected String name;
  protected int level;
  protected String clan;
  protected String rarity;
  protected String weapon;
  protected AtomicInteger health = new AtomicInteger(100);

  public Character(String name, int level, String clan, String rarity, String weapon) {
    this.name = name;
    this.level = level;
    this.clan = clan;
    this.rarity = rarity;
    this.weapon = weapon;
  }

  public String getName() {
    return name;
  }

  public AtomicInteger getHealth() {
    return health;
  }

  public abstract void displayInfo();
}
