package com.jobinbasani.reader;

import com.jobinbasani.reader.enums.RecordType;
import com.jobinbasani.reader.record.Record;
import org.apache.commons.lang3.NotImplementedException;

import java.io.File;
import java.util.List;

public interface RecordProcessor {
    RecordType getRecordType();
    List<Record> getRecords(File reportFile);
    default boolean canProcessFile(File reportFile){
        return reportFile.isFile() && reportFile.getName().toUpperCase().endsWith("." + getRecordType().name());
    }
    default void writeRecords(List<Record> records, File inputReferenceFile, File outputFile){
        throw new NotImplementedException("Not yet implemented!");
    }
}
