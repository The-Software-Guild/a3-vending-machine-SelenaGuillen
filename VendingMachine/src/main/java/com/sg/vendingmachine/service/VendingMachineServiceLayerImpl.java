package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineAuditDaoFileImpl;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Snack;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{

    VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;
    private VendingMachineAuditDao profitLog;

    private BigDecimal profit;

    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao,
                                          VendingMachineAuditDao profitLog) {
        this.dao = dao;
        this.auditDao = auditDao;
        this.profitLog = profitLog;
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
    public Snack getSnack(String code) throws VendingMachinePersistenceException {
        return dao.getSnack(code);
    }

    @Override
    public Snack updateSnackAmount(String code) throws
            VendingMachinePersistenceException,
            NoItemInventoryException {
        Snack updatedSnack = getSnack(code);

        //tries updateSnackAmount when a sale is made
        if (updatedSnack.getAmount() <= 0) {
            throw new NoItemInventoryException("Item is not in stock. Please try again.");
        }
        //dao writes to file and updates the inventory
        else return dao.updateSnackAmount(code);
    }

    public Snack vendSnack(String code, BigDecimal moneyIn) throws
            VendingMachinePersistenceException,
            InsufficientFundsException,
            NoItemInventoryException {

        //gets and updates snack to vend
        Snack snackToVend = getSnack(code);

        //check if money in is enough to cover snack
        if (moneyIn.compareTo(snackToVend.getPrice()) < 0) {
            throw new InsufficientFundsException("Insufficient Funds. You inserted: $" + moneyIn);
        }
        //add change calculations here



        //writing to audit dao & profit log
//        loadProfit();
//        profit = profit.add(snackToVend.getPrice());
        auditDao.writeAuditEntry(snackToVend.getType() + " SOLD.");
//        profitLog.writeAuditEntry(profit.toString());

        return updateSnackAmount(code);
    }


    //Sell Snack? add to Dao update inventory and validate money
}