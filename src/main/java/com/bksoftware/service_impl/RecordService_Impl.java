package com.bksoftware.service_impl;

import com.bksoftware.entities.Record;
import com.bksoftware.repository.RecordRepository;
import com.bksoftware.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RecordService_Impl implements RecordService {

    private final static Logger LOGGER = Logger.getLogger(RecordService_Impl.class.getName());


    @Autowired
    private RecordRepository recordRepository;


    @Override
    public List<Record> findAll() {
        return recordRepository.findByStatus(true);
    }

    @Override
    public Record findByName(String name) {
        try {
            Record record = recordRepository.findByName(name);
            if (record != null) return record;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "find-all-record-by-name-error : {0}", ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean saveRecord(Record record) {
        try {
            recordRepository.save(record);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "save-record-error : {0}", ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteRecord(int recordId) {
        try {
            Record record = recordRepository.findById(recordId);
            record.setStatus(false);
            recordRepository.save(record);
            return true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "delete-record-error : {0}", ex.getMessage());
        }
        return false;
    }
}
