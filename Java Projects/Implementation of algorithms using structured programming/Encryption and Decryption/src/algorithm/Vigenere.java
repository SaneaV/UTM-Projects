package algorithm;

public class Vigenere implements Algorithm {

  private static final String VIGENERE = "Vigenere";

  @Override
  public String encrypt(byte[] plainText, byte[] key) {
    final StringBuilder cipherText = new StringBuilder();
    final String str = LowerToUpper(new String(plainText));
    final String keyword = LowerToUpper(new String(key));
    final String generatedKey = generateKey(str, keyword);

    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (Character.isLetter(c)) {
        int x = (c + generatedKey.charAt(i)) % 26;
        x += 'A';
        cipherText.append((char) (x));
      } else {
        cipherText.append(c);
      }
    }
    return cipherText.toString();
  }

  @Override
  public String decrypt(String cipherText, byte[] key) {
    final StringBuilder originalText = new StringBuilder();
    final String keyword = LowerToUpper(new String(key));
    cipherText = LowerToUpper(cipherText);
    final String generatedKey = generateKey(cipherText, keyword);

    for (int i = 0; i < cipherText.length() && i < generatedKey.length(); i++) {
      char c = cipherText.charAt(i);
      if (Character.isLetter(c)) {
        int x = (c - generatedKey.charAt(i) + 26) % 26;
        x += 'A';
        originalText.append((char) (x));
      } else {
        originalText.append(c);
      }
    }
    return originalText.toString();
  }

  @Override
  public String getName() {
    return VIGENERE;
  }

  static String generateKey(String str, String key) {
    int x = str.length();

    StringBuilder keyBuilder = new StringBuilder(key);
    for (int i = 0; ; i++) {
      if (x == i) {
        i = 0;
      }
      if (keyBuilder.length() == str.length()) {
        break;
      }
      keyBuilder.append(keyBuilder.charAt(i));
    }
    key = keyBuilder.toString();
    return key;
  }

  static String LowerToUpper(String s) {
    StringBuilder str = new StringBuilder(s);
    for (int i = 0; i < s.length(); i++) {
      if (Character.isLowerCase(s.charAt(i))) {
        str.setCharAt(i, Character.toUpperCase(s.charAt(i)));
      }
    }
    s = str.toString();
    return s;
  }
}
