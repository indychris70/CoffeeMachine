package machine;

public enum UnitsOfMeasure {
    ML("ml", "ml", ""),
    G("g", "grams", ""),
    CUP("disposable cups", "disposable cups", ""),
    DOLLAR("dollars", "dollars", "$");

    private String shortText;
    private String longText;
    private String prefix;

    UnitsOfMeasure(String shortText, String longText, String prefix) {
        this.shortText = shortText;
        this.longText = longText;
        this.prefix = prefix;
    }

    public String getShortText() {
        return shortText;
    }

    public String getLongText() {
        return longText;
    }

    public String getPrefix() {
        return prefix;
    }
}
