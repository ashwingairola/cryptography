import java.util.HashSet;

class PlayfairCipher
{
  HashSet<Character> charset;
  char[][] matrix;

  public PlayfairCipher()
  {
    charset = new HashSet<>();
    matrix = new char[5][5];
  }

  public String encrypt(String plaintext, String key)
  {
    StringBuilder ciphertext = new StringBuilder();
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    prepareMatrix(key);
    prepareMatrix(alphabet);

    for(int i=0; i<5; ++i)
    {
      for(int j=0; j<5; ++j)
      {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println("");
    }

    StringBuilder paddedPlaintext = createPaddedPlaintext(plaintext);
    System.out.println(paddedPlaintext);

    for(int i=0; i<paddedPlaintext.length()-1; i+=2)
    {
      char ch1 = paddedPlaintext.charAt(i);
      char ch2 = paddedPlaintext.charAt(i+1);

      int row1, col1, row2, col2;
      row1 = col1 = row2 = col2 = -1;

      for(int j=0; j<5; ++j)
      {
        for(int k=0; k<5; ++k)
        {
          if(ch1 == matrix[j][k])
          {
            row1 = j;
            col1 = k;
          }
          else if(ch2 == matrix[j][k])
          {
            row2 = j;
            col2 = k;
          }

          if(row1 != -1 && row2 != -1)
          {
            break;
          }
        }
      }

      if(row1 == row2)
      {
        char ciph1 = (col1 == matrix[0].length-1) ? matrix[row1][0] : matrix[row1][col1+1];
        char ciph2 = (col2 == matrix[0].length-1) ? matrix[row2][0] : matrix[row2][col2+1];
        ciphertext.append(ciph1).append(ciph2);
      }
      else if(col1 == col2)
      {
        char ciph1 = (row1 == matrix[0].length-1) ? matrix[0][col1] : matrix[row1+1][col1];
        char ciph2 = (row2 == matrix[0].length-1) ? matrix[0][col2] : matrix[row2+1][col2];
        ciphertext.append(ciph1).append(ciph2);
      }
      else
      {
        ciphertext.append(matrix[row1][col2]).append(matrix[row2][col1]);
      }
    }

    return ciphertext.toString();
  }

  private void prepareMatrix(String key)
  {
    int keyCount = 0, i = 0, j = 0;

    for(i=0; i<5; ++i)
    {
      for(j=0; j<5; ++j)
      {
        if(matrix[i][j] == '\u0000')
        {
          break;
        }
      }
      if(matrix[i][j] == '\u0000')
      {
        break;
      }
    }

    if(i == 5 && j == 5)
    {
      return;
    }

    while(keyCount < key.length())
    {
      char ch = key.charAt(keyCount);

      if(charset.contains(ch))
      {
        ++keyCount;
      }
      else
      {
        if(ch != 'J')
        {
          matrix[i][j] = ch;
          charset.add(ch);
          ++keyCount;
          if(j == 4)
          {
            j = 0;
            ++i;
          }
          else
          {
            ++j;
          }
        }
        else if(ch == 'J' && !charset.contains('I'))
        {
          matrix[i][j] = 'I';
          charset.add('I');
          ++keyCount;
          if(j == 4)
          {
            j = 0;
            ++i;
          }
          else
          {
            ++j;
          }
        }
        else if(ch == 'J' && charset.contains('I'))
        {
          ++keyCount;
        }
      }
    }
  }

  private StringBuilder createPaddedPlaintext(String plaintext)
  {
    StringBuilder paddedPlaintext = new StringBuilder();
    for(int i=0; i<plaintext.length()-1; ++i)
    {
      char ch = plaintext.charAt(i), ch1 = plaintext.charAt(i+1);
      if(Character.isLetter(ch))
      {
        paddedPlaintext.append(Character.toUpperCase(ch));
        if(ch == ch1)
        {
          paddedPlaintext.append('Q');
        }
      }
    }
    char lastCharacter = plaintext.charAt(plaintext.length()-1);
    paddedPlaintext.append(Character.toUpperCase(lastCharacter));

    if(paddedPlaintext.length()%2 != 0)
    {
      paddedPlaintext.append('Q');
    }

    return paddedPlaintext;
  }
}

public class PlayfairCipherProgram
{
  public static void main(String[] args)
  {
    PlayfairCipher cipher = new PlayfairCipher();
    String ciphertext = cipher.encrypt("HELLO", "BIRJU");
    System.out.println(ciphertext);
  }
}
