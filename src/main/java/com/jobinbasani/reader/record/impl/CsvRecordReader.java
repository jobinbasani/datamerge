package com.jobinbasani.reader.record.impl;

import com.jobinbasani.reader.RecordReader;
import com.jobinbasani.reader.enums.ReaderType;
import com.jobinbasani.reader.record.Record;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class CsvRecordReader implements RecordReader {

    @Override
    public ReaderType getReaderType() {
        return ReaderType.CSV;
    }

    @Override
    public boolean canProcessFile(File reportFile) {
        return reportFile.isFile() && reportFile.getName().toUpperCase().endsWith("." + ReaderType.CSV.name());
    }

    @Override
    public List<Record> getRecords(File reportFile) {
        return Collections.EMPTY_LIST;
    }
}
