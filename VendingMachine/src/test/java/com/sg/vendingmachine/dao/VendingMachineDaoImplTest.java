package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Snack;
import com.sg.vendingmachine.dto.SnackType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineDaoImplTest {

    static VendingMachineDao testDao;

    public VendingMachineDaoImplTest() {

    }

    //Non-static method that is run once before each test method & used to set
    //things to a known good state before each test
    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testvendingmachine.txt";
        testDao = new VendingMachineDaoImpl(testFile);
    }

    @Test
    void getAllSnacks() throws Exception{
        /* test file preloaded with the following:
        B1::Lay's Potato Chips::LAYS_POTATO_CHIPS::1.25::10
        C2::Oreos::OREOS::1.50::0
         */
        //retrieve list of all regardless of inventory
        List<Snack> allSnacks = testDao.getAllSnacks();

        //check contents
        assertNotNull(allSnacks, "The list must not be null");
        assertEquals(2, allSnacks.size(), "The list must contain 2, regardless of what's in stock");

    }

    @Test
    void getAllSnacksInStock() throws Exception{
        List<Snack> snacksInStock = testDao.getAllSnacksInStock();

        assertNotNull(snacksInStock, "List is not null.");
        assertEquals(1, snacksInStock.size(), "Should only contain one snack.");
    }

    @Test
    void getSnack() throws Exception {
        Snack retrievedSnack = testDao.getSnack("C2");
        assertEquals("C2", retrievedSnack.getCode());
        assertEquals("Oreos", retrievedSnack.getName(), "Should reflect the same name.");
        assertEquals(SnackType.OREOS, retrievedSnack.getType(), "Type should be the same.");
        assertEquals(BigDecimal.valueOf(1.50).setScale(2, RoundingMode.HALF_EVEN), retrievedSnack.getPrice());
        assertEquals(0, retrievedSnack.getAmount());
    }

    @Test
    void updateSnackAmount() throws Exception{
        Snack snackToUpdate = testDao.updateSnackAmount("B1");
        //Before running this test, ensure Lay's is stocked with 10 since
        //this field cannot be modified in the program, have to manually reset
        assertEquals(9, snackToUpdate.getAmount(), "The amount should have decreased by 1.");
    }
}