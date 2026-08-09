package com.store.utils;

public enum MenuCategory {
    BOOKS("Books"),
    COMPUTERS("Computers"),
    ELECTRONICS("Electronics"),
    APPAREL_SHOES("Apparel & Shoes"),
    DIGITAL_DOWNLOADS("Digital downloads"),
    JEWELRY("Jewelry"),
    GIFT_CARDS("Gift Cards");

    private final String label;

    MenuCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
