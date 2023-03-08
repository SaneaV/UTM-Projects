package algorithm;

public class XOR implements Algorithm {

  private static final String XOR = "XOR";

  @Override
  public String encrypt(byte[] plaintext, byte[] key) {
    byte[] ciphertext = new byte[plaintext.length];
    for (int i = 0; i < plaintext.length; i++) {
      ciphertext[i] = (byte) (plaintext[i] ^ key[i % key.length]);
    }
    return bytesToHex(ciphertext);
  }

  @Override
  public String decrypt(String cipherText, byte[] key) {
    return hexToUtf8(encrypt(hexToBytes(cipherText), key));
  }

  @Override
  public String getName() {
    return XOR;
  }
}
