package com.tc.stockcontrol.product.enums;

public enum Category {
    GROCERIES("Groceries"), BEVERAGES("Beverages"), HOUSEHOLD_ITEMS("Household-Items");

    private final String value;

    private Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
