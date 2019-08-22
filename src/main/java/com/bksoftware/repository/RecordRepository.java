package com.bksoftware.repository;

import com.bksoftware.entities.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

    List<Record> findByStatus(boolean status);

    Record findByName(String name);

    Record findById(int id);
}
