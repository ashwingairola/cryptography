import java.io.*;
import java.util.Scanner;

class HillCipher
{
  public static String encrypt(String plaintext, int[][] key)
  {
    StringBuilder ciphertext = new StringBuilder();
    StringBuilder paddedPlaintext = new StringBuilder();
    int beg = 0, end = 2;

    // Append all letters to the new plaintext.
    for(int i=0; i<plaintext.length(); ++i)
    {
      char ch = plaintext.charAt(i);
      if(Character.isLetter(ch))
      {
        paddedPlaintext.append(ch);
      }
    }

    int originalLength = paddedPlaintext.length();

    // Pad the new plaintext with extra characters.
    while(paddedPlaintext.length() % 3 != 0)
    {
      paddedPlaintext.append('@');
    }

    // If the key is NxN, send N characters each time for matrix multiplication.
    while(end < paddedPlaintext.length())
    {
      ciphertext.append(matrixMultiply(paddedPlaintext.substring(beg, end+1), key));
      beg = end + 1;
      end = beg + key[0].length - 1;
    }

    // Remove padding.
    for(int i=ciphertext.length()-1; ciphertext.length() > originalLength; --i)
    {
      ciphertext.delete(i, i+1);
    }

    return ciphertext.toString();
  }

  public static String decrypt(String ciphertext, int[][] key)
  {
    StringBuilder plaintext = new StringBuilder();
    StringBuilder paddedCiphertext = new StringBuilder();
    int beg = 0, end = 2;

    for(int i=0; i<ciphertext.length(); ++i)
    {
      char ch = ciphertext.charAt(i);
      if(Character.isLetter(ch))
      {
        paddedCiphertext.append(ch);
      }
    }

    int originalLength = paddedCiphertext.length();

    while(paddedCiphertext.length() % 3 != 0)
    {
      paddedCiphertext.append('@');
    }

    // Get the inverse matrix.
    int[][] inverse = inverseKey(key);

    while(end < paddedCiphertext.length())
    {
      plaintext.append(matrixMultiply(paddedCiphertext.substring(beg, end+1), inverse));
      beg = end + 1;
      end = beg + key[0].length - 1;
    }

    for(int i=plaintext.length()-1; plaintext.length() > originalLength; --i)
    {
      plaintext.delete(i, i+1);
    }

    return plaintext.toString();
  }

  private static StringBuilder matrixMultiply(String sub, int[][] key)
  {
    StringBuilder result = new StringBuilder();
    for(int i=0; i<key.length; ++i)
    {
      int sum = 0;
      for(int j=0; j<key[i].length; ++j)
      {
        sum += key[i][j] * (sub.charAt(j)-65);
      }
      result.append((char)(sum % 26 + 65));
    }
    return result;
  }

  private static int[][] inverseKey(int[][] key)
  {
    int detInverse = determinantInverseMod26(key);
    int[][] adj = adjugateMod26(key);

    for(int i=0; i<adj.length; ++i)
    {
      for(int j=0; j<adj.length; ++j)
      {
        adj[i][j] *= detInverse;
        adj[i][j] %= 26;

        if(adj[i][j] < 0)
        {
          adj[i][j] += 26;
        }
      }
    }

    return adj;
  }

  private static int determinantInverseMod26(int[][] key)
  {
    int a = key[0][0], b = key[0][1], c = key[0][2],
    d = key[1][0], e = key[1][1], f = key[1][2],
    g = key[2][0], h = key[2][1], i = key[2][2];

    int determinant = (((a * e * i) + (b * f * g) + (c * d * h)) -
    ((a * f * h) + (b * d * i) + (c * e * g))) % 26;

    int detInverse = 0;

    for(int x=0; i<26; ++x)
    {
      if ((determinant * x) % 26 == 1)
      {
        detInverse = x;
        break;
      }
    }

    return detInverse;
  }

  private static int[][] adjugateMod26(int[][] key)
  {
    int[][] adj = new int[key.length][key.length];

    int a = key[0][0], b = key[0][1], c = key[0][2],
    d = key[1][0], e = key[1][1], f = key[1][2],
    g = key[2][0], h = key[2][1], i = key[2][2];

    adj[0][0] = (e * i) - (f * h);
    adj[0][1] = 0 - ((b * i) - (c * h));
    adj[0][2] = (b * f) - (e * c);
    adj[1][0] = 0 - ((d * i) - (f * g));
    adj[1][1] = (a * i) - (c * g);
    adj[1][2] = 0 - ((a * f) - (c * d));
    adj[2][0] = (d * h) - (e * g);
    adj[2][1] = 0 - ((a * h) - (b * g));
    adj[2][2] = (a * e) - (b * d);

    for(int j=0; j<adj.length; ++j)
    {
      for(int k=0; k<adj.length; ++k)
      {
        adj[j][k] %= 26;
        if(adj[j][k] < 0)
        {
          adj[j][k] += 26;
        }
      }
    }

    return adj;
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

    plaintext = HillCipher.decrypt(ciphertext, key);
    System.out.println(plaintext);
  }
}
