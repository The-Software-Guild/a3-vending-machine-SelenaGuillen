package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Snack;
import com.sg.vendingmachine.dto.SnackType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineServiceLayerImplTest {
    private VendingMachineServiceLayer service;

    @Autowired
    public VendingMachineServiceLayerImplTest() {

        ApplicationContext appContext
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = appContext.getBean("service", VendingMachineServiceLayer.class);

    }

    @Test
    public void testValidSnackPurchase() {


        try {
            service.vendSnack("A1", BigDecimal.valueOf(5.00));
        } catch(InsufficientFundsException
                | NoItemInventoryException
                | VendingMachinePersistenceException e) {
            fail("Snack should be available and the funds should cover the price.");
        }
    }

    @Test
    public void testInvalidSnackPurchase() {
        Snack testSnack = new Snack();
        testSnack.setCode("C1");
        testSnack.setName("Lay's Potato Chips");
        testSnack.setType(SnackType.LAYS_POTATO_CHIPS);
        testSnack.setPrice(BigDecimal.valueOf(1.50));
        testSnack.setAmount(0);


        //used the constructor w/ Snack param to test a snack with 0 inventory
        VendingMachineDao tempDao = new VendingMachineDaoStubImpl(testSnack);
        VendingMachineAuditDao tempAuditDao = new VendingMachineAuditDaoStubImpl();
        VendingMachineServiceLayer tempService = new VendingMachineServiceLayerImpl(tempDao, tempAuditDao);


        assertThrows(NoItemInventoryException.class, () -> {
            tempService.vendSnack("C1", BigDecimal.valueOf(5.00));
        }, "NoItemInventoryException should be thrown because the item is not in stock.");
    }

    @Test
    public void testInsufficientFunds() {

        assertThrows(InsufficientFundsException.class, () -> {
            service.vendSnack("A1", BigDecimal.valueOf(0.50));
        }, "InsufficientFundsException should be thrown because 0.50 is not enough to cover");
    }

    @Test
    void getAllSnacks() throws Exception{
        Snack snackClone = new Snack();
        snackClone.setCode("A1");
        snackClone.setName("Oreos");
        snackClone.setType(SnackType.OREOS);
        snackClone.setPrice(BigDecimal.valueOf(1.50));
        snackClone.setAmount(10);

        assertEquals(1, service.getAllSnacks().size(), "Should only have one snack.");
        assertEquals(snackClone, service.getSnack("A1"));
        assertTrue(service.getAllSnacks().contains(snackClone), "The one snack should be Oreos.");
    }


    @Test
    void getSnack() throws Exception{
        Snack snackClone = new Snack();
        snackClone.setCode("A1");
        snackClone.setName("Oreos");
        snackClone.setType(SnackType.OREOS);
        snackClone.setPrice(BigDecimal.valueOf(1.50));
        snackClone.setAmount(10);

        Snack shouldBeOreo = service.getSnack("A1");
        assertNotNull(shouldBeOreo, "Should not be null.");

        Snack shouldBeNull = service.getSnack("B1");
        assertNull(shouldBeNull, "No snack is stored here.");
    }

}