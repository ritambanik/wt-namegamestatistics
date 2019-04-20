package com.willowtree.test.statistics.repository;

import com.willowtree.test.statistics.data.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, Long> {

    List<Event> findByUserId(String userId);
}
