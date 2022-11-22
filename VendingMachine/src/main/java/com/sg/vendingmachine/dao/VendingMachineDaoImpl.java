package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Snack;
import com.sg.vendingmachine.dto.SnackType;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class VendingMachineDaoImpl implements VendingMachineDao{
    private final String INVENTORY_FILE;
    public static final String DELIMITER = "::";
    private Map<String, Snack> snacks = new HashMap<>();

    public VendingMachineDaoImpl() {
        INVENTORY_FILE = "Inventory.txt";
    }

    @Override
    public List<Snack> getAllSnacks() throws VendingMachinePersistenceException {
        loadInventory();
        return new ArrayList(snacks.values());
    }
    @Override
    public List<Snack> getAllSnacksInStock() throws VendingMachinePersistenceException {
        loadInventory();
        ArrayList<Snack> snackList = new ArrayList(snacks.values());
        ArrayList<Snack> availableSnacks = new ArrayList<>();
        for (Snack snack: snackList) {
            if (snack.getAmount() > 0) {
                availableSnacks.add(snack);
            }
        }
        return availableSnacks;
    }

    @Override
    public Snack getSnack(String code) throws VendingMachinePersistenceException {
        loadInventory();
        return snacks.get(code);
    }

    @Override
    public Snack updateSnackAmount(String code)
            throws VendingMachinePersistenceException {

        loadInventory();
        Snack modifiedSnack = getSnack(code);
        //decrease inventory by 1 and update Map
        modifiedSnack.setAmount(modifiedSnack.getAmount() - 1);
        snacks.replace(code, modifiedSnack);

        //write to inventory file
        writeInventory();

        return snacks.get(code);
    }

    private Snack unmarshallSnack(String snackAsText) {
        //Looks like this in file:
        //Code::Snack Name::SNACK_TYPE::0.00::0

        //create Snack object from file
        String[] snackTokens = snackAsText.split(DELIMITER);
        Snack currentSnack = new Snack();
        currentSnack.setCode(snackTokens[0]);
        currentSnack.setName(snackTokens[1]);
        currentSnack.setType(SnackType.valueOf(snackTokens[2]));
        currentSnack.setPrice(BigDecimal.valueOf(Double.parseDouble(snackTokens[3])));
        currentSnack.setAmount(Integer.parseInt(snackTokens[4]));

        return currentSnack;
    }

    private void loadInventory() throws VendingMachinePersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException("Could not load inventory into memory.", e);
        }

        String currentLine;
        Snack currentSnack;
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentSnack = unmarshallSnack(currentLine);

            snacks.put(currentSnack.getCode(),currentSnack);
        }
        scanner.close();
    }

    private String marshallSnack(Snack snack) {
        String snackAsText = snack.getCode() + DELIMITER;
        snackAsText += snack.getName() + DELIMITER;
        snackAsText += snack.getType() + DELIMITER;
        snackAsText += snack.getPrice() + DELIMITER;
        snackAsText += snack.getAmount();

        return snackAsText;
    }

    private void writeInventory() throws VendingMachinePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save data to memory.", e);
        }

        String snackAsText;
        List<Snack> snackList = this.getAllSnacks();
        for(Snack snack: snackList) {
            snackAsText = marshallSnack(snack);
            out.println(snackAsText);
            out.flush();
        }

        out.close();
    }

}