import java.util.Scanner;

public class Main {

    public static String print(String input) {
        return input;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String out = input.nextLine();
        System.out.println(print(out));
    }

}
