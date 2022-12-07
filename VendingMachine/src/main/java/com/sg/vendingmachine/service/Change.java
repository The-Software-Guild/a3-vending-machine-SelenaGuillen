package com.sg.vendingmachine.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Change {
    private static Map<Coin, Integer> coinAmounts = new HashMap<>();

    //method that takes in change as BD, converts to pennies, converts to coins, and returns each coin type w their amounts

    public static Map<Coin, Integer> getChangeInCoins(BigDecimal change) {
        BigDecimal currentChangeInPennies = change.multiply(new BigDecimal("100"));
        //ex: 350.00, 363.00

        int quarterComparison = currentChangeInPennies.compareTo(Coin.QUARTER.getValue());
        if(quarterComparison == 0 || quarterComparison == 1) {
            int quarterAmount = getCoins(currentChangeInPennies, Coin.QUARTER);
            BigDecimal penniesToSubtract = BigDecimal.valueOf(quarterAmount).multiply(Coin.QUARTER.getValue());
            currentChangeInPennies = currentChangeInPennies.subtract(penniesToSubtract);
            coinAmounts.put(Coin.QUARTER, quarterAmount);
        }

        int dimeComparison = currentChangeInPennies.compareTo(Coin.DIME.getValue());
        if (dimeComparison == 0 || dimeComparison == 1) {
            int dimeAmount = getCoins(currentChangeInPennies, Coin.DIME);
            BigDecimal penniesToSubtract = BigDecimal.valueOf(dimeAmount).multiply(Coin.QUARTER.getValue());
            currentChangeInPennies = currentChangeInPennies.subtract(penniesToSubtract);
            coinAmounts.put(Coin.DIME, dimeAmount);
        }

        int nickelComparison = currentChangeInPennies.compareTo(Coin.NICKEL.getValue());
        if (nickelComparison == 0 || nickelComparison == 1) {
            int nickelAmount = getCoins(currentChangeInPennies, Coin.NICKEL);
            BigDecimal penniesToSubtract = BigDecimal.valueOf(nickelAmount).multiply(Coin.NICKEL.getValue());
            currentChangeInPennies = currentChangeInPennies.subtract(penniesToSubtract);
            coinAmounts.put(Coin.NICKEL, nickelAmount);
        }

        int pennyComparison = currentChangeInPennies.compareTo(Coin.PENNY.getValue());
        if (pennyComparison == 0 || pennyComparison == 1) {
            int penniesAmount = getCoins(currentChangeInPennies, Coin.PENNY);
            coinAmounts.put(Coin.PENNY, penniesAmount);
        }

        return coinAmounts;
    }
    public static int getCoins(BigDecimal currentNumberOfPennies, Coin coinType) {
        BigDecimal numberOfCoin = new BigDecimal("0");
        switch (coinType) {
            case QUARTER:
                numberOfCoin = (currentNumberOfPennies.divide(Coin.QUARTER.getValue(), RoundingMode.HALF_EVEN).setScale(2, RoundingMode.HALF_EVEN));
                break;
            case DIME:
                numberOfCoin = (currentNumberOfPennies.divide(Coin.DIME.getValue(), RoundingMode.HALF_EVEN).setScale(2, RoundingMode.HALF_EVEN));
                break;
            case NICKEL:
                numberOfCoin = (currentNumberOfPennies.divide(Coin.NICKEL.getValue(), RoundingMode.HALF_EVEN).setScale(2, RoundingMode.HALF_EVEN));
                break;
            case PENNY:
                numberOfCoin = (currentNumberOfPennies.divide(Coin.PENNY.getValue(), RoundingMode.HALF_EVEN).setScale(2, RoundingMode.HALF_EVEN));
                break;
        }
        return numberOfCoin.intValue();
    }

}
