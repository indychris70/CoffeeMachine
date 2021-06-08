package machine;

public enum Recipe {
    ESPRESSO("espresso", 250, 0, 16, -4, 1),
    LATTE("latte", 350, 75, 20, -7, 1),
    CAPPUCCINO("cappuccino", 200, 100, 12, -6, 1);

    String text;
    private int water;
    private int milk;
    private int beans;
    private int price;
    private int cups;
    private Inventory[] ingredients = new Inventory[] {Inventory.WATER, Inventory.MILK, Inventory.BEANS, Inventory.MONEY, Inventory.CUPS};

    Recipe(String text, int water, int milk, int beans, int price, int cups) {
        this.text = text;
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.price = price;
        this.cups = cups;
    }

    public int getRequirement(Inventory ingredient) {
        switch (ingredient) {
            case WATER:
                return water;
            case MILK:
                return milk;
            case BEANS:
                return beans;
            case MONEY:
                return price;
            case CUPS:
                return cups;
        }
        return 0;
    }

    public String getText() {
        return text;
    }

    public int getPrice() {
        return price;
    }

    public Inventory[] getIngredients() {
        return ingredients;
    }
}
