package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Snack;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.ui.VendingMachineView;

import java.math.BigDecimal;
import java.util.Scanner;

public class VendingMachineController {
    Scanner userInput = new Scanner(System.in);
    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
        this.view = view;
        this.service = service;
    }
    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

        try {
            while (keepGoing) {
                menuSelection = printVendingMachineAndGetSelection();

                switch(menuSelection) {
                    case 1:
                        collectMoneyAndMakeSelection();
                        break;
                    case 2:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
        } catch (VendingMachinePersistenceException | InsufficientFundsException | NoItemInventoryException e) {
            if (e instanceof InsufficientFundsException) {
                view.printInsufficientFundsMessage((InsufficientFundsException) e);
            } else if (e instanceof  NoItemInventoryException) {
                view.printNoItemAvailableMessage((NoItemInventoryException) e);
            }
        }
    }

    private int printVendingMachineAndGetSelection()
            throws VendingMachinePersistenceException {
        view.printWelcomeBanner();
        view.printDivider();
        view.printVendingMachine(service.getAllSnacksInStock());
        view.printDivider();
        return view.printMenuAndGetSelection();
    }

    private void collectMoneyAndMakeSelection()
            throws VendingMachinePersistenceException,
            InsufficientFundsException, NoItemInventoryException {
        BigDecimal money = view.printPromptAndCollectMoney();
        String code = view.printPromptAndGetSnackSelection();
        Snack vendedSnack = service.vendSnack(code, money);

        //get change and return to user in enums service.vendSnack does all the inventory dao stuff
        //getCalculation should call to service where calculation is made
        getCalculation(vendedSnack);
    }

    private void getCalculation(Snack snack) {
        //return the change in enmum coins
        System.out.println(snack.getName());
    }
    private void errorMessage() {
        view.printErrorMessage();
    }

    private void unknownCommand() {
        view.printUnknownCommand();
    }
}
