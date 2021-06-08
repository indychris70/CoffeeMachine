package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private enum Messages {
        PROMPT_FILL("Write how many %s of %s you want to add:"),
        PROMPT_ACTION("Write action (buy, fill, take, remaining, exit):"),
        PROMPT_SELECTION("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:"),
        WITHDRAW("I gave you $%s"),
        RESPONSE_YES("I have enough resources, making you a coffee!"),
        RESPONSE_NO("Sorry, not enough %s!");

        String text;

        Messages(String text) {
            this.text = text;
        }

        public void print(String... strings) {
            System.out.println(String.format(text, (Object[]) strings));
        }
    }

    public static void main(String[] args) {
        final String EXIT_SELECTION = "exit";
        String selection;
        do {
            selection = getStringInput(Messages.PROMPT_ACTION);
            fulfillRequest(selection);
        } while (!selection.equals(EXIT_SELECTION));
    }

    private static void fulfillRequest(String action) {
        switch (action.toLowerCase()) {
            case "buy":
                buy();
                break;
            case "fill":
                fill();
                break;
            case "take":
                take();
                break;
            case "remaining":
                printInventory();
                break;
            case "exit":
                break;
        }
    }

    private static void buy() {
        String selection = getStringInput(Messages.PROMPT_SELECTION);
        if ("back".equals(selection))  {
            return;
        }
        updateInventory(Integer.parseInt(selection), 1);
    }

    private static void updateInventory(int selection, int numberOfCups) {
        Recipe recipe = Recipe.CAPPUCCINO;
        switch (selection) {
            case 1:
                recipe = Recipe.ESPRESSO;
                break;
            case 2:
                recipe = Recipe.LATTE;
                break;
            case 3:
                recipe = Recipe.CAPPUCCINO;
                break;
            default:
                return;
        }
        checkRequest(numberOfCups, recipe);
    }

    private static void fill() {
        int totalWater = getIntInput(Messages.PROMPT_FILL, Inventory.WATER.getUOM().getShortText(), Inventory.WATER.getText());
        Inventory.WATER.setTotalQuantity(Inventory.WATER.getTotalQuantity() + totalWater);
        int totalMilk = getIntInput(Messages.PROMPT_FILL, Inventory.MILK.getUOM().getShortText(), Inventory.MILK.getText());
        Inventory.MILK.setTotalQuantity(Inventory.MILK.getTotalQuantity() + totalMilk);
        int totalBeans = getIntInput(Messages.PROMPT_FILL, Inventory.BEANS.getUOM().getShortText(), Inventory.BEANS.getText());
        Inventory.BEANS.setTotalQuantity(Inventory.BEANS.getTotalQuantity() + totalBeans);
        int totalCups = getIntInput(Messages.PROMPT_FILL, Inventory.CUPS.getUOM().getShortText(), Inventory.CUPS.getText());
        Inventory.CUPS.setTotalQuantity(Inventory.CUPS.getTotalQuantity() + totalCups);
    }

    private static void take() {
        Messages.WITHDRAW.print(Integer.toString(Inventory.MONEY.getTotalQuantity()));
        Inventory.MONEY.setTotalQuantity(0);
    }

    private static int getIntInput(Messages prompt, String... strings) {
        Scanner s = new Scanner(System.in);
        prompt.print(strings);
        return s.nextInt();
    }

    private static String getStringInput(Messages prompt, String... strings) {
        Scanner s = new Scanner(System.in);
        prompt.print(strings);
        return s.nextLine();
    }

    private static void printRequirements(int cups, Recipe recipe) {
        System.out.println(String.format("For %d cups of %s you will need:\n", cups, recipe.getText()));
        for (Inventory ingredient : recipe.getIngredients()) {
            System.out.println(String.format("%d %s of %s\n", recipe.getRequirement(ingredient), ingredient.getUOM().getShortText(), ingredient.getText()));
        }
        System.out.println(String.format("The total price will be %d %s", recipe.getPrice(), Inventory.MONEY.getUOM().getShortText()));
    }

    private static void printInventory() {
        System.out.println("The coffee machine has:");
        System.out.println(String.format("%d %s of %s", Inventory.WATER.getTotalQuantity(), Inventory.WATER.getUOM().getShortText(), Inventory.WATER.getText()));
        System.out.println(String.format("%d %s of %s", Inventory.MILK.getTotalQuantity(), Inventory.MILK.getUOM().getShortText(), Inventory.MILK.getText()));
        System.out.println(String.format("%d %s of %s", Inventory.BEANS.getTotalQuantity(), Inventory.BEANS.getUOM().getShortText(), Inventory.BEANS.getText()));
        System.out.println(String.format("%d %s", Inventory.CUPS.getTotalQuantity(), Inventory.CUPS.getUOM().getShortText()));
        System.out.println(String.format("%s%d of %s", Inventory.MONEY.getUOM().getPrefix(), Inventory.MONEY.getTotalQuantity(), Inventory.MONEY.getText()));
    }

    private static void checkRequest (int cups, Recipe recipe) {
        Inventory constrainedIngredient = getConstrainedIngredient(cups, recipe);
        int excessCupsAfterRequest = cupsRemainingAfterRequest(cups, constrainedIngredient.getTotalQuantity(), recipe.getRequirement(constrainedIngredient));
        if (excessCupsAfterRequest >= 0) {
            Messages.RESPONSE_YES.print();
            for (Inventory ingredient : recipe.getIngredients()) {
                ingredient.setTotalQuantity(ingredient.getTotalQuantity() - recipe.getRequirement(ingredient) * cups);
            }
        } else {
            Messages.RESPONSE_NO.print(constrainedIngredient.getText());
        }
    }

    private static Inventory getConstrainedIngredient(int cups, Recipe recipe) {
        Inventory constrainedIngredient = Inventory.WATER;
        int minimumDifference = Integer.MAX_VALUE;
        int cupsRemaining;
        for (Inventory ingredient : recipe.getIngredients()) {
            if (ingredient != Inventory.MONEY && recipe.getRequirement(ingredient) > 0) {
                cupsRemaining = cupsRemainingAfterRequest(cups, ingredient.getTotalQuantity(), recipe.getRequirement(ingredient));
                if (cupsRemaining < minimumDifference) {
                    minimumDifference = cupsRemaining;
                    constrainedIngredient = ingredient;
                }
            }
        }
        return constrainedIngredient;
    }

    public static int numberOfCups(int totalQuantity, int quantityPerCup) {
        if (quantityPerCup == 0) {
            return 0;
        } else {
            return totalQuantity / quantityPerCup;
        }
    }

    public static int cupsRemainingAfterRequest(int cups, int totalQuantity, int quantityPerCup) {
        return numberOfCups(totalQuantity, quantityPerCup) - cups;
    }
}
