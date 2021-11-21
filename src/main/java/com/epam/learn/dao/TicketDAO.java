package com.epam.learn.dao;

import com.epam.learn.storage.TicketEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface TicketDAO extends CrudRepository<TicketEntity, Integer> {

}
