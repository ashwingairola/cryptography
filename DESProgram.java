import java.io.*;
import java.security.*;
import javax.crypto.*;

public class DESProgram
{
  private static Cipher encryptCipher, decryptCipher;

  public static void main(String[] args)
  {
    try
    {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      System.out.println("Enter a plaintext message: ");
      String plaintext = br.readLine();

      // Generating a DES-format key
      KeyGenerator keygen = KeyGenerator.getInstance("DES");
      SecretKey key = keygen.generateKey();

      // Instantiating a DES encryption cipher
      encryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
      // Set the key and the mode (encryption) to the cipher
      encryptCipher.init(Cipher.ENCRYPT_MODE, key);
      // Send the plaintext to user-defined encryption method
      byte[] encryptedData = encryptData(plaintext);

      // Instantiating a DES decryption cipher
      decryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
      // Set the key and the mode (decryption) to the cipher
      decryptCipher.init(Cipher.DECRYPT_MODE, key);
      // Send the encrypted data to user-defined decryption method
      decryptData(encryptedData);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  private static byte[] encryptData(String data)
    throws IllegalBlockSizeException, BadPaddingException
  {
    System.out.println("Data before encryption:\n" + data);

    // First convert the string to bytes
    byte[] dataToEncrypt = data.getBytes();
    // Call the Cipher.doFinal method to encrypt the data using DES
    byte[] encryptedData = encryptCipher.doFinal(dataToEncrypt);

    System.out.println("Data after encryption:\n" + encryptedData);

    return encryptedData;
  }

  private static void decryptData(byte[] data)
    throws IllegalBlockSizeException, BadPaddingException
  {
    byte[] decryptedData = decryptCipher.doFinal(data);
    System.out.println("Data after decryption:\n" + new String(decryptedData));
  }
}
