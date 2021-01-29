package az.guavapay.model.enums;

public enum OrderStatus {
    CONFIRMED(0), NOT_CONFIRMED(1), REJECTED(2);
    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
