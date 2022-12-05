package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Snack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{

    VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;


    @Autowired
    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public List<Snack> getAllSnacks() throws VendingMachinePersistenceException {
        return dao.getAllSnacks();
    }

    @Override
    public List<Snack> getAllSnacksInStock() throws VendingMachinePersistenceException {
        return dao.getAllSnacksInStock();
    }

    @Override
    public Snack getSnack(String code) throws
            VendingMachinePersistenceException,
            NoItemInventoryException{
        try {
            return dao.getSnack(code);
        } catch (NullPointerException e) {
            throw new NoItemInventoryException("Item is not in stock or does not exist. Please try again.");
        }
    }

    @Override
    public Snack updateSnackAmount(String code) throws
            VendingMachinePersistenceException,
            NoItemInventoryException {

        try {
            Snack updatedSnack = getSnack(code);
            if (updatedSnack.getAmount() <= 0) {
                throw new NoItemInventoryException("Item is not in stock or does not exist. Please try again.");
            }
            else return dao.updateSnackAmount(code);
        } catch (NullPointerException e) {
            throw new NoItemInventoryException("Item is not in stock or does not exist. Please try again.");
        }
    }

    public Map<Coin, Integer> vendSnack(String code, BigDecimal moneyIn) throws
            VendingMachinePersistenceException,
            InsufficientFundsException,
            NoItemInventoryException {

        //gets and updates snack to vend
        Snack snackToVend = getSnack(code);

        //check if money in is enough to cover snack
        if (moneyIn.compareTo(snackToVend.getPrice()) < 0) {
            throw new InsufficientFundsException("Insufficient Funds. You inserted: $" + moneyIn);
        }

        BigDecimal change = calculateChange(moneyIn, snackToVend.getPrice());
        Map<Coin, Integer> coinAmounts = Change.getChangeInCoins(change);

        auditDao.writeAuditEntry(snackToVend.getType() + " SOLD.");

        updateSnackAmount(code);
        return coinAmounts;
    }

    public BigDecimal calculateChange(BigDecimal moneyIn, BigDecimal price) {
        return moneyIn.subtract(price);
    }
}
