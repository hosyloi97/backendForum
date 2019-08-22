package com.bksoftware.service;

import com.bksoftware.entities.Record;

import java.util.List;

public interface RecordService {

    List<Record> findAll();

    Record findByName(String name);

    boolean saveRecord(Record record);

    boolean deleteRecord(int recordId);
}
