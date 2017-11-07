import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Program
{
  public static void main(String[] args)
    throws NoSuchAlgorithmException, IOException
  {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Enter a text message: ");
    String text = br.readLine();
    System.out.println(sha1(text));
  }

  static String sha1(String input) throws NoSuchAlgorithmException
  {
    MessageDigest mDigest = MessageDigest.getInstance("SHA1");
    byte[] result = mDigest.digest(input.getBytes());
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < result.length; i++)
    {
        sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16)
          .substring(1));
    }

    return sb.toString();
  }
}
