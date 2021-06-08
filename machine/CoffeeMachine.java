package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many cups of coffee you will need:");
        int cups = scanner.nextInt();
        printIngredients(cups);
    }

    private static void printIngredients(int cups) {
        final int WATER_PER_CUP = 200;
        final int MILK_PER_CUP = 50;
        final int COFFEE_BEANS_PER_CUP = 15;
        System.out.println(String.format("For %d cups of coffee you will need:\n" +
                "%d ml of water\n" +
                "%d ml of milk\n" +
                "%d g of coffee beans\n", cups, WATER_PER_CUP * cups, MILK_PER_CUP * cups, COFFEE_BEANS_PER_CUP * cups));
    }
}
