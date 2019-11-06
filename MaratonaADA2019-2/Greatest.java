import java.util.Scanner;

public class Greatest
{
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args)
    {
        int numValues = scan.nextInt();
        int value;

        while (numValues > 0)
        {
            int greatest = Integer.MIN_VALUE;

            for (int i = 0; i < numValues; i++)
            {
                value = scan.nextInt();

                if (value > greatest) greatest = value;
            }

            System.out.println(greatest);
            numValues = scan.nextInt();
        }
    }
}
