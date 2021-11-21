package com.epam.learn.dao;

import com.epam.learn.storage.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface AccountDAO extends CrudRepository<AccountEntity, Integer> {

}