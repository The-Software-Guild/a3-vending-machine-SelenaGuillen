package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Snack;

import java.io.*;
import java.math.BigDecimal;
import java.util.Scanner;

public class VendingMachineProfitFile implements VendingMachineAuditDao {
    public static final String PROFIT_FILE = "profit.txt";
    public BigDecimal profit = BigDecimal.valueOf(0);

    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(PROFIT_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not persist profit information.");
        }

        out.println(entry);
        out.flush();
    }

    public void loadProfit() throws VendingMachinePersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(PROFIT_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException("Could not load profit into memory.", e);
        }

        String currentLine;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            profit = BigDecimal.valueOf(Double.parseDouble(currentLine));
            scanner.close();
        }
    }
}
