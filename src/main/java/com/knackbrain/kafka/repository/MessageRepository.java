package com.knackbrain.kafka.repository;

import com.knackbrain.kafka.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findByTitle(final String tittle);
}
