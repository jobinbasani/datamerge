package com.jobinbasani.reader;

import com.jobinbasani.reader.enums.ReaderType;
import com.jobinbasani.reader.record.Record;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface RecordProcessor {
    ReaderType getReaderType();
    boolean canProcessFile(File reportFile);
    List<Record> getRecords(File reportFile);
}
