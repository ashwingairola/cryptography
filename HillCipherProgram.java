import java.io.*;
import java.util.Scanner;

class HillCipher
{
  public static String encrypt(String plaintext, int[][] key)
  {
    StringBuilder ciphertext = new StringBuilder();
    StringBuilder paddedPlaintext = new StringBuilder();
    int beg = 0, end = 2;

    for(int i=0; i<plaintext.length(); ++i)
    {
      char ch = plaintext.charAt(i);
      if(Character.isLetter(ch))
      {
        paddedPlaintext.append(ch);
      }
    }

    int originalLength = paddedPlaintext.length();

    while(paddedPlaintext.length() % 3 != 0)
    {
      paddedPlaintext.append('@');
    }

    while(end < paddedPlaintext.length())
    {
      ciphertext.append(encipher(paddedPlaintext.substring(beg, end+1), key));
      beg = end + 1;
      end = beg + key.length - 1;
    }

    for(int i=ciphertext.length()-1; ciphertext.length() > originalLength; --i)
    {
      ciphertext.delete(i, i+1);
    }

    return ciphertext.toString();
  }

  private static String encipher(String sub, int[][] key)
  {
    String ciphersub = "";
    for(int i=0; i<key.length; ++i)
    {
      int sum = 0;
      for(int j=0; j<key[i].length; ++j)
      {
        sum += key[i][j] * (sub.charAt(j)-65);
      }
      ciphersub += (char)(sum % 26 + 65);
    }
    return ciphersub;
  }
}

public class HillCipherProgram
{
  public static void main(String[] args) throws IOException
  {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Scanner sc = new Scanner(System.in);

    System.out.println("Enter a plaintext string:");
    String plaintext = br.readLine().toUpperCase();

    System.out.println("Enter the size N of the square matrix key: ");
    int n = Integer.parseInt(br.readLine());

    int[][] key = new int[n][n];
    System.out.println("Enter an NxN square matrix: ");

    for(int i=0; i<n; ++i)
    {
      for(int j=0; j<n; ++j)
      {
        key[i][j] = sc.nextInt();
      }
    }

    String ciphertext = HillCipher.encrypt(plaintext, key);
    System.out.println(ciphertext);
  }
}
