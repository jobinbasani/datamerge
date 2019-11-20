package com.jobinbasani.reader;

import com.jobinbasani.reader.enums.RecordType;
import com.jobinbasani.reader.record.Record;

import java.io.File;
import java.util.List;

public interface RecordProcessor {
    RecordType getRecordType();
    boolean canProcessFile(File reportFile);
    List<Record> getRecords(File reportFile);
    void writeRecords(List<Record> records, File inputReferenceFile, File outputFile);
}
