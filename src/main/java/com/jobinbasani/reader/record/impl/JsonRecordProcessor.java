package com.jobinbasani.reader.record.impl;

import com.jobinbasani.reader.RecordProcessor;
import com.jobinbasani.reader.enums.RecordType;
import com.jobinbasani.reader.record.Record;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class JsonRecordProcessor implements RecordProcessor {
    @Override
    public RecordType getRecordType() {
        return RecordType.JSON;
    }

    @Override
    public List<Record> getRecords(File reportFile) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public void writeRecords(List<Record> records, File inputReferenceFile, File outputFile) {

    }
}
