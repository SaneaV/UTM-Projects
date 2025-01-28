package factory;

import character.Character;
import character.Mage;

public class MageFactory implements CharacterFactory {

  @Override
  public Character createWeakCharacter() {
    return new Mage.Builder()
        .setName("Novice Mage")
        .setLevel(1)
        .setClan("Wizard")
        .setStaff("Wooden Staff")
        .build();
  }

  @Override
  public Character createMediumCharacter() {
    return new Mage.Builder()
        .setName("Experienced Mage")
        .setLevel(10)
        .setClan("Wizard")
        .setRarity("Uncommon")
        .setStaff("Elder Staff")
        .setMana(50)
        .build();
  }

  @Override
  public Character createStrongCharacter() {
    return new Mage.Builder()
        .setName("Archmage")
        .setLevel(20)
        .setClan("Wizard")
        .setRarity("Legendary")
        .setStaff("Crystal Staff")
        .setMana(100)
        .setPet("Dragon")
        .build();
  }
}
