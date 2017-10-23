import java.util.Scanner;

public class AdditiveModuloInverse
{
  public static void main(String[] args)
  {
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter an integer: ");
    int a = sc.nextInt();
    System.out.print("Enter base modulo: ");
    int m = sc.nextInt();

    int x = modInverse(a,m);

    System.out.println("The multiplicative modulo inverse of " + a +
    " under modulo " + m + " is: " + x);
  }

  public static int modInverse(int a, int m)
  {
    for(int x=1; x<m; ++x)
    {
      if((a + x) % m == 0)
      {
        return x;
      }
    }
    return 0;
  }
}
