package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Snack;
import com.sg.vendingmachine.service.Coin;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.ui.VendingMachineView;

import java.math.BigDecimal;
import java.util.Map;

public class VendingMachineController {
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

        //get money from user
        BigDecimal money = view.printPromptAndCollectMoney();

        //retrieve snack
        String code = view.printPromptAndGetSnackSelection();
        Snack vendedSnack = service.getSnack(code);

        //sell snack and get change in coins
        Map<Coin, Integer> changeInCoins = service.vendSnack(code, money);
        view.printDivider();
        view.printChange(money, vendedSnack.getPrice());
        view.printChangeInCoins(changeInCoins);
    }


    private void unknownCommand() {
        view.printUnknownCommand();
    }
}
