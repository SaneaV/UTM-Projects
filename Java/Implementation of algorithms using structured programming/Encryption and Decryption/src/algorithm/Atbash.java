package algorithm;

import java.util.stream.Collectors;

public class Atbash implements Algorithm {

  private static final String ATBASH = "Atbash";
  private static final String EMPTY = "";
  private static final String[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split(EMPTY);

  @Override
  public String encrypt(byte[] plainText, byte[] key) {
    final String input = new String(plainText);
    final String stringKey = new String(key);
    final int keyLength = stringKey.length() - 1;
    final StringBuilder result = new StringBuilder();
    for (char ch : input.toCharArray()) {
      if (Character.isLetter(ch)) {
        int index = stringKey.indexOf(Character.toUpperCase(ch));
        if (index != -1) {
          char newChar = stringKey.charAt(keyLength - index);
          if (Character.isLowerCase(ch)) {
            newChar = Character.toLowerCase(newChar);
          }
          result.append(newChar);
        }
      } else {
        result.append(ch);
      }
    }
    return result.toString();
  }

  @Override
  public String decrypt(String cipherText, byte[] key) {
    return encrypt(cipherText.getBytes(), new StringBuilder(new String(key)).reverse().toString().getBytes());
  }

  @Override
  public String updateKey(String key) {
    String formattedKey = key
        .replaceAll("[^a-zA-Z]+", EMPTY)
        .toUpperCase()
        .chars().mapToObj(c -> (char) c)
        .distinct()
        .map(Object::toString)
        .collect(Collectors.joining());

    if (formattedKey.length() % 2 != 0) {
      formattedKey += getLetter(formattedKey);
    }

    return formattedKey;
  }

  private String getLetter(String key) {
    for (String letter : ALPHABET) {
      if (!key.contains(letter)) {
        return letter;
      }
    }
    throw new RuntimeException("Wrong key");
  }

  @Override
  public String getName() {
    return ATBASH;
  }
}
