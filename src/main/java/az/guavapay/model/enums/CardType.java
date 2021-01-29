package az.guavapay.model.enums;

public enum CardType {
    MC(0), VISA(1), UnionPay(2);
    private final int value;

    CardType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
