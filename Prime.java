import java.util.Scanner;

public class Prime
{
  public static void main(String[] args)
  {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter a positive integer: ");
    int n = sc.nextInt();
    for(int i=2; i<=Math.sqrt(n); ++i)
    {
      if(n % i == 0)
      {
        System.out.println(n + " is not prime.");
        return;
      }
    }
    System.out.println(n + " is prime.");
  }
}
