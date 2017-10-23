import java.util.HashSet;
import java.io.*;

class PlayfairCipher
{
  HashSet<Character> charset;
  char[][] matrix;

  public PlayfairCipher()
  {
    charset = new HashSet<>();
    matrix = new char[5][5];
  }

  // Encryption
  public String encrypt(String plaintext, String key)
  {
    // Convert the plaintext and the key to uppercase.
    plaintext = plaintext.toUpperCase();
    key = key.toUpperCase();
    // Ciphertext will be stored here.
    StringBuilder ciphertext = new StringBuilder();
    // For completing the matrix.
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    // First arrange the key within the matrix, then the remaining letters.
    prepareMatrix(key);
    prepareMatrix(alphabet);

    // Displaying the matrix.
    System.out.println("\nThe matrix is:");
    for(int i=0; i<5; ++i)
    {
      for(int j=0; j<5; ++j)
      {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println("");
    }

    /* Modify the plaintext such that the length of the plaintext is even
    (for making pairs) and there are no consequtive duplicate letters. */
    StringBuilder paddedPlaintext = createPaddedPlaintext(plaintext);
    System.out.println("\nThe padded plaintext is:\n" + paddedPlaintext);

    // Analyse two letters in the plaintext at a time.
    for(int i=0; i<paddedPlaintext.length()-1; i+=2)
    {
      // Extract the two characters in the pair.
      char ch1 = paddedPlaintext.charAt(i);
      char ch2 = paddedPlaintext.charAt(i+1);

      /* The first character will be at the position (row1, col1) in the matrix,
      while the second will be at (row2, col2). Initially, they are all -1.*/
      int row1, col1, row2, col2;
      row1 = col1 = row2 = col2 = -1;

      // Search the entire matrix and find the positions of the two characters.
      for(int j=0; j<5; ++j)
      {
        for(int k=0; k<5; ++k)
        {
          /* If the first character is found,
          store its indices in row1 and col1. */
          if(ch1 == matrix[j][k])
          {
            row1 = j;
            col1 = k;
          }
          /* If the second character is found,
          store its indices in row2 and col2. */
          else if(ch2 == matrix[j][k])
          {
            row2 = j;
            col2 = k;
          }

          /* If the positions of both the characters has been found,
          break out of the loop. */
          if(row1 != -1 && row2 != -1)
          {
            break;
          }
        }
        // Make the same check so as to avoid unnecessary iterations.
        if(row1 != -1 && row2 != -1)
        {
          break;
        }
      }

      // Case 1: The two characters are on the same row in the matrix.
      if(row1 == row2)
      {
        /* Find the two characters directly to the right of the original
        characters. If the on of the characters is the last letter in its row,
        select the first character in the row. These two new characters are
        appended to the existing ciphertext. */
        char ciph1 = (col1 == matrix[0].length-1) ? matrix[row1][0] : matrix[row1][col1+1];
        char ciph2 = (col2 == matrix[0].length-1) ? matrix[row2][0] : matrix[row2][col2+1];
        ciphertext.append(ciph1).append(ciph2);
      }

      // Case 2: The two characters are on the same column in the matrix.
      else if(col1 == col2)
      {
        /* Find the two characters directly below the original characters.
        If the on of the characters is the last letter in its column,
        select the first character in the column. These two new characters are
        appended to the existing ciphertext. */
        char ciph1 = (row1 == matrix[0].length-1) ? matrix[0][col1] : matrix[row1+1][col1];
        char ciph2 = (row2 == matrix[0].length-1) ? matrix[0][col2] : matrix[row2+1][col2];
        ciphertext.append(ciph1).append(ciph2);
      }

      // Case 3: The two characters are on different rows and columns.
      else
      {
        /*
        1. Find the character on the same row as that of the FIRST plaintext
          character and the same column as that of the SECOND plaintext
          character. Append it to the ciphertext.

        2. Find the character on the same row as that of the SECOND plaintext
          character and the same column as that of the FIRST plaintext
          character. Append it to the ciphertext.
        */
        ciphertext.append(matrix[row1][col2]).append(matrix[row2][col1]);
      }
    }
    // Return the ciphertext.
    return ciphertext.toString();
  }

  // Preparing the matrix from the key.
  private void prepareMatrix(String key)
  {
    int keyCount = 0, i = 0, j = 0;
    /* Iterate through the matrix to find the index till which the matrix has
    been filled. This is useful when the key has been used and the remaining
    letters need to be filled. */
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

    /* If the i and j are both 5, the matrix is completely filled and there is
    no need to proceed further. This is useful when the original key itself
    contains all the letters in the English alphabet. */
    if(i == 5 && j == 5)
    {
      return;
    }

    // Iterate till all the characters in the key are exhausted.
    while(keyCount < key.length())
    {
      // Extract the current key character.
      char ch = key.charAt(keyCount);
      /* If the character is already in the matrix, just move to the next
      character. */
      if(charset.contains(ch))
      {
        ++keyCount;
      }
      else
      {
        /* If the character is not 'J':
        1. Add it to the matrix and the charset.
        2. Update the keyCount.
        3. If the last element in the row has been reached, move to the first
          character in the next row, else just move to the next character in the
          same row. */
        if(ch != 'J')
        {
          matrix[i][j] = ch;
          charset.add(ch);
          ++keyCount;
          ++j;
          if(j == 5)
          {
            j = 0;
            ++i;
          }
        }

        /* If the character is 'J' and 'I' doesn't exist in the matrix:
        1. Add 'I' to the matrix and the charset instead of 'J'.
        2. Same as steps 2 and 3 above. */
        else if(ch == 'J' && !charset.contains('I'))
        {
          matrix[i][j] = 'I';
          charset.add('I');
          ++keyCount;
          ++j;
          if(j == 5)
          {
            j = 0;
            ++i;
          }
        }

        /* If the character id 'J' and 'I' already exists in the matrix, simply
        ignore this and move on to the next character. */
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
    // Iterate till the second-last character in the plaintext string.
    for(int i=0; i<plaintext.length()-1; ++i)
    {
      // Extract the current character as well as the one next to it.
      char ch = plaintext.charAt(i), ch1 = plaintext.charAt(i+1);
      // If the character is a letter, append it to the padded plaintext.
      if(Character.isLetter(ch))
      {
        paddedPlaintext.append(Character.toUpperCase(ch));
        // If the two characters are the same, add a 'Q' between them.
        if(ch == ch1)
        {
          paddedPlaintext.append('Q');
        }
      }
    }
    // Append the last character in the plaintext to the padded plaintext.
    char lastCharacter = plaintext.charAt(plaintext.length()-1);
    paddedPlaintext.append(Character.toUpperCase(lastCharacter));

    /* If the length of the padded plaintext is not even, add a 'Q' as padding
    to make it so. */
    if(paddedPlaintext.length()%2 != 0)
    {
      paddedPlaintext.append('Q');
    }

    return paddedPlaintext;
  }
}

public class PlayfairCipherProgram
{
  public static void main(String[] args) throws IOException
  {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PlayfairCipher cipher = new PlayfairCipher();
    System.out.println("Enter a plaintext string: ");
    String plaintext = br.readLine();
    System.out.println("Enter a key:");
    String key = br.readLine();
    String ciphertext = cipher.encrypt(plaintext, key);
    System.out.println("\nThe ciphertext is:\n" + ciphertext);
  }
}
