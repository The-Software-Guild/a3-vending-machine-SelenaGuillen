package com.sg.vendingmachine.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Change {
    private static Map<Coin, Integer> coinAmounts = new HashMap<>();
    private int quartersAmount;
    private int dimesAmount;
    private int nickelsAmount;
    private int penniesAmount;

    public void setQuartersAmount(Coin quarters, int quartersAmount) {
        quarters = Coin.QUARTER;
        this.quartersAmount = quartersAmount;
    }

    public void setDimesAmount(Coin dimes, int amount) {
        dimes = Coin.DIME;
        this.dimesAmount = amount;
    }
    public void setNickelsAmount(Coin nickels, int nickelsAmount) {
        nickels = Coin.NICKEL;
        this.nickelsAmount = nickelsAmount;
    }

    public void setPenniesAmount(Coin pennies, int penniesAmount) {
        pennies = Coin.PENNY;
        this.penniesAmount = penniesAmount;
    }

    public int getQuartersAmount() {
        return quartersAmount;
    }

    public int getDimesAmount() {
        return dimesAmount;
    }

    public int getNickelsAmount() {
        return nickelsAmount;
    }

    public int getPenniesAmount() {
        return penniesAmount;
    }

    //method that takes in change as BD, converts to pennies, converts to coins, and returns each coin type w their amounts

    public static Map<Coin, Integer> getChangeInCoins(BigDecimal change) {
        BigDecimal currentChangeInPennies = change.multiply(BigDecimal.valueOf(100));
        //ex: 350.00, 363.00
        //coinAmounts.put(Coin.PENNY, amountOfPennies);

        int quarterComparison = currentChangeInPennies.compareTo(BigDecimal.valueOf(25));
        if(quarterComparison == 0 || quarterComparison == 1) {
            int quarterAmount = getQuarters(currentChangeInPennies);
            BigDecimal penniesToSubtract = BigDecimal.valueOf(quarterAmount).multiply(BigDecimal.valueOf(25));
            currentChangeInPennies = currentChangeInPennies.subtract(penniesToSubtract);
            coinAmounts.put(Coin.QUARTER, quarterAmount);
        }

        int dimeComparison = currentChangeInPennies.compareTo(BigDecimal.valueOf(10));
        if (dimeComparison == 0 || dimeComparison == 1) {
            int dimeAmount = getDimes(currentChangeInPennies);
            BigDecimal penniesToSubtract = BigDecimal.valueOf(dimeAmount).multiply(BigDecimal.valueOf(10));
            currentChangeInPennies = currentChangeInPennies.subtract(penniesToSubtract);
            coinAmounts.put(Coin.DIME, dimeAmount);
        }

        int nickelComparison = currentChangeInPennies.compareTo(BigDecimal.valueOf(5));
        if (nickelComparison == 0 || nickelComparison == 1) {
            int nickelAmount = getNickels(currentChangeInPennies);
            BigDecimal penniesToSubtract = BigDecimal.valueOf(nickelAmount).multiply(BigDecimal.valueOf(5));
            currentChangeInPennies = currentChangeInPennies.subtract(penniesToSubtract);
            coinAmounts.put(Coin.NICKEL, nickelAmount);
        }

        int pennyComparison = currentChangeInPennies.compareTo(BigDecimal.valueOf(1));
        if (pennyComparison == 0 || pennyComparison == 1) {
            int penniesAmount = getPennies(currentChangeInPennies);
            BigDecimal penniesToSubtract = BigDecimal.valueOf(penniesAmount).multiply(BigDecimal.valueOf(1));
            currentChangeInPennies = currentChangeInPennies.subtract(penniesToSubtract);
            coinAmounts.put(Coin.PENNY, penniesAmount);
        }

        return coinAmounts;
    }
    public static int getQuarters(BigDecimal currentNumberOfPennies) {
        BigDecimal numberOfQuarters;
        numberOfQuarters = (currentNumberOfPennies.divide(BigDecimal.valueOf(25))).setScale(2, RoundingMode.HALF_EVEN);
        return numberOfQuarters.intValue();
    }

    public static int getDimes(BigDecimal currentNumberOfPennies) {
        BigDecimal numberOfQuarters;
        numberOfQuarters = (currentNumberOfPennies.divide(BigDecimal.valueOf(10))).setScale(2, RoundingMode.HALF_EVEN);
        return numberOfQuarters.intValue();
    }
    public static int getNickels(BigDecimal currentNumberOfPennies) {
        BigDecimal numberOfQuarters;
        numberOfQuarters = (currentNumberOfPennies.divide(BigDecimal.valueOf(5))).setScale(2, RoundingMode.HALF_EVEN);
        return numberOfQuarters.intValue();
    }
    public static int getPennies(BigDecimal currentNumberOfPennies) {
        BigDecimal numberOfQuarters;
        numberOfQuarters = (currentNumberOfPennies.divide(BigDecimal.valueOf(1))).setScale(2, RoundingMode.HALF_EVEN);
        return numberOfQuarters.intValue();
    }
}
