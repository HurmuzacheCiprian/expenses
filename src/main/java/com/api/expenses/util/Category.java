package com.api.expenses.util;

import java.util.Optional;

public enum Category {

    FOOD("Food"),
    CLOTHES("Clothes"),
    BILLS("Bills"),
    CAR("Car"),
    HOUSEHOLD("Household"),
    ENTERTAINMENT("Entertainment"),
    SPORTS("Sports"),
    COSMETICS("Cosmetics"),
    HOLIDAY("Holiday"),
    BOOKS("Books"),
    TECHNOLOGY("Technology");

    private String categoryName;
    Category(String name) {
        this.categoryName = name;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public static Optional<Category> getCategory(String name) {
        for(Category category : Category.values()) {
            if(category.getCategoryName().equals(name)) {
                return Optional.of(category);
            }
        }
        return Optional.empty();
    }
}
