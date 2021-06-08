package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private enum Messages {
        PROMPT("Write how many %s of %s the coffee machine has:"),
        PROMPT_CUPS_OF_COFFEE("Write how many cups of coffee you will need:"),
        RESPONSE_YES("Yes, I can make that amount of coffee"),
        RESPONSE_NO("No, I can make only %s cup(s) of coffee"),
        RESPONSE_YES_AND("Yes, I can make that amount of coffee (and even %s more than that)");

        String text;

        Messages(String text) {
            this.text = text;
        }

        public void print(String... strings) {
            System.out.println(String.format(text, (Object[]) strings));
        }
    }

    private enum UnitsOfMeasure {
        ML("ml"), G("g");

        private String text;

        UnitsOfMeasure(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    private enum Ingredients {
        WATER("water", 200, UnitsOfMeasure.ML),
        MILK("milk", 50, UnitsOfMeasure.ML),
        BEANS("coffee beans", 15, UnitsOfMeasure.G);

        private String text;
        private int totalQuantity;
        private int quantityPerCup;
        private UnitsOfMeasure unitOfMeasure;

        Ingredients(String text, int quantityPerCup, UnitsOfMeasure unitOfMeasure) {
            this.text = text;
            this.quantityPerCup = quantityPerCup;
            this.unitOfMeasure = unitOfMeasure;
            this.totalQuantity = 0;
        }

        public String getText() {
            return text;
        }

        public String getUnitOfMeasureText() {
            return unitOfMeasure.getText();
        }

        public void setTotalQuantity(int totalQuantity) {
            this.totalQuantity = totalQuantity;
        }

        public int numberOfCups() {
            if (totalQuantity == 0) {
                return 0;
            } else {
                return totalQuantity / quantityPerCup;
            }
        }

        public int cupsRemainingAfterRequest(int cups) {
            return numberOfCups() - cups;
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int totalWater = getInput(scanner, Messages.PROMPT, Ingredients.WATER.getUnitOfMeasureText(), Ingredients.WATER.getText());
        Ingredients.WATER.setTotalQuantity(totalWater);
        int totalMilk = getInput(scanner, Messages.PROMPT, Ingredients.MILK.getUnitOfMeasureText(), Ingredients.MILK.getText());
        Ingredients.MILK.setTotalQuantity(totalMilk);
        int totalBeans = getInput(scanner, Messages.PROMPT, Ingredients.BEANS.getUnitOfMeasureText(), Ingredients.BEANS.getText());
        Ingredients.BEANS.setTotalQuantity(totalBeans);
        int requiredCups = getInput(scanner, Messages.PROMPT_CUPS_OF_COFFEE);
        checkRequest(requiredCups);
    }

    public static int getInput(Scanner scanner, Messages prompt, String... strings) {
        prompt.print(strings);
        return scanner.nextInt();
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

    private static void checkRequest (int cups) {
        int maxCups = Math.min(Math.min(Ingredients.WATER.cupsRemainingAfterRequest(cups), Ingredients.MILK.cupsRemainingAfterRequest(cups)), Ingredients.BEANS.cupsRemainingAfterRequest(cups));
        if (maxCups > 0) {
            Messages.RESPONSE_YES_AND.print(Integer.toString(maxCups));
        } else if (maxCups < 0) {
            Messages.RESPONSE_NO.print(Integer.toString(cups + maxCups));
        } else {
            Messages.RESPONSE_YES.print();
        }
    }
}
