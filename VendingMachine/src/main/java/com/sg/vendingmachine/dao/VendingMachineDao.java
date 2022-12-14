package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Snack;

import java.util.List;

public interface VendingMachineDao {
    /**
     * Returns a List of all Snacks in the vending machine regardless of inventory.
     *
     * @return Snack List containing all current snacks in the vending machine.
     * @throws VendingMachinePersistenceException
     */
    List<Snack> getAllSnacks()
            throws VendingMachinePersistenceException;
    /**
     * Returns a List of all available (at least 1 in inventory)
     * Snacks in the vending machine.
     *
     * @return Snack List containing all current snacks in the vending machine.
     * @throws VendingMachinePersistenceException
     */
    List<Snack> getAllSnacksInStock()
        throws VendingMachinePersistenceException;
    /**
     * Returns the snack object associated with the given snack type.
     * Returns null if no such snack exists.
     *
     * @param code vending machine code of the snack to retrieve
     * @return the Snack object associated with the given snack type,
     * null if no such snack exists
     * @throws VendingMachinePersistenceException
     */

    Snack getSnack(String code)
        throws VendingMachinePersistenceException;

    /**
     * Returns the snack that was sold.
     * Updates the Inventory by decreasing by 1.
     * Returns null if no such snack exits.
     *
     * @param code vending machine code of the snack to retrieve
     * @return the Snack object associated with the given snack type,
     * null if no such snack exists.
     * @throws VendingMachinePersistenceException
     */

    Snack updateSnackAmount(String code)
        throws VendingMachinePersistenceException;
}
