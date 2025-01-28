package factory;

import character.Archer;
import character.Character;

public class ArcherFactory implements CharacterFactory {

  @Override
  public Character createWeakCharacter() {
    return new Archer.Builder()
        .setName("Novice Archer")
        .setLevel(1)
        .setClan("Elf")
        .setArrows(10)
        .build();
  }

  @Override
  public Character createMediumCharacter() {
    return new Archer.Builder()
        .setName("Expert Archer")
        .setLevel(10)
        .setClan("Elf")
        .setRarity("Uncommon")
        .setArrows(50)
        .setAccuracy(0.9)
        .build();
  }

  @Override
  public Character createStrongCharacter() {
    return new Archer.Builder()
        .setName("Master Archer")
        .setLevel(20)
        .setClan("Elf")
        .setRarity("Legendary")
        .setArrows(100)
        .setAccuracy(0.95)
        .setCompanion("Phoenix")
        .build();
  }
}
