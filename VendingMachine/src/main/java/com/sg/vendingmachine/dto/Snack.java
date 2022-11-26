package com.sg.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Snack {
    private String name;
    private SnackType type;
    private BigDecimal price;
    private int amount;

    private String code;
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(SnackType type) {
        this.type = type;
    }

    public SnackType getType() {
        return type;
    }

    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Snack snack = (Snack) o;

        if (amount != snack.amount) return false;
        if (!Objects.equals(name, snack.name)) return false;
        if (type != snack.type) return false;
        if (!Objects.equals(price, snack.price)) return false;
        return Objects.equals(code, snack.code);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + amount;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }
}
