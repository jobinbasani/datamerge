package com.jobinbasani.reader;

import com.jobinbasani.reader.enums.ReaderType;
import com.jobinbasani.reader.record.Record;

import java.io.File;
import java.util.List;

public interface RecordReader {
    ReaderType getReaderType();
    boolean canProcessFile(File reportFile);
    List<Record> getRecords(File reportFile);
}
