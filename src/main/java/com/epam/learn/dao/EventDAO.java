package com.epam.learn.dao;

import com.epam.learn.storage.EventEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface EventDAO extends CrudRepository<EventEntity, Integer> {

}
