package algorithm;

public class RC4 implements Algorithm {

  private static final String RC4 = "RC4";
  private final byte[] S = new byte[256];

  @Override
  public String encrypt(byte[] plainText, byte[] key) {
    for (int k = 0; k < 256; k++) {
      S[k] = (byte) k;
    }

    for (int k = 0, t = 0; k < 256; k++) {
      t = (t + S[k] + key[k % key.length]) & 0xFF;
      swap(S, k, t);
    }

    byte[] ciphertext = new byte[plainText.length];
    for (int k = 0, i = 0, j = 0; k < plainText.length; k++) {
      i = (i + 1) & 0xFF;
      j = (j + S[i]) & 0xFF;
      swap(S, i, j);
      ciphertext[k] = (byte) (plainText[k] ^ S[(S[i] + S[j]) & 0xFF]);
    }
    return bytesToHex(ciphertext);
  }

  @Override
  public String decrypt(String cipherText, byte[] key) {
    return hexToUtf8(encrypt(hexToBytes(cipherText), key));
  }

  @Override
  public String updateKey(String key) {
    return key;
  }

  @Override
  public String getName() {
    return RC4;
  }

  private void swap(byte[] S, int i, int j) {
    byte temp = S[i];
    S[i] = S[j];
    S[j] = temp;
  }
}
