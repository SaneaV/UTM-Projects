package factory;

import character.Character;

public interface CharacterFactory {

  Character createWeakCharacter();

  Character createMediumCharacter();

  Character createStrongCharacter();
}
