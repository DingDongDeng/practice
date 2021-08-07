import calculator.Calculator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("start java calculator");
        String formula = scanner.nextLine();
        Calculator calculator = new Calculator();
        System.out.println(calculator.calc(formula));



    }


}
