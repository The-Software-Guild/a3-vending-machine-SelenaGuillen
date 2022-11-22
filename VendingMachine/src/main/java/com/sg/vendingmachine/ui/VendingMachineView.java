package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Snack;
import com.sg.vendingmachine.service.Coin;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class VendingMachineView {
    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("1. Buy");
        io.print("2. Exit");

        return io.readInt("Please select from the above choices.", 1, 2);
    }

    public void printWelcomeBanner() {
        io.print("~*~* Welcome! *~*~");
    }

    public void printDivider() {
        io.print("---------------------------------");
    }
    public void printVendingMachine(List<Snack> snackList) {
        for (Snack snack: snackList) {
            io.print("|" + snack.getCode() + "|" + snack.getName() + " : $" + snack.getPrice());
        }
    }

    public BigDecimal printPromptAndCollectMoney() {
        double userInput = io.readDouble("Please enter cash amount.");
        return BigDecimal.valueOf(userInput);
    }

    public String printPromptAndGetSnackSelection() {
        String code = io.readString("Please enter the snack code.");
        return code;
    }
    public void printUnknownCommand() {
        io.print("Unknown Command. Please try again.");
    }
    public void printErrorMessage() {
        io.print("ERROR");
    }

    public void printInsufficientFundsMessage(InsufficientFundsException e) {
        io.print(e.getMessage());
    }

    public void printNoItemAvailableMessage(NoItemInventoryException e) {
        io.print(e.getMessage());
    }

    public void printUserMoneyIn(BigDecimal moneyIn) {
        io.print("You inserted $" + moneyIn.toString());
    }

    public void printChange(BigDecimal moneyIn, BigDecimal price) {
        io.print("Your change is $" + moneyIn.subtract(price));
    }

    public void printChangeInCoins(Map<Coin, Integer> coinAmounts) {
        io.print("You get back: ");


        //returns collection view of map
        for (Map.Entry<Coin, Integer> entry: coinAmounts.entrySet()) {
            Coin key = entry.getKey();
            Integer value = entry.getValue();

            switch (key) {
                case QUARTER:
                    io.print(value + " quarter(s)");
                    break;
                case DIME:
                    io.print(value + " dime(s)");
                    break;
                case NICKEL:
                    io.print(value + " nickel(s)");
                    break;
                case PENNY:
                    io.print(value + " penny/pennies");
                    break;
            }
        }
        io.print("\n");
    }

}
