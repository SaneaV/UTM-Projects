package game;

import character.Character;
import factory.ArcherFactory;
import factory.CharacterFactory;
import factory.MageFactory;
import factory.WarriorFactory;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class GameWorld {

  private static volatile GameWorld instance;
  private final ReentrantLock lock = new ReentrantLock();
  private final List<Character> characters = new CopyOnWriteArrayList<>();
  private final List<Player> players = new CopyOnWriteArrayList<>();
  private final Random random = new Random();

  private GameWorld() {

  }

  public static GameWorld getInstance() {
    if (instance == null) {
      synchronized (GameWorld.class) {
        if (instance == null) {
          instance = new GameWorld();
        }
      }
    }
    return instance;
  }

  private void addCharacters() {
    CharacterFactory warriorFactory = new WarriorFactory();
    CharacterFactory mageFactory = new MageFactory();
    CharacterFactory archerFactory = new ArcherFactory();

    CharacterFactory[] factories = {warriorFactory, mageFactory, archerFactory};

    final int numberOfCharacters = random.nextInt(2) + 1; // Generates 1 or 2

    for (int i = 0; i < numberOfCharacters; i++) {
      final int characterType = random.nextInt(3); // Generates 0 (Warrior), 1 (Mage), or 2 (Archer)
      final int characterLevel = random.nextInt(3); // Generates 0 (Weak), 1 (Medium), or 2 (Strong)

      CharacterFactory factory = factories[characterType];

      switch (characterLevel) {
        case 0 -> addCharacter(factory.createWeakCharacter());
        case 1 -> addCharacter(factory.createMediumCharacter());
        case 2 -> addCharacter(factory.createStrongCharacter());
      }
    }
  }

  public void addCharacter(Character character) {
    lock.lock();
    try {
      characters.add(character);
      System.out.println("Added character: " + character.getName());
    } finally {
      lock.unlock();
    }
  }

  public void addPlayer(Player player) {
    lock.lock();
    try {
      players.add(player);
      addCharacters();
      System.out.println("Player connected: " + player.getName());
    } finally {
      lock.unlock();
    }
  }

  public void disconnectPlayer(String name) {
    lock.lock();
    try {
      players.removeIf(player -> player.getName().equalsIgnoreCase(name));
    } finally {
      lock.unlock();
    }
  }

  public void simulateCombat() {
    lock.lock();
    try {
      players.forEach(player -> {
        if (!characters.isEmpty()) {
          Character target = characters.get(ThreadLocalRandom.current().nextInt(characters.size()));
          player.attack(target);
        }
      });
      characters.removeIf(c -> {
        final boolean isDead = c.getHealth().get() <= 0;
        if (isDead) {
          System.out.println("Character " + c.getName() + " is dead");
        }
        return isDead;
      });
    } finally {
      lock.unlock();
    }
  }

  public List<Character> getCharacters() {
    return this.characters;
  }

  public int getNumberOfPlayers() {
    return this.players.size();
  }
}
