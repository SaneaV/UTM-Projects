package factory;

import character.Warrior;
import character.Character;

public class WarriorFactory implements CharacterFactory {

  @Override
  public Character createWeakCharacter() {
    return new Warrior.Builder()
        .setName("Grunt")
        .setLevel(1)
        .setClan("Orcs")
        .setArmor("Rusty Armor")
        .build();
  }

  @Override
  public Character createMediumCharacter() {
    return new Warrior.Builder()
        .setName("Veteran Warrior")
        .setLevel(10)
        .setClan("Human")
        .setRarity("Uncommon")
        .setArmor("Chainmail")
        .setStrength(50)
        .build();
  }

  @Override
  public Character createStrongCharacter() {
    return new Warrior.Builder()
        .setName("Heroic Warrior")
        .setLevel(20)
        .setClan("Human")
        .setRarity("Legendary")
        .setArmor("Plate Mail")
        .setStrength(80)
        .setAbility("Mighty Swing")
        .build();
  }
}
