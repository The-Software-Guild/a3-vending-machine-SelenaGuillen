package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Snack;
import com.sg.vendingmachine.dto.SnackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VendingMachineDaoStubImpl implements VendingMachineDao {


    public Snack onlySnack = new Snack();
    public List<Snack> snackList = new ArrayList<>();
    @Autowired
    public VendingMachineDaoStubImpl() {
        onlySnack.setCode("A1");
        onlySnack.setName("Oreos");
        onlySnack.setType(SnackType.OREOS);
        onlySnack.setPrice(BigDecimal.valueOf(1.50));
        onlySnack.setAmount(10);

        snackList.add(onlySnack);
    }

    public VendingMachineDaoStubImpl(Snack testSnack) {
        this.onlySnack = testSnack;
    }
    @Override
    public List<Snack> getAllSnacks() {

        return snackList;
    }

    @Override
    public List<Snack> getAllSnacksInStock() {
        List<Snack> snackList = new ArrayList<>();
        snackList.add(onlySnack);
        List<Snack> availableSnacks = snackList.stream()
                .filter((snack -> snack.getAmount() > 0))
                .collect(Collectors.toList());

        return availableSnacks;

    }

    @Override
    public Snack getSnack(String code)  {
        if (code.equals(onlySnack.getCode())) {
            return onlySnack;
        } else {
            return null;
        }
    }

    @Override
    public Snack updateSnackAmount(String code) throws VendingMachinePersistenceException {
        return getSnack(onlySnack.getCode());
    }

}
