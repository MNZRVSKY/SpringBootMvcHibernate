package com.epam.learn.services;

import com.epam.learn.dao.AccountDAO;
import com.epam.learn.storage.AccountEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountService {

    private static final Logger LOGGER = LogManager.getLogger(AccountService.class.getName());

    private final AccountDAO accountDAO;

    @Autowired
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public List<AccountEntity> getAllAccounts() {
        LOGGER.debug("getAllAccounts");

        List<AccountEntity> accounts = new ArrayList<>();
        accountDAO.findAll().forEach(accounts::add);

        return accounts;
    }

    public AccountEntity updateAccount(int userId, float money) {
        LOGGER.debug("updateAccount << " + userId + " >>");

        AccountEntity updatedAccount = accountDAO.findById(userId).orElse(null);

        if (updatedAccount == null) {
            return null;
        }

        if(updatedAccount.getUserId() == userId) {
            updatedAccount.setUserMoney(money);
        }

        return accountDAO.save(updatedAccount);
    }
}
