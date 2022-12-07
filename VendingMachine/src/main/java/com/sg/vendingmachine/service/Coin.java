package com.sg.vendingmachine.service;

import java.math.BigDecimal;

public enum Coin {
    QUARTER(new BigDecimal("25")), DIME(new BigDecimal("10")), NICKEL(new BigDecimal("5")), PENNY(new BigDecimal("1"));
    final private BigDecimal value;

    Coin(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }
}
