package game;

import java.util.Random;

import character.Character;

public class Player {

  private final String name;

  public Player(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void attack(Character target) {
    int damage = new Random().nextInt(15) + 5;
    target.getHealth().addAndGet(-damage);
    System.out.printf("%s attacks %s for %d damage!%n", name, target.getName(), damage);
  }
}
