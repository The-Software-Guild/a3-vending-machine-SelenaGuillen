package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VendingMachineAuditDaoStubImpl implements VendingMachineAuditDao {
    @Autowired
    public VendingMachineAuditDaoStubImpl(){}

    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {

        //does nothing, allows for service layer
        //to make call to the audit DAO
    }
}
