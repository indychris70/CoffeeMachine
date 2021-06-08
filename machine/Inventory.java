package machine;

public enum Inventory {
    WATER("water", UnitsOfMeasure.ML, 400),
    MILK("milk", UnitsOfMeasure.ML, 540),
    BEANS("coffee beans", UnitsOfMeasure.G, 120),
    MONEY("money", UnitsOfMeasure.DOLLAR, 550),
    CUPS("coffee", UnitsOfMeasure.CUP, 9);

    private String text;
    private int totalQuantity;
    private UnitsOfMeasure unitOfMeasure;

    Inventory(String text, UnitsOfMeasure unitOfMeasure, int totalQuantity) {
        this.text = text;
        this.unitOfMeasure = unitOfMeasure;
        this.totalQuantity = totalQuantity;
    }

    public String getText() {
        return text;
    }

    public UnitsOfMeasure getUOM() {
        return unitOfMeasure;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
