package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Snack;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;

import java.math.BigDecimal;
import java.util.List;

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

}
