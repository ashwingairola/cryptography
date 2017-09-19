import java.io.*;

class CaesarCipher
{
  public static String encrypt(String plaintext, int offset)
  {
    StringBuilder ciphertext = new StringBuilder();
    for(int i=0; i<plaintext.length(); ++i)
    {
      char ch = plaintext.charAt(i);
      if(!Character.isLetter(ch))
      {
        continue;
      }
      ciphertext.append((char)((((ch - 65) + offset) % 26) + 65));
    }
    return ciphertext.toString();
  }

  public static String decrypt(String ciphertext, int offset)
  {
    StringBuilder plaintext = new StringBuilder();
    for(int i=0; i<ciphertext.length(); ++i)
    {
      char ch = ciphertext.charAt(i);
      if(!Character.isLetter(ch))
      {
        continue;
      }
      plaintext.append((char)((((ch - 65) - offset) % 26) + 65));
    }
    return plaintext.toString();
  }
}

public class CaesarCipherProgram
{
  public static void main(String[] args) throws IOException
  {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    System.out.println("Enter a plaintext string:");
    String plaintext = br.readLine();

    System.out.println("Enter an integer offset:");
    int offset = Integer.parseInt(br.readLine());

    System.out.println("The ciphertext after encryption is:");
    String ciphertext = CaesarCipher.encrypt(plaintext.toUpperCase(), offset);
    System.out.println(ciphertext);

    System.out.println("The plaintext after decryption is:");
    System.out.println(CaesarCipher.decrypt(ciphertext.toUpperCase(), offset));
  }
}
